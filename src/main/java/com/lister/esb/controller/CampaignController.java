package com.lister.esb.controller;

import com.lister.esb.dto.CampaignDTO;
import com.lister.esb.dto.ProgramDTO;
import com.lister.esb.dto.CampaignProgramDTO;
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
 * Handles all the Campaign Related Request
 */
@Controller
public class CampaignController {

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
     * Handles Update JSON format Requests For Campaigns
     * @param isResponseRequired
     * @param updateCampaignsJson
     * @return
     */
    @RequestMapping(value = "/campaigns", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody String updateCampaignsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateCampaignsJson) {

        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateCampaignsJson, ActionType.UPDATE, isResponseRequired, DataFormat.JSON, CampaignDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Campaign request: Service Request Created ");

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
            logger.info("Campaign Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }


    /**
     * Handles the Json Format request for Creating Campaigns
     * @param isResponseRequired
     * @param createCampaignJson
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value = "/campaigns" ,headers = "Accept=application/json" )
    public @ResponseBody String createCampaignJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createCampaignJson)
    {

        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createCampaignJson, ActionType.CREATE, isResponseRequired, DataFormat.JSON, CampaignDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Campaign request: Service Request Created ");

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
            logger.info("Campaign Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }


    /**
        * Handles Delete Request of Json format For Customer data
        * @param isResponseRequired
        * @param deleteCampaignsJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/campaigns" , headers = "Accept=application/json" )
       public @ResponseBody String deleteCampaignsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deleteCampaignsJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deleteCampaignsJson, ActionType.DELETE, isResponseRequired, DataFormat.JSON, CampaignDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Campaign Delete request: Service Request Created ");

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
               logger.info("Campaign Delete Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }

    /**
     * Handles Update XML format Requests For Campaigns
     * @param isResponseRequired
     * @param updateCampaignsXml
     * @return
     */
    @RequestMapping(value="/campaigns", method= RequestMethod.PUT ,headers = "Accept=application/xml")
    public @ResponseBody String updateCampaignsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateCampaignsXml) {

        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateCampaignsXml, ActionType.UPDATE, isResponseRequired, DataFormat.XML, CampaignDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Campaign Update request: Service Request Created ");
        logger.info(updateCampaignsXml);
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
            logger.info("Campaign Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }

    /**
     * Handles the XML Format request for Creating Campaigns
     * @param isResponseRequired
     * @param createMembersXml
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value="/campaigns" ,headers = "Accept=application/xml" )
    public @ResponseBody String createCampaignsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired ,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName ,@RequestBody String createMembersXml)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createMembersXml, ActionType.CREATE, isResponseRequired, DataFormat.XML, CampaignDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Campaign request: Service Request Created ");
        logger.info(createMembersXml);
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
            logger.info("Campaign Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }

        /**
        * Handles Delete Request of XML format For Campaign data
        * @param isResponseRequired
        * @param deleteCampaignsXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/campaigns" , headers = "Accept=application/xml" )
       public @ResponseBody String deleteCampaignsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deleteCampaignsXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deleteCampaignsXml, ActionType.DELETE, isResponseRequired, DataFormat.XML, CampaignDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Campaign Delete request: Service Request Created ");

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
               logger.info("Campaign Delete Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }


        /**
        * Handles Read Request of XML format For Campaign data
        * @param isResponseRequired
        * @param readCampaignsXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/campaignsInfo" , headers = "Accept=application/xml" )
       public @ResponseBody String readCampaignsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readCampaignsXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readCampaignsXml, ActionType.READ, isResponseRequired, DataFormat.XML, CampaignDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Campaign Read request: Service Request Created ");

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
               logger.info("Campaign Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }

        /**
        * Handles Read Request of JSON format For Campaign data
        * @param isResponseRequired
        * @param readCampaignsJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/campaignsInfo" , headers = "Accept=application/json" )
       public @ResponseBody String readCampaignsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readCampaignsJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readCampaignsJson, ActionType.READ, isResponseRequired, DataFormat.JSON, CampaignDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Campaign Read request: Service Request Created ");

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
               logger.info("Campaign Read Request : Sending the input request to the MQ");
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
     * Logging exceptions related to Campaign[XML] request
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
        * Handles Read All Request of XML format For Campaigns data
        * @param isResponseRequired
        * @param readAllCampaignsXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.GET, value="/campaigns" , headers = "Accept=application/xml" )
       public @ResponseBody String readAllCampaignsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAllCampaignsXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAllCampaignsXml, ActionType.READ, isResponseRequired, DataFormat.XML, CampaignDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Campaigns Read-All request: Service Request Created ");

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
               logger.info("Campaigns Read-All  Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }



      /**
        * Handles Read All Request of JSON format For Programs data
        * @param isResponseRequired
        * @param readAllCampaignsJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.GET, value="/campaigns" , headers = "Accept=application/json" )
       public @ResponseBody String readAllCampaignsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAllCampaignsJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAllCampaignsJson, ActionType.READ, isResponseRequired, DataFormat.JSON, CampaignDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Campaigns Read All request: Service Request Created ");

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
               logger.info("Campaigns Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }


    /**
     * Handles Read All Campaigns for a particular program
     * @param isResponseRequired
     * @param readAllCampaignProgramsJson
     * @return
     * @throws Exception
     */
    @RequestMapping(method=RequestMethod.POST, value="/campaignPrograms" , headers = "Accept=application/json" )
    public @ResponseBody String readAllCampaignProgramsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAllCampaignProgramsJson)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAllCampaignProgramsJson, ActionType.READ, isResponseRequired, DataFormat.JSON, CampaignProgramDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Campaign Program Read All request: Service Request Created ");

        String responseJsonString=null;
        if(isResponseRequired)
        {
            //Process synchronous requests

            responseJsonString=conversionUtils.convertToJsonString(requestDelegator.transfer(serviceRequest));

        }
        else
        {   //Sending the input request to the MQ
            logger.info("Campaign Program Read Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }

}
