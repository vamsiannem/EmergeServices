package com.lister.esb.controller;

import java.security.Principal;

import com.lister.esb.dto.LoginDTO;
import com.lister.esb.dto.RegisterDTO;
import com.lister.esb.dto.ResetPasswordDTO;
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
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 4/6/13
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

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
    * Handles Read Request of XML format For Login data
    * @param isResponseRequired
    * @param readLoginXml
    * @return
    * @throws Exception
    */
   @RequestMapping(method=RequestMethod.POST, value="/login" , headers = "Accept=application/xml" )
   public @ResponseBody String readLoginXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readLoginXml)
   {
       //(wraps up information to be passed to the Next Layer)
       ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readLoginXml, ActionType.READ, isResponseRequired, DataFormat.XML, LoginDTO.class, SourceSystem.MARKET,storedProcedureName);
       logger.info("Login Read request: Service Request Created ");

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
           logger.info("Login Read Request : Sending the input request to the MQ");
           jmsTemplate.convertAndSend(serviceRequest);
           responseXmlString=ESBConstants.RESPONSE_SUCCESS;
       }
       return responseXmlString;
   }

    /**
    * Handles Read Request of XML format For Login data
    * @param isResponseRequired
    * @param readLoginJson
    * @return
    * @throws Exception
    */
   @RequestMapping(method=RequestMethod.POST, value="/login" , headers = "Accept=application/json" )
   public @ResponseBody String readLoginJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readLoginJson)
   {
       //(wraps up information to be passed to the Next Layer)
       ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readLoginJson, ActionType.READ, isResponseRequired, DataFormat.JSON, LoginDTO.class, SourceSystem.MARKET,storedProcedureName);
       logger.info("Login Read request: Service Request Created ");

       String responseJsonString=null;
       if(isResponseRequired)
       {
           //Process synchronous requests
          // try {
               responseJsonString=conversionUtils.convertToJsonString(requestDelegator.transfer(serviceRequest));
           //} catch (IOException e) {
            //throw new JsonException(EX_RESPONSE_JSON_FMT);
        //} catch (JSONException e) {
          //  throw new JsonException(EX_RESPONSE_JSON_FMT);
        //}
       }
       else
       {   //Sending the input request to the MQ
           logger.info("Login Read Request : Sending the input request to the MQ");
           jmsTemplate.convertAndSend(serviceRequest);
           responseJsonString=ESBConstants.RESPONSE_SUCCESS;
       }
       return responseJsonString;
   }
   	/**
     * Handles Creation Request of JSON format For Login data
     * @param isResponseRequired
     * @param createLoginXml
     * @return
     * @throws Exception
     */
    @RequestMapping(method=RequestMethod.POST, value="/register" , headers = "Accept=application/xml" )
    public @ResponseBody String createLoginXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createLoginXml)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createLoginXml, ActionType.CREATE, isResponseRequired, DataFormat.XML, LoginDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Login request: Service Request Created ");

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
            logger.info("Login Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }

	/**
     * Handles Creation Request of JSON format For Login data
     * @param isResponseRequired
     * @param createLoginJson
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value="/register" ,headers = "Accept=application/json" )
    public @ResponseBody String createLoginJson (@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createLoginJson )
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createLoginJson, ActionType.CREATE, isResponseRequired, DataFormat.JSON, LoginDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Login request: Service Request Created ");

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
            logger.info("Login Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }
    /**
        * Handles Update Request of XML format For Customer Purchase data
        * @param isResponseRequired
        * @param updateLoginJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/resetlogin" , headers = "Accept=application/json" )
       public @ResponseBody String updateLoginJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateLoginJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateLoginJson, ActionType.READ, isResponseRequired, DataFormat.JSON, ResetPasswordDTO.class, SourceSystem.MARKET,storedProcedureName);
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
        * @param updateLoginXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/resetlogin" , headers = "Accept=application/xml" )
       public @ResponseBody String updateLoginXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateLoginXml)
       {
             //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateLoginXml, ActionType.UPDATE, isResponseRequired, DataFormat.XML, ResetPasswordDTO.class, SourceSystem.MARKET,storedProcedureName);
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
