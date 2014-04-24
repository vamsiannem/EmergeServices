package com.lister.esb.controller;

import com.lister.esb.dto.CampaignDTO;
import com.lister.esb.dto.ProgramDTO;
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
 * User: Rajeev
 * Date: 11/16/12
 * Time: 1:15 AM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class ProgramController {
     private static Logger logger = LoggerFactory.getLogger(ProgramController.class);

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
     * Handles Update JSON format Requests For Programs
     * @param isResponseRequired
     * @param updateProgramsJson
     * @return
     */
    @RequestMapping(value = "/programs", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody String updateProgramsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateProgramsJson) {

        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateProgramsJson, ActionType.UPDATE, isResponseRequired, DataFormat.JSON, ProgramDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Program request: Service Request Created ");

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
            logger.info("Program Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }


    /**
     * Handles the Json Format request for Creating Program
     * @param isResponseRequired
     * @param createProgramJson
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value = "/programs" ,headers = "Accept=application/json" )
    public @ResponseBody String createProgramsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createProgramsJson)
    {

        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createProgramsJson, ActionType.CREATE, isResponseRequired, DataFormat.JSON, ProgramDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Program request: Service Request Created ");

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
            logger.info("Program Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }


    /**
        * Handles Delete Request of Json format For Programs data
        * @param isResponseRequired
        * @param deleteProgramsJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/programs" , headers = "Accept=application/json" )
       public @ResponseBody String deleteProgramsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deleteProgramsJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deleteProgramsJson, ActionType.DELETE, isResponseRequired, DataFormat.JSON, ProgramDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Programs Delete request: Service Request Created ");

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
               logger.info("Programs Delete Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }

    /**
     * Handles Update XML format Requests For Program
     * @param isResponseRequired
     * @param updateProgramsXml
     * @return
     */
    @RequestMapping(value="/programs", method= RequestMethod.PUT ,headers = "Accept=application/xml")
    public @ResponseBody String updateProgramsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateProgramsXml) {

        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateProgramsXml, ActionType.UPDATE, isResponseRequired, DataFormat.XML, ProgramDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Program request: Service Request Created ");
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
            logger.info("Program Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }

    /**
     * Handles the XML Format request for Creating Program
     * @param isResponseRequired
     * @param createMembersXml
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value="/programs" ,headers = "Accept=application/xml" )
    public @ResponseBody String createProgramsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired ,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName ,@RequestBody String createProgramsXml)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createProgramsXml, ActionType.CREATE, isResponseRequired, DataFormat.XML, ProgramDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Program request: Service Request Created ");
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
            logger.info("Program Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }

    /**
        * Handles Delete Request of XML format For Programs data
        * @param isResponseRequired
        * @param deleteProgramsXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/programs" , headers = "Accept=application/xml" )
       public @ResponseBody String deleteProgramsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deleteProgramsXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deleteProgramsXml, ActionType.DELETE, isResponseRequired, DataFormat.XML, ProgramDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Programs Delete request: Service Request Created ");

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
               logger.info("Programs Delete Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }


    /**
        * Handles Read Request of XML format For Programs data
        * @param isResponseRequired
        * @param readProgramsXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/programsInfo" , headers = "Accept=application/xml" )
       public @ResponseBody String readProgramsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readProgramsXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readProgramsXml, ActionType.READ, isResponseRequired, DataFormat.XML, ProgramDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Programs Read request: Service Request Created ");

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
               logger.info("Programs Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }

        /**
        * Handles Read Request of JSON format For Programs data
        * @param isResponseRequired
        * @param readProgramsJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/programsInfo" , headers = "Accept=application/json" )
       public @ResponseBody String readProgramsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readProgramsJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readProgramsJson, ActionType.READ, isResponseRequired, DataFormat.JSON, ProgramDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Programs Read request: Service Request Created ");

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
               logger.info("Programs Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }



    /**
        * Handles Read All Request of XML format For Programs data
        * @param isResponseRequired
        * @param readAllProgramsXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.GET, value="/programs" , headers = "Accept=application/xml" )
       public @ResponseBody String readAllProgramsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAllProgramsXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAllProgramsXml, ActionType.READ, isResponseRequired, DataFormat.XML, ProgramDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Programs Read-All request: Service Request Created ");

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
               logger.info("Programs Read-All  Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }



      /**
        * Handles Read All Request of JSON format For Programs data
        * @param isResponseRequired
        * @param readAllProgramsJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.GET, value="/programs" , headers = "Accept=application/json" )
       public @ResponseBody String readAllProgramsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAllProgramsJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAllProgramsJson, ActionType.READ, isResponseRequired, DataFormat.JSON, ProgramDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Programs Read All request: Service Request Created ");

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
               logger.info("Programs Read Request : Sending the input request to the MQ");
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
     * Logging exceptions related to Program[XML] request
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
