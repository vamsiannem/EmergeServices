package com.lister.esb.controller;

import com.lister.esb.dto.TemplateDTO;
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
 * Date: 11/23/12
 * Time: 7:07 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class TemplateController {
     private static Logger logger = LoggerFactory.getLogger(TemplateController.class);

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
     * Handles Update JSON format Requests For Templates
     * @param isResponseRequired
     * @param updateTemplatesJson
     * @return
     */
    @RequestMapping(value = "/templates", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody String updateTemplatesJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateTemplatesJson) {

        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateTemplatesJson, ActionType.UPDATE, isResponseRequired, DataFormat.JSON, TemplateDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Template request: Service Request Created ");

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
            logger.info("Template Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }


    /**
     * Handles the Json Format request for Creating Templates
     * @param isResponseRequired
     * @param createTemplateJson
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value = "/templates" ,headers = "Accept=application/json" )
    public @ResponseBody String createTemplateJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createTemplateJson)
    {

        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createTemplateJson, ActionType.CREATE, isResponseRequired, DataFormat.JSON, TemplateDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Template request: Service Request Created ");

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
            logger.info("Template Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }

     /**
        * Handles Delete Request of Json format For Templates data
        * @param isResponseRequired
        * @param deleteTemplatesJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/templates" , headers = "Accept=application/json" )
       public @ResponseBody String deleteTemplatesJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deleteTemplatesJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deleteTemplatesJson, ActionType.DELETE, isResponseRequired, DataFormat.JSON, TemplateDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Templates Delete request: Service Request Created ");

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
               logger.info("Templates Delete Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }


    /**
     * Handles Update XML format Requests For Templates
     * @param isResponseRequired
     * @param updateTemplatesXml
     * @return
     */
    @RequestMapping(value="/templates", method= RequestMethod.PUT ,headers = "Accept=application/xml")
    public @ResponseBody String updateTemplatesXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateTemplatesXml) {

        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateTemplatesXml, ActionType.UPDATE, isResponseRequired, DataFormat.XML, TemplateDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Template request: Service Request Created ");
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
            logger.info("Template Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }

    /**
     * Handles the XML Format request for Creating Templates
     * @param isResponseRequired
     * @param createTemplatesXml
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value="/templates" ,headers = "Accept=application/xml" )
    public @ResponseBody String createTemplatesXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired ,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName ,@RequestBody String createTemplatesXml)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createTemplatesXml, ActionType.CREATE, isResponseRequired, DataFormat.XML, TemplateDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Template request: Service Request Created ");
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
            logger.info("Template Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }


    /**
        * Handles Delete Request of XML format For Templates data
        * @param isResponseRequired
        * @param deleteTemplatesXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/templates" , headers = "Accept=application/xml" )
       public @ResponseBody String deleteTemplatesXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deleteTemplatesXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deleteTemplatesXml, ActionType.DELETE, isResponseRequired, DataFormat.XML, TemplateDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Templates Delete request: Service Request Created ");

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
               logger.info("Templates Delete Request : Sending the input request to the MQ");
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
     * Logging exceptions related to Template[XML] request
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


   /**
        * Handles Read Request of XML format For Template data
        * @param isResponseRequired
        * @param readTemplatesXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/templateInfo" , headers = "Accept=application/xml" )
       public @ResponseBody String readTemplatesXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readTemplatesXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readTemplatesXml, ActionType.READ, isResponseRequired, DataFormat.XML, TemplateDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Template Read request: Service Request Created ");

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
               logger.info("Template Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }

        /**
        * Handles Read Request of JSON format For Template data
        * @param isResponseRequired
        * @param readTemplatesJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/templateInfo" , headers = "Accept=application/json" )
       public @ResponseBody String readCampaignsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readTemplatesJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readTemplatesJson, ActionType.READ, isResponseRequired, DataFormat.JSON, TemplateDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Template Read request: Service Request Created ");

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
               logger.info("Template Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }


    /**
        * Handles Read All Request of XML format For Campaigns data
        * @param isResponseRequired
        * @param readAllTemplatesXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.GET, value="/templates" , headers = "Accept=application/xml" )
       public @ResponseBody String readAllTemplatesXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAllTemplatesXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAllTemplatesXml, ActionType.READ, isResponseRequired, DataFormat.XML, TemplateDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Templates Read-All request: Service Request Created ");

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
               logger.info("Templates Read-All  Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }



      /**
        * Handles Read All Request of JSON format For Programs data
        * @param isResponseRequired
        * @param readAllTemplatesJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.GET, value="/templates" , headers = "Accept=application/json" )
       public @ResponseBody String readAllTemplatesJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAllTemplatesJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAllTemplatesJson, ActionType.READ, isResponseRequired, DataFormat.JSON, TemplateDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Templates Read All request: Service Request Created ");

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
               logger.info("Templates Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }
}
