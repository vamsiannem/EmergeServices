package com.lister.esb.controller;

import com.lister.esb.dto.MetadataDTO;
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
 * Date: 1/16/13
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class MetadataController {

    private static Logger logger = LoggerFactory.getLogger(MetadataController.class);

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
        * Handles Read Request of JSON format For displaying Metadata of the table
        * @param isResponseRequired
        * @param readMetadataJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/metadata" , headers = "Accept=application/json" )
       public @ResponseBody String readMetadataJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readMetadataJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readMetadataJson, ActionType.READ, isResponseRequired, DataFormat.JSON, MetadataDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Metadata Read request: Service Request Created ");

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


    /**
        * Handles Read Request of XML format For displaying columns of the table
        * @param isResponseRequired
        * @param readMetadataXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/metadata" , headers = "Accept=application/xml" )
       public @ResponseBody String readMetadataXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readMetadataXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readMetadataXml, ActionType.READ, isResponseRequired, DataFormat.XML, MetadataDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Metadata Read request: Service Request Created ");

           String responseXmlString=null;
           if(isResponseRequired)
           {
               //Process synchronous requests
               try {
                   responseXmlString=conversionUtils.convertToXmlString(requestDelegator.transfer(serviceRequest),"metadataDTO");
               } catch (IOException e) {
                   throw new XmlException(ESBConstants.EX_RESPONSE_XML_FMT);
               }
           }
           else
           {   //Sending the input request to the MQ
               logger.info("Metadata Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }


    /**
     * Handles Read Request of JSON format For displaying Metadata of the table
     * @param isResponseRequired
     * @param readAllMetadataJson
     * @return
     * @throws Exception
     */
    @RequestMapping(method=RequestMethod.GET, value="/metadata" , headers = "Accept=application/json" )
    public @ResponseBody String readAllMetadataJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readAllMetadataJson)
    {
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readAllMetadataJson, ActionType.READ, isResponseRequired, DataFormat.JSON, MetadataDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Metadata Read All request: Service Request Created ");

        String responseJsonString=null;
        if(isResponseRequired)
        {
            //Process synchronous requests

            responseJsonString=conversionUtils.convertToJsonString(requestDelegator.transfer(serviceRequest));

        }
        else
        {   //Sending the input request to the MQ
            logger.info("Metadata Read  All Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        return responseJsonString;
    }
}
