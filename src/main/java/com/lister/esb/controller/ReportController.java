package com.lister.esb.controller;

import com.lister.esb.dto.ReportcategoryDTO;
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
import static com.lister.esb.utils.ESBConstants.EX_RESPONSE_JSON_FMT;
/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 2/14/13
 * Time: 12:38 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class ReportController {
    private static Logger logger = LoggerFactory.getLogger(ReportController.class);

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
        * Handles Read All Request of XML format For Report Category data
        * @param isResponseRequired
        * @param readAllReportCategoryXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.GET, value="/reportCategory" , headers = "Accept=application/xml" )
       public @ResponseBody String readAllReportCategoryXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAllReportCategoryXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAllReportCategoryXml, ActionType.READ, isResponseRequired, DataFormat.XML, ReportcategoryDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Report Category Read-All request: Service Request Created ");

           String responseXmlString=null;
           if(isResponseRequired)
           {
               //Process synchronous requests
               try {
                   responseXmlString=conversionUtils.convertToXmlString(requestDelegator.transfer(serviceRequest),"reportcategoryDTO");
               } catch (IOException e) {
                   throw new XmlException(ESBConstants.EX_RESPONSE_XML_FMT);
               }
           }
           else
           {   //Sending the input request to the MQ
               logger.info("Report Category Read-All  Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }



      /**
        * Handles Read All Request of JSON format For Category data
        * @param isResponseRequired
        * @param readAllReportCategoryJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.GET, value="/reportCategory" , headers = "Accept=application/json" )
       public @ResponseBody String readAllReportCategoryJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAllReportCategoryJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAllReportCategoryJson, ActionType.READ, isResponseRequired, DataFormat.JSON, ReportcategoryDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Report Category Read All request: Service Request Created ");

           String responseJsonString=null;
           if(isResponseRequired)
           {
               //Process synchronous requests
               try {
                   responseJsonString=conversionUtils.convertObjectToJson(requestDelegator.transfer(serviceRequest));
               } catch (IOException e) {
                throw new JsonException(EX_RESPONSE_JSON_FMT);
            } catch (JSONException e) {
                throw new JsonException(EX_RESPONSE_JSON_FMT);
            }
           }
           else
           {   //Sending the input request to the MQ
               logger.info("Report Category Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
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

