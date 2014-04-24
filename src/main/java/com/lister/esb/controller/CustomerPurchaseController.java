package com.lister.esb.controller;


import com.lister.esb.dto.CustomerpurchaseDTO;
import com.lister.esb.enums.ActionType;
import com.lister.esb.enums.SourceSystem;
import com.lister.esb.exceptions.JsonException;
import com.lister.esb.exceptions.XmlException;
import com.lister.esb.model.DataFormat;
import com.lister.esb.model.ServiceRequest;
import com.lister.esb.processors.IRequestDelegate;
import com.lister.esb.service.UDMService;
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
 *  Handles all the Customer Related Request
 */
@Controller
public class CustomerPurchaseController {

    private static Logger logger = LoggerFactory.getLogger(CustomerPurchaseController.class);

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

    @Autowired
    private UDMService udmService;

    /**
     * Handles Creation Request of JSON format For Customer data
     * @param isResponseRequired
     * @param createCustomerPurchaseJson
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value="/memberPurchase" ,headers = "Accept=application/json" )
    public @ResponseBody String createCustomerPurchaseJson (@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createCustomerPurchaseJson )
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createCustomerPurchaseJson, ActionType.CREATE, isResponseRequired, DataFormat.JSON, CustomerpurchaseDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Customer request: Service Request Created ");

        String responseJsonString="Success";
        if(isResponseRequired)
        {
            // Process synchronous requests
            try {
                 responseJsonString=conversionUtils.convertObjectToJson(requestDelegator.transfer(serviceRequest));
            }
            catch (IOException e) {
                throw new JsonException(EX_RESPONSE_JSON_FMT);
            } catch (JSONException e) {
                throw new JsonException(EX_RESPONSE_JSON_FMT);
            }
        }
        else
        {
            // Sending the input request to the MQ
            logger.info("Customer Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }


     /**
        * Handles Delete Request of Json format For Customer data
        * @param isResponseRequired
        * @param deleteCustomerPurchaseJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/memberPurchase" , headers = "Accept=application/json" )
       public @ResponseBody String deleteCustomerPurchaseJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deleteCustomerPurchaseJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deleteCustomerPurchaseJson, ActionType.DELETE, isResponseRequired, DataFormat.JSON, CustomerpurchaseDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Customer Delete request: Service Request Created ");

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
               logger.info("Customer Delete Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }

    /**
     * Handles Creation Request of JSON format For Customer data
     * @param isResponseRequired
     * @param createCustomerPurchaseXml
     * @return
     * @throws Exception
     */
    @RequestMapping(method=RequestMethod.POST, value="/memberPurchase" , headers = "Accept=application/xml" )
    public @ResponseBody String createCustomerPurchaseXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createCustomerPurchaseXml)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createCustomerPurchaseXml, ActionType.CREATE, isResponseRequired, DataFormat.XML, CustomerpurchaseDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Customer request: Service Request Created ");

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
        {   //Sending the input request to the MQ
            logger.info("Customer Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }


        /**
        * Handles Read Request of XML format For Customer data
        * @param isResponseRequired
        * @param readCustomerPurchaseXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/CustomerpurchaseInfo" , headers = "Accept=application/xml" )
       public @ResponseBody String readCustomerPurchaseXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readCustomerPurchaseXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readCustomerPurchaseXml, ActionType.READ, isResponseRequired, DataFormat.XML, CustomerpurchaseDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Customer Read request: Service Request Created ");

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
           {   //Sending the input request to the MQ
               logger.info("Customer Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }

        /**
        * Handles Read Request of XML format For Customer data
        * @param isResponseRequired
        * @param readCustomerPurchaseJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/memberPurchaseInfo" , headers = "Accept=application/json" )
       public @ResponseBody String readCustomerPurchaseJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readCustomerPurchaseJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readCustomerPurchaseJson, ActionType.READ, isResponseRequired, DataFormat.JSON, CustomerpurchaseDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Customer Read request: Service Request Created ");

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
               logger.info("Customer Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }

       /**
        * Handles Delete Request of XML format For Customer data
        * @param isResponseRequired
        * @param deleteCustomerPurchaseXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/memberPurchase" , headers = "Accept=application/xml" )
       public @ResponseBody String deleteCustomerPurchaseXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deleteCustomerPurchaseXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deleteCustomerPurchaseXml, ActionType.DELETE, isResponseRequired, DataFormat.XML, CustomerpurchaseDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Customer Delete request: Service Request Created ");

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
           {   //Sending the input request to the MQ
               logger.info("Customer Delete Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }

    /**
     * Logging exceptions related to JSon
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
     * Logging exceptions related to Customer[XML] request
     * @param xmlException
     * @return
     */
    @ExceptionHandler(value = XmlException.class)
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody String handleAddressNotFoundException(XmlException xmlException) {
        String errorMessage;
        try {
            errorMessage = conversionUtils.convertToXML(xmlException.getXmlExceptionMessage());
        } catch (IOException e) {
            errorMessage = "<Error>Error in forming the exception xml</Error>";
        }
        return errorMessage;
    }
}
