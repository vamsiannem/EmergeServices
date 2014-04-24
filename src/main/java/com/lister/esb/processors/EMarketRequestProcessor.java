package com.lister.esb.processors;

import com.lister.esb.dto.BaseDTO;
import com.lister.esb.model.BaseBO;
import com.lister.esb.model.Request;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value = "eMarketRP")
public class EMarketRequestProcessor extends BaseProcessor {

    private Logger logger = Logger.getLogger(EMarketRequestProcessor.class);


    public String validateAndProcess(Request request) {

        String responseString = null;
        if ( request.getProcedureName() != null ) {
            logger.info("inside validate and process");
            responseString = processJDBCRequest(request);
        }
        else {
            responseString = processJpaRequest(request);
        }

        return responseString;
    }

    private String processJpaRequest(Request request) {
        logger.info("ESB"+request.getInputMessage().isEmpty());
        if(request.getInputMessage().isEmpty()){
                    logger.info("No data");
        }
        String responseString = null;
        Class<? extends BaseDTO> _dtoClass = request.getDtoClass();
        Class<? extends BaseBO> _boClass = getBOClass(_dtoClass);
        List<BaseDTO> baseDTOs = null;
        List<BaseBO> baseBOs;
        List<BaseBO> baseBOs2 = new ArrayList<BaseBO>();
        ArrayList<BaseBO> baseBOs1 = new ArrayList<BaseBO>();
        logger.info("Input Request:"+ request.toString());
        logger.info("BO Class:"+ _boClass);

        if(request.getInputMessage().isEmpty()){
             baseBOs1 = (ArrayList)processJpaCall(request,_boClass);
            responseString = getResponse(baseBOs1, _boClass, request);
        }else{
        // 1. JSON to Dto Conversion
            logger.debug("Converting Json/XML to DTO");
            baseDTOs = getDTOList(request.getInputMessage(), request.getInputDataFormat(), _dtoClass);

            // 2. Attribute Validations
            logger.debug("Validating DTO using BeanValidator (annotation based)");
            if( baseDTOs != null ){
                doValidation(request.getInputMessage(), baseDTOs, _dtoClass);
                logger.debug("Converting DTO input to BO to process the request");
                baseBOs = getConversionUtils().convertToBOFromDTO(baseDTOs);  // Get BO from DTO
                logger.debug("CRUDing BO to/from the DB");
                if((request.getActionType().toString()).equalsIgnoreCase("READ")){
                    baseBOs1.add(processJpaCall(request, baseBOs, _boClass));     // Process request based on ActionType (CRUD)
                    responseString = getResponse(baseBOs1, _boClass, request);  // Get the Json/XML response from the output of previous step.
                }else{
                    processJpaCall(request, baseBOs, _boClass);     // Process request based on ActionType (CRUD)
                    responseString = getResponse(baseBOs, _boClass, request);  // Get the Json/XML response from the output of previous step.
                }
            }

        }
        return responseString;
    }

    private String processJDBCRequest(Request request) {
        Object response = processJDBCCall(request);
        return response.toString();
    }


}
