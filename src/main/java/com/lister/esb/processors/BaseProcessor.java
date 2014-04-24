package com.lister.esb.processors;

import com.lister.esb.dto.BaseDTO;
import com.lister.esb.exceptions.ESBException;
import com.lister.esb.exceptions.JsonException;
import com.lister.esb.exceptions.XmlException;
import com.lister.esb.model.*;
import com.lister.esb.service.UDMService;
import com.lister.esb.utils.ConversionUtils;
import com.lister.esb.utils.DTO2BOCacheManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import javax.jms.Destination;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.net.URLDecoder;

import static com.lister.esb.utils.ESBConstants.*;

@Component
public abstract class BaseProcessor implements IRequestProcessor {

    @Autowired(required = true)
    @Qualifier(value = "jmsTemplate")
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier(value = "errorQueue")
    private Destination errorQueue;

    @Autowired
    private Validator beanValidator;

    @Autowired
    private UDMService udmService;

    @Autowired
    private ConversionUtils conversionUtils;

    @Autowired
    private DTO2BOCacheManager dto2BOCacheManager;


    private Logger logger = Logger.getLogger(BaseProcessor.class);

    /**
     * Convert the inputData (json/xml) to DTO Objects
     * @param inputData
     * @param dataFormat
     * @param dtoClass
     * @return
     *
     */
    protected List<BaseDTO> getDTOList(String inputData, DataFormat dataFormat, Class<? extends BaseDTO> dtoClass) {

        List<BaseDTO> result = null;
        if(dataFormat.equals(DataFormat.JSON)){
            logger.debug("Converting JSON Input to DTO");
            try {
                result = conversionUtils.convertToDTOFromJSON(inputData, dtoClass);
            } catch (Throwable ex){
                handleException(new ESBException(inputData, DataFormat.JSON, ex, EX_JSON_FMT));
            }
        }
        else if(dataFormat.equals(DataFormat.XML)){
            logger.debug("Converting XML Input to DTO");
            try {
                result = conversionUtils.convertToDTOFromXML(inputData);
            }catch (Throwable ex){
                handleException(new ESBException(inputData, DataFormat.XML, ex, EX_XML_FMT));
            }
        }
        return  result;
    }

    /**
     *  Put a message in to the queue
     * @param inputData
     * @param throwable
     */
    protected void putErrorQ(String inputData, Throwable throwable){
        jmsTemplate.convertAndSend(errorQueue, new ErrorMessage(inputData, throwable).toString());
    }

    /**
     * Validate the DTO Bean based on the field level annotations with in the DTO class.
     *
     * @param inputData
     * @param baseDTOs
     * @param dtoClass
     * @return
     */
    protected void doValidation(String inputData, List<BaseDTO> baseDTOs, Class dtoClass) {
        logger.debug("Validating the DTO Beans in the list");
        for (BaseDTO baseDTO: baseDTOs){
            BindingResult bindingResult = new BeanPropertyBindingResult(baseDTO, getName(dtoClass));
            beanValidator.validate(baseDTO, bindingResult);
            if(bindingResult.hasErrors()) {
                /*for(ObjectError error: bindingResult.getAllErrors()){
                    String errorMsg = error.getDefaultMessage();
                }*/
                logger.error("Validation errors found: ");
                logger.error(bindingResult.getAllErrors().toString());
                Exception exception = new Exception(bindingResult.getAllErrors().toString());
                handleException(new ESBException(inputData, DataFormat.JSON, exception,EX_JSON_UNR_PROP));
            }
        }
        logger.debug("No Bean validation errors, Continue...");
    }

    /**
     * Call the appropriate service on the Spring data layer.
     * @param request
     * @param baseBOs
     * @param _boClass
     */
    @Transactional(propagation = Propagation.REQUIRED)
    protected BaseBO processJpaCall(Request request, List<BaseBO> baseBOs, Class _boClass){

        BaseBO baseBO=null;
        try {
            switch (request.getActionType()){
                case CREATE:
                    logger.debug("Save Objects for "+ _boClass.getSimpleName());
                    getUdmService().save(baseBOs, _boClass);
                    break;

                case DELETE:
                    logger.debug("Delete Objects for "+ _boClass.getSimpleName());
                    getUdmService().delete(baseBOs.get(0).getId(), _boClass);
                    break;

                case READ:
                    logger.debug("Read Objects for "+ _boClass.getSimpleName());
                     baseBO=getUdmService().find(baseBOs.get(0).getId(),_boClass);
                    break;

                case UPDATE:
                    logger.debug("Update Objects for "+ _boClass.getSimpleName());
                    getUdmService().update(baseBOs, _boClass);
                    break;
            }
        }
        catch (DataAccessException dae){
            handleException(new ESBException(request.getInputMessage(), request.getInputDataFormat(), dae, EX_DATA_ACCESS));
        }
        return baseBO;
    }

