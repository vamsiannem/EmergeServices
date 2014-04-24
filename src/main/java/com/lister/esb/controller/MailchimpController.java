package com.lister.esb.controller;

import com.lister.esb.enums.ActionType;
import com.lister.esb.enums.SourceSystem;
import com.lister.esb.exceptions.ESBException;
import org.springframework.data.repository.core.support.PropertiesBasedNamedQueries;
import com.lister.esb.mailchimp.MailchimpAPI;
import com.lister.esb.MailchimpWrapper.*;
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
import com.ecwid.mailchimp.*;
import java.util.*;
import java.lang.*;
import java.sql.*;
import java.lang.*;
import java.io.*;
import java.io.IOException;

import static com.lister.esb.utils.ESBConstants.EX_DATA_ACCESS;
import static com.lister.esb.utils.ESBConstants.EX_RESPONSE_JSON_FMT;
import static com.lister.esb.utils.ESBConstants.EX_XML_FMT;
/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 1/10/14
 * Time: 10:46 AM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class MailchimpController {
        private static Logger logger = LoggerFactory.getLogger(MailchimpController.class);

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

       @Autowired
       private Properties mailchimpconfig;

       @RequestMapping(method=RequestMethod.POST, value="/mailchimp" ,headers = "Accept=application/json" )
    public @ResponseBody void createCustomersJson (@RequestParam(value="isResponseRequired",required=true) Boolean isResponseRequired,@RequestParam(value="storedProcedureName",required=false) String storedProcedureName,@RequestBody String createCustomersJson )
    {
        String apikey = mailchimpconfig.getProperty("apiKey");
        MailchimpAPI mailchimpAPI = new MailchimpAPI(apikey);
        mailchimpAPI.getCampaignList();

    }
}
