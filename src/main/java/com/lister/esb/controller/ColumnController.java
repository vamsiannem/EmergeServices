package com.lister.esb.controller;

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
 * Date: 12/31/12
 * Time: 10:33 AM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class ColumnController {

    private static Logger logger = LoggerFactory.getLogger(ColumnController.class);

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
        * Handles Read Request of JSON format For displaying columns of the table
        * @param isResponseRequired
        * @param readColumnsJson
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/columns" , headers = "Accept=application/json" )
       public @ResponseBody String readColumnsJson(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readColumnsJson)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readColumnsJson, ActionType.READ, isResponseRequired, DataFormat.JSON, ColumnDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Column Read request: Service Request Created ");

           String responseJsonString=null;
           if(isResponseRequired)
           {
               //Process synchronous requests

                   responseJsonString=conversionUtils.convertToJsonString(requestDelegator.transfer(serviceRequest));

           }
           else
           {   //Sending the input request to the MQ
               logger.info("Column Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseJsonString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseJsonString;
       }


    /**
        * Handles Read Request of XML format For displaying columns of the table
        * @param isResponseRequired
        * @param readColumnsXml
        * @return
        * @throws Exception
        */
       @RequestMapping(method=RequestMethod.POST, value="/columns" , headers = "Accept=application/xml" )
       public @ResponseBody String readColumnsXml(@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String readColumnsXml)
       {
           //(wraps up information to be passed to the Next Layer)
           ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(readColumnsXml, ActionType.READ, isResponseRequired, DataFormat.XML, ColumnDTO.class, SourceSystem.MARKET,storedProcedureName);
           logger.info("Columns Read request: Service Request Created ");

           String responseXmlString=null;
           if(isResponseRequired)
           {
               //Process synchronous requests
               try {
                   responseXmlString=conversionUtils.convertToXmlString(requestDelegator.transfer(serviceRequest),"columnDTO");
               } catch (IOException e) {
                   throw new XmlException(ESBConstants.EX_RESPONSE_XML_FMT);
               }
           }
           else
           {   //Sending the input request to the MQ
               logger.info("Columns Read Request : Sending the input request to the MQ");
               jmsTemplate.convertAndSend(serviceRequest);
               responseXmlString=ESBConstants.RESPONSE_SUCCESS;
           }
           return responseXmlString;
       }
}
