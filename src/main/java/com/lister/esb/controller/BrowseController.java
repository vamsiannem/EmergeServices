package com.lister.esb.controller;

import com.lister.esb.dto.BrowseDTO;
import com.lister.esb.enums.ActionType;
import com.lister.esb.enums.SourceSystem;
import com.lister.esb.exceptions.JsonException;
import com.lister.esb.exceptions.XmlException;
import com.lister.esb.model.DataFormat;
import com.lister.esb.model.ServiceRequest;
import com.lister.esb.processors.IRequestDelegate;
import com.lister.esb.utils.ConversionUtils;
import com.lister.esb.utils.ESBConstants;
import com.lister.esb.utils.ServiceRequestFactoryBean;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 11/22/12
 * Time: 3:46 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class BrowseController {

    private static Logger logger = LoggerFactory.getLogger(BrowseController.class);

    @Autowired
    private ConversionUtils conversionUtils;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("serviceRequestFactoryBean")
    private ServiceRequestFactoryBean serviceRequestFactoryBean;

    @Autowired
    @Qualifier("simpleRequestDelegator")
    private IRequestDelegate requestDelegator;


    /**
     * Handles the Json Format request for Creating Browse
     * @param isResponseRequired
     * @param createBrowseJson
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value = "/browse" ,headers = "Accept=application/json" )
        public @ResponseBody String createBrowseJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createBrowseJson)
        {

            //(wraps up information to be passed to the Next Layer)
            ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createBrowseJson, ActionType.CREATE, isResponseRequired, DataFormat.JSON, BrowseDTO.class, SourceSystem.MARKET,storedProcedureName);
            logger.info("Browse request: Service Request Created ");

            String responseJsonString=null;
            if(isResponseRequired)
            {
              // Process synchronous requests
                try {
                    responseJsonString = conversionUtils.convertObjectToJson(requestDelegator.transfer(serviceRequest));
                } catch (IOException e) {
                    throw new JsonException(ESBConstants.EX_RESPONSE_JSON_FMT);
                } catch (JSONException e) {
                    throw new JsonException(ESBConstants.EX_RESPONSE_JSON_FMT);
                }
            }
            else
            {
                //Sending the input request to the MQ
                logger.info("Browse Request : Sending the input request to the MQ");
                jmsTemplate.convertAndSend(serviceRequest);
                responseJsonString=ESBConstants.RESPONSE_SUCCESS;
            }
            return responseJsonString;
        }

    /**
     * Handles the XML Format request for Creating Browse
     * @param isResponseRequired
     * @param createBrowseXml
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value="/browse" ,headers = "Accept=application/xml" )
    public @ResponseBody String createBrowseXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired ,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName ,@RequestBody String createBrowseXml)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createBrowseXml, ActionType.CREATE, isResponseRequired, DataFormat.XML, BrowseDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Browse request: Service Request Created ");
        String responseXmlString=null;
        if(isResponseRequired)
        {
           //Process synchronous requests
            try {
                responseXmlString=conversionUtils.convertToXML(requestDelegator.transfer(serviceRequest));
            } catch (IOException e) {
               throw new XmlException(ESBConstants.EX_RESPONSE_XML_FMT);
            }
        }
        else
        {
            //Sending the input request to the MQ
            logger.info("Browse Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }

    /**
     * Handles all the Exceptions related to Json format Request
     * @param jsonException
     * @return
     */
    @ExceptionHandler(value = JsonException.class)
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody String handleAddressNotFoundException(JsonException jsonException) {
        String errorMessage;
        try {
            errorMessage = conversionUtils.convertObjectToJson(jsonException.getJsonExceptionMessage());
        } catch (IOException e) {
            errorMessage= ESBConstants.EX_JSON_FMT;
        } catch (JSONException e) {
            errorMessage=ESBConstants.EX_JSON_FMT;
        }
        return errorMessage;
   }

    /**
     * Logging exceptions related to Attributes[XML] request
     * @param xmlException
     * @return
     */
   @ExceptionHandler(value = XmlException.class)
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody String handleAddressNotFoundException(XmlException xmlException) {
       String errorMessage = null;
       try {
           errorMessage = conversionUtils.convertToXML(xmlException);
       } catch (IOException e) {
           errorMessage = "<Error>Error in forming the exception xml</Error>";
       }
       return errorMessage;
   }
}
