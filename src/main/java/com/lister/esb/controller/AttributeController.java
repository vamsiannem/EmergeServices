package com.lister.esb.controller;

import com.lister.esb.dto.AttributeDTO;
import com.lister.esb.dto.ColumnDTO;
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
 * Date: 11/22/12
 * Time: 3:46 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class AttributeController{

    private static Logger logger = LoggerFactory.getLogger(AttributeController.class);

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
     * Handles Update JSON format Requests For Attributes
     * @param isResponseRequired
     * @param updateAttributesJson
     * @return
     */
    @RequestMapping(value = "/attributes", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody String updateAttributesJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateAttributesJson) {

        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateAttributesJson, ActionType.UPDATE, isResponseRequired, DataFormat.JSON, AttributeDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Attribute request: Service Request Created ");

        String responseJsonString = null;
        if(isResponseRequired)
        {
            //Process synchronous requests
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
            logger.info("Attribute Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }


    /**
     * Handles the Json Format request for Creating Attributes
     * @param isResponseRequired
     * @param createAttributeJson
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value = "/attributes" ,headers = "Accept=application/json" )
    public @ResponseBody String createAttributeJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createAttributeJson)
    {

        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createAttributeJson, ActionType.CREATE, isResponseRequired, DataFormat.JSON, AttributeDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Attribute request: Service Request Created ");

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
            logger.info("Attribute Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }

     /**
        * Handles Delete Request of Json format For Attribute data
        * @param isResponseRequired
        * @param deleteAttributesJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/attributes" , headers = "Accept=application/json" )
       public @ResponseBody String deleteAttributesJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deleteAttributesJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deleteAttributesJson, ActionType.DELETE, isResponseRequired, DataFormat.JSON, AttributeDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Attribute Delete request: Service Request Created ");

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
               logger.info("Attribute Delete Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }
    /**
     * Handles Update XML format Requests For Attributes
     * @param isResponseRequired
     * @param updateAttributesXml
     * @return
     */
    @RequestMapping(value="/attributes", method= RequestMethod.PUT ,headers = "Accept=application/xml")
    public @ResponseBody String updateAttributesXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateAttributesXml) {

        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateAttributesXml, ActionType.UPDATE, isResponseRequired, DataFormat.XML, AttributeDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Attribute request: Service Request Created ");
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
            logger.info("Attributes Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }

    /**
     * Handles the XML Format request for Creating Attribute
     * @param isResponseRequired
     * @param createAttributesXml
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value="/attributes" ,headers = "Accept=application/xml" )
    public @ResponseBody String createAttributesXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired ,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName ,@RequestBody String createAttributesXml)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createAttributesXml, ActionType.CREATE, isResponseRequired, DataFormat.XML, AttributeDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Attribute request: Service Request Created ");
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
            logger.info("Attribute Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }


     /**
        * Handles Delete Request of XML format For Attribute data
        * @param isResponseRequired
        * @param deleteAttributesXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/attributes" , headers = "Accept=application/xml" )
       public @ResponseBody String deleteAttributesXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deleteAttributesXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deleteAttributesXml, ActionType.DELETE, isResponseRequired, DataFormat.XML, AttributeDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Attribute Delete request: Service Request Created ");

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
               logger.info("Attribute Delete Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }



    /**
        * Handles Read Request of XML format For Attribute data
        * @param isResponseRequired
        * @param readAttributesXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/attributesInfo" , headers = "Accept=application/xml" )
       public @ResponseBody String readAttributesXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAttributesXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAttributesXml, ActionType.READ, isResponseRequired, DataFormat.XML, AttributeDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Attribute Read request: Service Request Created ");

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
               logger.info("Attribute Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }

        /**
        * Handles Read Request of JSON format For Attribute data
        * @param isResponseRequired
        * @param readAttributesJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/attributesInfo" , headers = "Accept=application/json" )
       public @ResponseBody String readAttributesJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAttributesJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAttributesJson, ActionType.READ, isResponseRequired, DataFormat.JSON, AttributeDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Attribute Read request: Service Request Created ");

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
               logger.info("Attribute Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }





    /**
        * Handles Read All Request of XML format For Programs data
        * @param isResponseRequired
        * @param readAllAttributesXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.GET, value="/attributes" , headers = "Accept=application/xml" )
       public @ResponseBody String readAllAttributesXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAllAttributesXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAllAttributesXml, ActionType.READ, isResponseRequired, DataFormat.XML, AttributeDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Attribute Read-All request: Service Request Created ");

           String responseXmlString=null;
           if(isResponseRequired)
           {
               //Process synchronous requests
               try {
                   if(storedProcedureName!=null){
                       responseXmlString=conversionUtils.convertToXmlString(requestDelegator.transfer(serviceRequest),"attributeDTO");
                   }else{
                       responseXmlString=conversionUtils.convertToXML(requestDelegator.transfer(serviceRequest));
                   }

               } catch (IOException e) {
                   throw new XmlException(ESBConstants.EX_RESPONSE_XML_FMT);
               }
           }
           else
           {   //Sending the input request to the MQ
               logger.info("Attributes Read-All  Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }



      /**
        * Handles Read All Request of JSON format For Programs data
        * @param isResponseRequired
        * @param readAllAttributesJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.GET, value="/attributes" , headers = "Accept=application/json" )
       public @ResponseBody String readAllAttributesJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAllAttributesJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAllAttributesJson, ActionType.READ, isResponseRequired, DataFormat.JSON, AttributeDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Attribute Read All request: Service Request Created ");

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
               logger.info("Attribute Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }





    /**
        * Handles Read Attribute by type Request of JSON format For Attribute data
        * @param isResponseRequired
        * @param readAttributeByTypeJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/attributeByType" , headers = "Accept=application/json" )
       public @ResponseBody String readAttributeByTypeJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAttributeByTypeJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAttributeByTypeJson, ActionType.READ, isResponseRequired, DataFormat.JSON, AttributeDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Attribute Read By TYPE request: Service Request Created ");

           String responseJsonString=null;
           if(isResponseRequired)
           {
               //Process synchronous requests

                   responseJsonString=conversionUtils.convertToJsonString(requestDelegator.transfer(serviceRequest));

           }
           else
           {   //Sending the input request to the MQ
               logger.info("Attribute Read by Type Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }



    /**
        * Handles Read Attribute by type Request XML
        * @param isResponseRequired
        * @param readAttributeByTypeXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/attributeByType" , headers = "Accept=application/xml" )
       public @ResponseBody String readAttributeByTypeXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAttributeByTypeXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAttributeByTypeXml, ActionType.READ, isResponseRequired, DataFormat.XML, AttributeDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Attributes Read by type request: Service Request Created ");
           logger.info(readAttributeByTypeXml);
           String responseXmlString=null;
           if(isResponseRequired)
           {
               //Process synchronous requests
               try {
                   responseXmlString=conversionUtils.convertToXmlString(requestDelegator.transfer(serviceRequest),"attributeDTO");
               } catch (IOException e) {
                   throw new XmlException(ESBConstants.EX_RESPONSE_XML_FMT);
               }
           }
           else
           {   //Sending the input request to the MQ
               logger.info("Attribtues Read By type Read Request : Sending the input request to the MQ");
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
