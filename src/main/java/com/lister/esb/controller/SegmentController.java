package com.lister.esb.controller;

import com.lister.esb.dto.SegmentDTO;
import com.lister.esb.dto.TestsegmentDTO;
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
 *  Handles all the Segment Related Request
 */


/**
 * Created by IntelliJ IDEA.
 * User: bhavani_r
 * Date: 11/22/12
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class SegmentController {
      private static Logger logger = LoggerFactory.getLogger(SegmentController.class);

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
     * Handles Update JSON format Requests For Segment data
     * @param isResponseRequired
     * @param updateSegmentJson
     * @return
     */
    @RequestMapping(value="/segment", method= RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    String updateSegmentJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateSegmentJson)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateSegmentJson, ActionType.UPDATE,isResponseRequired, DataFormat.JSON, SegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Segment request: Service Request Created ");

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
            logger.info("Segment Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString= ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;

    }

    /**
     * Handles Creation Request of JSON format For Segment data
     * @param isResponseRequired
     * @param createCustomersJson
     * @return
     */
    @RequestMapping(method=RequestMethod.POST, value="/segment" ,headers = "Accept=application/json" )
    public @ResponseBody String createSegmentJson (@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createSegmentJson )
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createSegmentJson, ActionType.CREATE, isResponseRequired, DataFormat.JSON, SegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Segment request: Service Request Created ");

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
            logger.info("Segment Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }

     /**
        * Handles Delete Request of Json format For Segment data
        * @param isResponseRequired
        * @param deleteSegmentsJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/segment" , headers = "Accept=application/json" )
       public @ResponseBody String deleteSegmentsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deleteSegmentsJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deleteSegmentsJson, ActionType.DELETE, isResponseRequired, DataFormat.JSON, SegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Segment Delete request: Service Request Created ");

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
               logger.info("Segment Delete Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }


    /**
     * Handles Update XML format Requests For Segment data
     * @param isResponseRequired
     * @param updateSegmentXml
     * @return
     */
    @RequestMapping(value="/segment", method= RequestMethod.PUT, headers = "Accept=application/xml")
    public @ResponseBody String updateSegmentXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String updateSegmentXml)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(updateSegmentXml,ActionType.UPDATE,isResponseRequired, DataFormat.XML, SegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Segment request: Service Request Created ");

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
            logger.info("Segment Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }

    /**
     * Handles Creation Request of JSON format For Segment data
     * @param isResponseRequired
     * @param createSegmentXml
     * @return
     * @throws Exception
     */
    @RequestMapping(method=RequestMethod.POST, value="/segment" , headers = "Accept=application/xml" )
    public @ResponseBody String createSegmentXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createSegmentXml)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createSegmentXml, ActionType.CREATE, isResponseRequired, DataFormat.XML, SegmentDTO.class, SourceSystem.MARKET, storedProcedureName);
        logger.info("Segment request: Service Request Created ");

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
            logger.info("Segment Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseXmlString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseXmlString;
    }


    /**
        * Handles Delete Request of XML format For Segments data
        * @param isResponseRequired
        * @param deleteSegmentsXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.DELETE, value="/segment" , headers = "Accept=application/xml" )
       public @ResponseBody String deleteSegmentsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String deleteSegmentsXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(deleteSegmentsXml, ActionType.DELETE, isResponseRequired, DataFormat.XML, SegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Segments Delete request: Service Request Created ");

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
           {   //Sending the Segments request to the MQ
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
     * Logging exceptions related to Segment[XML] request
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
        * Handles Read Request of XML format For Segment data
        * @param isResponseRequired
        * @param readSegmentsXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/segmentInfo" , headers = "Accept=application/xml" )
       public @ResponseBody String readSegmentsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readSegmentsXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readSegmentsXml, ActionType.READ, isResponseRequired, DataFormat.XML, SegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Segment Read request: Service Request Created ");

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
               logger.info("Segment Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }

        /**
        * Handles Read Request of JSON format For Segment data
        * @param isResponseRequired
        * @param readSegmentsJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/segmentInfo" , headers = "Accept=application/json" )
       public @ResponseBody String readSegmentsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readSegmentsJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readSegmentsJson, ActionType.READ, isResponseRequired, DataFormat.JSON, SegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Segment Read request: Service Request Created ");

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
               logger.info("Segment Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }



    /**
        * Handles Read All Request of XML format For Programs data
        * @param isResponseRequired
        * @param readAllSegmentsXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.GET, value="/segment" , headers = "Accept=application/xml" )
       public @ResponseBody String readAllSegmentsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAllSegmentsXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAllSegmentsXml, ActionType.READ, isResponseRequired, DataFormat.XML, SegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Segments Read-All request: Service Request Created ");

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
               logger.info("Segments Read-All  Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }



      /**
        * Handles Read All Request of JSON format For Programs data
        * @param isResponseRequired
        * @param readAllSegmentsJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.GET, value="/segment" , headers = "Accept=application/json" )
       public @ResponseBody String readAllSegmentsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAllSegmentsJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAllSegmentsJson, ActionType.READ, isResponseRequired, DataFormat.JSON, SegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Segments Read All request: Service Request Created ");

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
               logger.info("Segments Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }


    /**
     * Handles Read Request of JSON format For testing the Segment query
     * @param isResponseRequired
     * @param testSegmentsJson
     * @return
     * @throws Exception
     */
    @RequestMapping(method=RequestMethod.POST, value="/testSegment" , headers = "Accept=application/json" )
    public @ResponseBody String testSegmentsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String testSegmentsJson)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(testSegmentsJson, ActionType.READ, isResponseRequired, DataFormat.JSON, TestsegmentDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Segment CampaignBlast request: Service Request Created ");

        String responseJsonString=null;
        if(isResponseRequired)
        {
            //Process synchronous requests
            responseJsonString=conversionUtils.convertToJsonString(requestDelegator.transfer(serviceRequest));
        }
        else
        {   //Sending the input request to the MQ
            logger.info("Segment CampaignBlast Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }
}
