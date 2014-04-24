package com.lister.esb.controller;

import com.lister.esb.dto.PurchaseDTO;
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
 */
@Controller
public class PurchaseController {

    private static Logger logger = LoggerFactory.getLogger(CampaignController.class);

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
     * Handles Update JSON format Requests For Purchase data
     * @param isResponseRequired
     * @param updatePurchasesJson
     * @return
     */
    @RequestMapping(value="/purchases", method= RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody String updatePurchasesJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updatePurchasesJson)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updatePurchasesJson,ActionType.UPDATE,isResponseRequired, DataFormat.JSON, PurchaseDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Purchase request: Service Request Created ");

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
        {
            //Sending the input request to the MQ  : Async
            logger.info("Purchase Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;

    }

    /**
     * Handles Creation Request of JSON format For Purchase data
     * @param isResponseRequired
     * @param createPurchasesJson
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value="/purchases" ,headers = "Accept=application/json" )
    public @ResponseBody String createPurchasesJson (@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createPurchasesJson )
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createPurchasesJson, ActionType.CREATE, isResponseRequired, DataFormat.JSON, PurchaseDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Purchase request: Service Request Created ");

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
            logger.info("Purchase Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }


    /**
        * Handles Delete Request of Json format For Purchases data
        * @param isResponseRequired
        * @param deletePurchasesJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/purchases" , headers = "Accept=application/json" )
       public @ResponseBody String deletePurchasesJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deletePurchasesJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deletePurchasesJson, ActionType.DELETE, isResponseRequired, DataFormat.JSON, PurchaseDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Purchases Delete request: Service Request Created ");

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
               logger.info("Purchases Delete Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }


    /**
     * Handles Update XML format Requests For Purchase data
     * @param isResponseRequired
     * @param updatePurchasesXml
     * @return
     */
    @RequestMapping(value="/purchases", method= RequestMethod.PUT, headers = "Accept=application/xml")
    public @ResponseBody String updatePurchasesXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updatePurchasesXml)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updatePurchasesXml,ActionType.UPDATE,isResponseRequired, DataFormat.XML, PurchaseDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Purchase request: Service Request Created ");

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
            logger.info("Purchase Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }

    /**
     * Handles Creation Request of JSON format For Purchase data
     * @param isResponseRequired
     * @param createPurchasesXml
     * @return
     * @throws Exception
     */
    @RequestMapping(method=RequestMethod.POST, value="/purchases" , headers = "Accept=application/xml" )
    public @ResponseBody String createPurchasesXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createPurchasesXml)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createPurchasesXml, ActionType.CREATE, isResponseRequired, DataFormat.XML, PurchaseDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Purchase request: Service Request Created ");

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
            logger.info("Purchase Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }


    /**
        * Handles Delete Request of XML format For Purchase data
        * @param isResponseRequired
        * @param deletePurchasesXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/purchases" , headers = "Accept=application/xml" )
       public @ResponseBody String deletePurchasesXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deletePurchasesXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deletePurchasesXml, ActionType.DELETE, isResponseRequired, DataFormat.XML, PurchaseDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Purchase Delete request: Service Request Created ");

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
               logger.info("Purchase Delete Request : Sending the input request to the MQ");
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
     * Logging exceptions related to Purchase[XML] request
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


    /**
        * Handles Read Request of XML format For Purchase data
        * @param isResponseRequired
        * @param readPurchasesXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/purchaseInfo" , headers = "Accept=application/xml" )
       public @ResponseBody String readPurchasesXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readPurchasesXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readPurchasesXml, ActionType.READ, isResponseRequired, DataFormat.XML, PurchaseDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Purchase Read request: Service Request Created ");

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
               logger.info("Purchase Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }

        /**
        * Handles Read Request of JSON format For Purchase data
        * @param isResponseRequired
        * @param readPurchasesJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/purchaseInfo" , headers = "Accept=application/json" )
       public @ResponseBody String readPurchasesJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readPurchasesJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readPurchasesJson, ActionType.READ, isResponseRequired, DataFormat.JSON, PurchaseDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Purchase Read request: Service Request Created ");

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
               logger.info("Purchase Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }
}
