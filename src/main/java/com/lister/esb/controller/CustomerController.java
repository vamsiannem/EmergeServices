package com.lister.esb.controller;

import com.lister.esb.dto.CustomerpurchaseDTO;
import com.lister.esb.dto.CustomerDTO;
import com.lister.esb.enums.ActionType;
import com.lister.esb.enums.SourceSystem;
import com.lister.esb.exceptions.ESBException;
import com.lister.esb.service.UDMService;
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
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.lang.IllegalAccessException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.lister.esb.utils.ESBConstants.EX_DATA_ACCESS;
import static com.lister.esb.utils.ESBConstants.EX_RESPONSE_JSON_FMT;
import static com.lister.esb.utils.ESBConstants.EX_XML_FMT;

/**
 *  Handles all the Customer Related Request
 */
@Controller
public class CustomerController {

    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);

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
     * Handles Update JSON format Requests For Customer data
     * @param isResponseRequired
     * @param updateCustomersJson
     * @return
     */
    @RequestMapping(value="/customers", method= RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody String updateCustomersJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateCustomersJson)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateCustomersJson,ActionType.UPDATE,isResponseRequired, DataFormat.JSON, CustomerDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Customer request: Service Request Created ");

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
            logger.info("Customer Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;

    }

    /**
     * Handles Creation Request of JSON format For Customer data
     * @param isResponseRequired
     * @param createCustomersJson
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value="/customers" ,headers = "Accept=application/json", consumes = "application/json")
    public @ResponseBody String createCustomersJson (@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createCustomersJson )
    {

        createCustomersJson = createCustomersJson.replaceFirst("\"","");
        createCustomersJson = createCustomersJson.replace("}\"", "}").replace("\\","");


        logger.info("Customer request: "+ createCustomersJson);

        /*String[] jString = (createCustomersJson.replace("=", "\":\"")).split("&");
        createCustomersJson = "[{";
        for (int i=0;i < jString.length; i++){
            if (jString[i].indexOf("storedProcedureName")==-1 && jString[i].indexOf("isResponseRequired")==-1)
                createCustomersJson +=  "\""+jString[i]+"\",";
        }
        createCustomersJson = createCustomersJson.substring(0, createCustomersJson.length()-1) + "}]";
        logger.info("Customer request: "+ createCustomersJson);*/
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createCustomersJson, ActionType.CREATE, isResponseRequired, DataFormat.JSON, CustomerDTO.class, SourceSystem.MARKET,storedProcedureName);
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
        * @param deleteCustomersJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/customers" , headers = "Accept=application/json" )
       public @ResponseBody String deleteCustomersJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deleteCustomersJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deleteCustomersJson, ActionType.DELETE, isResponseRequired, DataFormat.JSON, CustomerDTO.class, SourceSystem.MARKET,storedProcedureName);
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
     * Handles Update XML format Requests For Customer data
     * @param isResponseRequired
     * @param updateCustomersXml
     * @return
     */
    @RequestMapping(value="/customers", method= RequestMethod.PUT, headers = "Accept=application/xml")
    public @ResponseBody String updateCustomersXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateCustomersXml)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateCustomersXml,ActionType.UPDATE,isResponseRequired, DataFormat.XML, CustomerDTO.class, SourceSystem.MARKET,storedProcedureName);
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
     * Handles Creation Request of JSON format For Customer data
     * @param isResponseRequired
     * @param createCustomersXml
     * @return
     * @throws Exception
     */
    @RequestMapping(method=RequestMethod.POST, value="/customers" , headers = "Accept=application/xml" )
    public @ResponseBody String createCustomersXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createCustomersXml)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createCustomersXml, ActionType.CREATE, isResponseRequired, DataFormat.XML, CustomerDTO.class, SourceSystem.MARKET,storedProcedureName);
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
        * @param readCustomersXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/customersInfo" , headers = "Accept=application/xml" )
       public @ResponseBody String readCustomersXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readCustomersXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readCustomersXml, ActionType.READ, isResponseRequired, DataFormat.XML, CustomerDTO.class, SourceSystem.MARKET,storedProcedureName);
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
        * @param readCustomersJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/customersInfo" , headers = "Accept=application/json" )
       public @ResponseBody String readCustomersJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readCustomersJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readCustomersJson, ActionType.READ, isResponseRequired, DataFormat.JSON, CustomerDTO.class, SourceSystem.MARKET,storedProcedureName);
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
        * @param deleteCustomersXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/customers" , headers = "Accept=application/xml" )
       public @ResponseBody String deleteCustomersXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deleteCustomersXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deleteCustomersXml, ActionType.DELETE, isResponseRequired, DataFormat.XML, CustomerDTO.class, SourceSystem.MARKET,storedProcedureName);
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


    /**
        * Handles Update Request of XML format For Customer Purchase data
        * @param isResponseRequired
        * @param updateCustomerPurchaseJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/customerpurchases" , headers = "Accept=application/json" )
       public @ResponseBody String updateCustomerPurchaseJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateCustomerPurchaseJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateCustomerPurchaseJson, ActionType.READ, isResponseRequired, DataFormat.JSON, CustomerpurchaseDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Customer Purchase request: Service Request Created ");

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
               logger.info("Customer Purchase Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }

       /**
        * Handles Update Request of XML format For Customer data
        * @param isResponseRequired
        * @param updateCustomerPurchaseXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/customerpurchases" , headers = "Accept=application/xml" )
       public @ResponseBody String updateCustomerPurchaseXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateCustomerPurchaseXml)
       {
             //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateCustomerPurchaseXml, ActionType.UPDATE, isResponseRequired, DataFormat.XML, CustomerpurchaseDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Customer Update request: Service Request Created ");

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
               logger.info("Customer Update Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }
}