    /**
     * Call the appropriate Read All service on the Spring data layer.
     * @param request
     * @param _boClass
     */
    @Transactional(propagation = Propagation.REQUIRED)
    protected List<BaseBO> processJpaCall(Request request, Class _boClass){
        logger.info("Inside function");
        List<BaseBO> baseBO=null;
         try {
              logger.debug("Read Objects for "+ _boClass.getSimpleName());
              baseBO=getUdmService().findAll(_boClass);
         }catch (DataAccessException dae){
            handleException(new ESBException(request.getInputMessage(), request.getInputDataFormat(), dae, EX_DATA_ACCESS));
        }
         return baseBO;
    }


    /*protected BaseBO processJpaCall(Request request, List<BaseBO> baseBOs, Class _boClass,int readFlag){
        logger.info("inside new function");
        BaseBO baseBO = null;
        try {
            CustomerBO customerBO = null;
                    logger.info("Read Objects for "+ _boClass.getSimpleName());
                    logger.info( getUdmService().find(baseBOs.get(0).getId(),_boClass));
                    baseBO =  getUdmService().find(baseBOs.get(0).getId(),_boClass);
                    customerBO = (CustomerBO)baseBO;
                    logger.info(customerBO.getFirstName()+customerBO.getCity());
        }
        catch (DataAccessException dae){
            handleException(new ESBException(request.getInputMessage(), request.getInputDataFormat(), dae, EX_DATA_ACCESS));
        }

        return baseBO;
    }*/



    protected Object processJDBCCall(Request request)
    {
        logger.info("inside processJDBC call");
        List<Map<String , Object>> inParameterMap =null;
        Object returnObject = null;
         if(request.getInputMessage().isEmpty()){
                    logger.info("No data");
        }   else{
             logger.info(request.getInputMessage());
             logger.info(request.getInputDataFormat());
             logger.info(request.getProcedureName());
             logger.info(request.isResponseRequired());

            inParameterMap = convertInputStrToParamMap(request);
         }

        logger.info("inside processJDBC call2");
        logger.info(request.getProcedureName());
        try {
            if ( inParameterMap != null ) {
                if( inParameterMap.size() > 1 ) {
                    int[] response = udmService.executeProcedure( request.getProcedureName(), inParameterMap );
                    StringBuilder responseString  = new StringBuilder();
                    for(int i: response){
                        responseString.append(i);
                    }
                    returnObject = responseString.toString();
                }
                else {
                    returnObject = udmService.executeProcedure(request.getProcedureName(), inParameterMap.get(0) );
                }
            }else{
                returnObject = udmService.executeProcedure(request.getProcedureName());
                logger.info("No data found to create map");
            }
        } catch(Throwable dae) {
            handleException(new ESBException(request.getInputMessage(), request.getInputDataFormat(), dae , EX_DATA_ACCESS_PROCEDURE));
        }

        return returnObject;

    }

    private List<Map<String, Object>> convertInputStrToParamMap(Request request) {
        List<Map<String, Object>> inParameterMap = null;
        switch(request.getInputDataFormat())
        {
            case JSON:
                try {
                    inParameterMap = conversionUtils.convertToMapFromJson(request.getInputMessage());
                } catch (Throwable ex) {
                    handleException(new ESBException(request.getInputMessage(), request.getInputDataFormat(), ex , EX_JSON_FMT));
                }
                break;
            case XML:
                try {
                    inParameterMap = conversionUtils.convertToMapFromXML(request.getInputMessage());
                } catch (Throwable ex) {
                    handleException(new ESBException(request.getInputMessage(), request.getInputDataFormat(), ex, EX_XML_FMT));
                }
                break;
        }
        return inParameterMap;

    }

    /**
     * Get the bo class
     * @param baseDTOs
     * @return
     */
    protected Class getBOClass(List<BaseDTO> baseDTOs){
        Class _boClass = null;
        for (int i = 0, baseDTOsSize = baseDTOs.size(); i < baseDTOsSize; i++) {
            BaseDTO baseDTO = baseDTOs.get(i);
            _boClass = dto2BOCacheManager.getBOClass(baseDTO.getClass());
            break;
        }
        return _boClass;
    }

    /**
     *
     * @param dtoClass
     * @return
     */
    protected Class<? extends BaseBO> getBOClass(Class dtoClass){
        return dto2BOCacheManager.getBOClass(dtoClass);
    }


