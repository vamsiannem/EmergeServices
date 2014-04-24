package com.lister.esb.controller;

import com.lister.esb.dto.CampaignsegmentDTO;
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
 * Date: 11/26/12
 * Time: 11:18 AM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class CampaignsegmentController {

    private static Logger logger = LoggerFactory.getLogger(CampaignsegmentController.class);

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
     * Handles Update JSON format Requests For Segment Mapping
     * @param isResponseRequired
     * @param updateCampaignsegmentsJson
     * @return
     */
    @RequestMapping(value = "/campaignsegments", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody String updateCampaignsegmentsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateCampaignsegmentsJson) {

        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateCampaignsegmentsJson, ActionType.UPDATE, isResponseRequired, DataFormat.JSON, CampaignsegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Mapping request: Service Request Created ");

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
            logger.info("Mapping Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }


    /**
     * Handles the Json Format request for Creating Campaign Segment Mapping
     * @param isResponseRequired
     * @param createCampaignSegmentJson
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value = "/campaignsegments" ,headers = "Accept=application/json" )
    public @ResponseBody String createCampaignSegmentJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createCampaignSegmentJson)
    {

        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createCampaignSegmentJson, ActionType.CREATE, isResponseRequired, DataFormat.JSON, CampaignsegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Mapping request: Service Request Created ");

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
            logger.info("Mapping Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }


    /**
        * Handles Delete Request of Json format For Campaign Segment Mapping
        * @param isResponseRequired
        * @param deleteCampaignSegmentJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/campaignsegments" , headers = "Accept=application/json" )
       public @ResponseBody String deleteCampaignSegmentJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deleteCampaignSegmentJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deleteCampaignSegmentJson, ActionType.DELETE, isResponseRequired, DataFormat.JSON, CampaignsegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Campaign Segment Mapping Delete request: Service Request Created ");

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
               logger.info("Campaign Segment Mapping Delete Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }

    /**
     * Handles Update XML format Requests For Campaign Segment Mapping
     * @param isResponseRequired
     * @param updateCampaignsegmentsXml
     * @return
     */
    @RequestMapping(value="/campaignsegments", method= RequestMethod.PUT ,headers = "Accept=application/xml")
    public @ResponseBody String updateCampaignsegmentsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateCampaignsegmentsXml) {

        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateCampaignsegmentsXml, ActionType.UPDATE, isResponseRequired, DataFormat.XML, CampaignsegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Mapping request: Service Request Created ");
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
            logger.info("Mapping Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }

    /**
     * Handles the XML Format request for Creating Campaign Segment Mapping
     * @param isResponseRequired
     * @param createCampaignsegmentXml
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value="/campaignsegments" ,headers = "Accept=application/xml" )
    public @ResponseBody String createCampaignsegmentXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired ,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName ,@RequestBody String createCampaignsegmentXml)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createCampaignsegmentXml, ActionType.CREATE, isResponseRequired, DataFormat.XML, CampaignsegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Mapping request: Service Request Created ");
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
            logger.info("Mapping Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }


/**
        * Handles Delete Request of XML format For campaign segment mapping
        * @param isResponseRequired
        * @param deleteCampaignsegmentsXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/campaignsegments" , headers = "Accept=application/xml" )
       public @ResponseBody String deleteCampaignsegmentsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deleteCampaignsegmentsXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deleteCampaignsegmentsXml, ActionType.DELETE, isResponseRequired, DataFormat.XML, CampaignsegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("campaign segment mapping Delete request: Service Request Created ");

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
               logger.info("campaign segment mapping Delete Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }

        /**
        * Handles Read Request of XML format For Campaign Segments Mapping data
        * @param isResponseRequired
        * @param readCampaignsegmentsXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/campaignsegmentsInfo" , headers = "Accept=application/xml" )
       public @ResponseBody String readCampaignsegmentsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readCampaignsegmentsXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readCampaignsegmentsXml, ActionType.READ, isResponseRequired, DataFormat.XML, CampaignsegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Campaign Segment Mapping Read request: Service Request Created ");

           String responseXmlString=null;
           if(isResponseRequired)
           {
               //Process synchronous requests
               try {
                   responseXmlString=conversionUtils.convertToXmlString(requestDelegator.transfer(serviceRequest),"campaignsegmentDTO");
               } catch (IOException e) {
                   throw new XmlException(ESBConstants.EX_RESPONSE_XML_FMT);
               }
           }
           else
           {   //Sending the input request to the MQ
               logger.info("Campaign Segment Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }

        /**
        * Handles Read Request of JSON format For Campaign Segments Mapping data
        * @param isResponseRequired
        * @param readCampaignsegmentsJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/campaignsegmentsInfo" , headers = "Accept=application/json" )
       public @ResponseBody String readCampaignsegmentsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readCampaignsegmentsJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readCampaignsegmentsJson, ActionType.READ, isResponseRequired, DataFormat.JSON, CampaignsegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Campaign Segments Mapping Read request: Service Request Created ");

           String responseJsonString=null;
           if(isResponseRequired)
           {
               //Process synchronous requests

                   responseJsonString=conversionUtils.convertToJsonString(requestDelegator.transfer(serviceRequest));

           }
           else
           {   //Sending the input request to the MQ
               logger.info("Campaign Segments Mapping Read Request : Sending the input request to the MQ");
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
     * Logging exceptions related to Mapping[XML] request
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