    /**
     *  Convert the response to appropriate format based on the input
     *
     *
     * @param baseBOs
     * @param boClass
     * @param request
     * @return
     */
    protected String getResponse(List<BaseBO> baseBOs, Class boClass, Request request)
    {
        logger.info(request.getInputDataFormat());
        logger.info(request.getInputDataFormat().toString().equalsIgnoreCase("JSON"));
        if(request.isResponseRequired()){
            BaseBO baseBO = null;
            if(baseBOs!=null && baseBOs.size() > 0 ){
                baseBO = baseBOs.get(0);
                if( boClass != null && baseBOs.size() == 1 ){
                    // Convert BO object to an xml for reading purposes
                    if((request.getActionType().toString()).equalsIgnoreCase("READ")){
                        if(request.getInputDataFormat().toString().equalsIgnoreCase("XML")){
                            try{
                                logger.info(conversionUtils.convertToXML(baseBO));
                                return conversionUtils.convertToXML(baseBO);
                            }catch (Throwable ex)  {
                                handleException(new ESBException(request.getInputMessage(), request.getInputDataFormat(), ex, EX_RESPONSE_BUILDING));
                            }
                        }else if(request.getInputDataFormat().toString().equalsIgnoreCase("JSON")){
                            logger.info("inside JSON");
                            return conversionUtils.convertToJSON(baseBO);
                        }
                    }else{
                            logger.debug("Single Object Response, returning Id");
                            if(boClass.equals(CampaignBO.class))
                                return ((CampaignBO)baseBO).getCampaignId().toString();
                            else if(boClass.equals(CustomerBO.class))
                                return ((CustomerBO)baseBO).getMemberId().toString();
                            else if(boClass.equals(ProgramBO.class))
                                return ((ProgramBO)baseBO).getProgramId().toString();
                            else if(boClass.equals(AttributeBO.class))
                                return ((AttributeBO)baseBO).getAttributeId().toString();
                            else if(boClass.equals(CartBO.class))
                                return ((CartBO)baseBO).getCartId().toString();
                            else if(boClass.equals(BrowseBO.class))
                                return ((BrowseBO)baseBO).getMemberId().toString();
                            else if(boClass.equals(TemplateBO.class))
                                return ((TemplateBO)baseBO).getTemplateId().toString();
                             else if(boClass.equals(CampaignsegmentBO.class))
                                return ((CampaignsegmentBO)baseBO).getCampaignId().toString();
                            else if(boClass.equals(SegmentBO.class))
                                return ((SegmentBO)baseBO).getSegmentId().toString();
                            else if(boClass.equals(PurchaseBO.class))
                                return ((PurchaseBO)baseBO).getMemberId().toString();
                            else if(boClass.equals(TemplatedetailBO.class))
                                return ((TemplatedetailBO)baseBO).getSequenceNo().toString();
                            else if(boClass.equals(SkuBO.class))
                                return ((SkuBO)baseBO).getMemberId().toString();
                            else if(boClass.equals(CategoryBO.class))
                                return ((CategoryBO)baseBO).getMemberId().toString();
                            else if(boClass.equals(EventBO.class))
                                return ((EventBO)baseBO).getMemberId().toString();
                    }
                }
                else {
                    return getResponse(request.getInputDataFormat(), baseBOs, request.getInputMessage());
                }
            }
        }
        return null;
    }

    private String getResponse(DataFormat inputDataFormat, List<BaseBO> baseBOs, String inputMessage)  {
        String response = null;
        switch (inputDataFormat){
            case JSON: response = conversionUtils.convertToJSONFromBO(baseBOs);
                break;
            case XML:
                try {
                    response = conversionUtils.convertToXMLFromBO(baseBOs);
                } catch (Throwable ex) {
                    handleException(new ESBException(inputMessage, inputDataFormat, ex, EX_RESPONSE_BUILDING));
                }
                break;
        }
        return  response;
    }


    /**
     *  Handle all the processor level exceptions
     * @param esbException
     */
    protected void handleException(ESBException esbException) {
        Throwable t = esbException.getExceptionMessage();
        logger.error(t);
        if ( esbException.getDataFormat().equals( DataFormat.JSON ) ){
            putErrorQ(esbException.getInputData(), t);
            throw new JsonException(esbException.getBusinessException());
        }
        else if(esbException.getDataFormat().equals( DataFormat.XML )) {
            putErrorQ(esbException.getInputData(), t);
            throw new XmlException(esbException.getBusinessException());
        }
    }

    protected String getName(Class dtoClass) {
        return dtoClass.getSimpleName() + "_" + Math.random();
    }

    public UDMService getUdmService() {
        return udmService;
    }

    public ConversionUtils getConversionUtils() {
        return conversionUtils;
    }


}
