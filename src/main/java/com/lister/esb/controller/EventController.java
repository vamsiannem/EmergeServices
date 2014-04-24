package com.lister.esb.controller;

import org.springframework.data.repository.core.support.PropertiesBasedNamedQueries;
import com.ecwid.mailchimp.*;
import com.ecwid.mailchimp.MailChimpException;
import com.ecwid.mailchimp.internal.guava.Objects;
import com.ecwid.mailchimp.method.campaign.*;
import com.ecwid.mailchimp.method.list.*;
import com.ecwid.mailchimp.method.list.ListMemberInfoResult;
import com.ecwid.mailchimp.method.list.ListSubscribeMethod;
import com.ecwid.mailchimp.method.list.MemberInfo;
import java.lang.*;
import java.io.*;
import java.io.IOException;

import org.apache.commons.beanutils.converters.IntegerArrayConverter;
import org.apache.commons.beanutils.converters.StringArrayConverter;
import org.apache.commons.collections.iterators.ArrayListIterator;
import org.apache.commons.collections.map.HashedMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lister.esb.service.UDMService;
import org.springmodules.validation.util.condition.Condition;
import org.springmodules.validation.valang.functions.AddFunction;

import java.util.*;
import java.lang.*;
import java.sql.*;
import javax.persistence.criteria.Predicate;
import javax.transaction.SystemException;
import javax.validation.OverridesAttribute;

import java.util.List;

import com.lister.esb.MailchimpWrapper.*;

/* IMPORTS FOR JAVA SCHEDULER CRON*/
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Async;


/*IMPORTS FOR MAIL CHIMP API CALLS*/
import com.lister.esb.mailchimp.MailchimpAPI;


import com.lister.esb.dto.EventDTO;
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

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 7/1/14
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */

@Service
public class EventController {

    private static Logger logger = LoggerFactory.getLogger(EventController.class);

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

    /**
     * Handles Creation Request of JSON format For Event data
     * @param isResponseRequired
     * @param createEventsJson
     * @return
     */
    //@RequestMapping(method=RequestMethod.POST, value="/events" ,headers = "Accept=application/json" )
   @Scheduled(fixedRate = 500000)
    public void createEventsJson()
    {
       fetchOpenStats();
       fetchClickStats();
       fetchSentBounceStats();
       callCampaignEventAggregator();
       fetchLastOpenAndClickDate();
    }

    public void fetchOpenStats(){
        String apikey = mailchimpconfig.getProperty("apiKey");

        //Campaign List API
        CampaignsResultMethod campaignsResultMethod = new CampaignsResultMethod();
        List<CampaignsResultInfoMethod> campaignsResultInfoMethod = new ArrayList<CampaignsResultInfoMethod>();

        //Campaign Members API
        CampaignMembersResultMethod campaignMembersResultMethod = new CampaignMembersResultMethod();
        List<CampaignMemberRecordsMethod> campaignMemberRecordsMethod = new ArrayList<CampaignMemberRecordsMethod>();

        //Campaign OPENED Emails details API
        CampaignOpenedAIMResult campaignOpenedAIMResult = new CampaignOpenedAIMResult();
        List<CampaignOpenedAIMResultInfo> campaignOpenedAIMResultInfos = new ArrayList<CampaignOpenedAIMResultInfo>();

        MailchimpAPI mailchimpAPI = new MailchimpAPI(apikey);

        //retrieve list of campaigns
        campaignsResultMethod = mailchimpAPI.getCampaignList();

        //retrieve individual ids
        campaignsResultInfoMethod = campaignsResultMethod.data;

        //for(int i=0;i<3;i++){
        for(int i=0;i<campaignsResultInfoMethod.size();i++){

            String campaignId = campaignsResultInfoMethod.get(i).id;
            campaignOpenedAIMResult = mailchimpAPI.getCampaignOpenStats(campaignId);
            campaignOpenedAIMResultInfos = campaignOpenedAIMResult.data;

            for(int j=0;j<campaignOpenedAIMResultInfos.size();j++){
                String createEventsJson1 = "[{\"primaryEmail\":\""+campaignOpenedAIMResultInfos.get(j).email+"\",\"totalOpens\":\""+campaignOpenedAIMResultInfos.get(j).open_count+"\",\"launchId\":\""+campaignId+"\"}]";
                boolean isResponseRequired = false;
                String storedProcedureName = "opensInterProc";
                //(wraps up information to be passed to the Next Layer)
                ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createEventsJson1, ActionType.CREATE, isResponseRequired, DataFormat.JSON, EventDTO.class, SourceSystem.MARKET,storedProcedureName);
                logger.info("Events request: Opens Count Request  ");

                String responseJsonString="Success";
                if(isResponseRequired)
                {
                    // Process synchronous requests
                    try {
                        responseJsonString=conversionUtils.convertObjectToJson(requestDelegator.transfer(serviceRequest));
                    }
                    catch (IOException e) {
                        throw new JsonException(ESBConstants.EX_RESPONSE_JSON_FMT);
                    } catch (JSONException e) {
                        throw new JsonException(ESBConstants.EX_RESPONSE_JSON_FMT);
                    }
                }
                else
                {
                    // Sending the input request to the MQ
                    logger.info("Event Request : ( Opens) Sending the input request to the MQ");
                    jmsTemplate.convertAndSend(serviceRequest);
                    responseJsonString=ESBConstants.RESPONSE_SUCCESS;
                }
                logger.info("Insert Event Success: "+responseJsonString);
                //return responseJsonString;
                logger.info(campaignId+" -> "+campaignOpenedAIMResultInfos.get(j).email+" : "+campaignOpenedAIMResultInfos.get(j).open_count);
            }

        }
    }


    public void fetchClickStats(){
       String apikey = mailchimpconfig.getProperty("apiKey");

        //Campaign List API
        CampaignsResultMethod campaignsResultMethod = new CampaignsResultMethod();
        List<CampaignsResultInfoMethod> campaignsResultInfoMethod = new ArrayList<CampaignsResultInfoMethod>();

        //Campaign Members API
        CampaignMembersResultMethod campaignMembersResultMethod = new CampaignMembersResultMethod();
        List<CampaignMemberRecordsMethod> campaignMemberRecordsMethod = new ArrayList<CampaignMemberRecordsMethod>();

        //Campaign Click Emails details API
        Map campaignClickStatsResult = new HashMap();

        //list of emails, that clicked on a given URL, and how many times they clicked
        CampaignClickDetailAIMResult campaignClickDetailAIMResult = new CampaignClickDetailAIMResult();
        List<CampaignClickDetailAIMInfo> campaignClickDetailAIMInfo = new ArrayList<CampaignClickDetailAIMInfo>();

        MailchimpAPI mailchimpAPI = new MailchimpAPI(apikey);

        //retrieve list of campaigns
        campaignsResultMethod = mailchimpAPI.getCampaignList();

        //retrieve individual ids
        campaignsResultInfoMethod = campaignsResultMethod.data;

        //for(int i=0;i<3;i++){
        for(int i=0;i<campaignsResultInfoMethod.size();i++){

            String campaignId = campaignsResultInfoMethod.get(i).id;
            campaignClickStatsResult = mailchimpAPI.getCampaignClickStats(campaignId);
            ArrayList<String> campaignUrls = new ArrayList<String>();

            //stores email Ids with cumulative clicks for a campaign across multiple URLs
            HashMap emailClickDetails = new HashMap();

            Iterator iter = campaignClickStatsResult.keySet().iterator();
            while(iter.hasNext()){
                campaignUrls.add((String)iter.next());
            }

            for(int j=0;j<campaignUrls.size();j++){
               campaignClickDetailAIMResult = mailchimpAPI.getEmailsForURL(campaignId,campaignUrls.get(j));
               campaignClickDetailAIMInfo = campaignClickDetailAIMResult.data;

               for(int k=0;k<campaignClickDetailAIMInfo.size();k++){
                  int totalClicks = emailClickDetails.containsKey(campaignClickDetailAIMInfo.get(k).email) ? (Integer)emailClickDetails.get(campaignClickDetailAIMInfo.get(k).email) : 0;
                  emailClickDetails.put(campaignClickDetailAIMInfo.get(k).email,totalClicks + (Integer)campaignClickDetailAIMInfo.get(k).clicks);
               }

            }

            Iterator EmailIter = emailClickDetails.keySet().iterator();
            while(EmailIter.hasNext()) {
                String emailKey = (String)EmailIter.next();
                Integer clickCount = (Integer)emailClickDetails.get(emailKey);

                //Insert into inter table
                String createEventsJson1 = "[{\"primaryEmail\":\""+emailKey+"\",\"totalClicks\":\""+clickCount+"\",\"launchId\":\""+campaignId+"\"}]";
                boolean isResponseRequired = false;
                String storedProcedureName = "clicksInterProc";
                //(wraps up information to be passed to the Next Layer)
                ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createEventsJson1, ActionType.CREATE, isResponseRequired, DataFormat.JSON, EventDTO.class, SourceSystem.MARKET,storedProcedureName);
                logger.info("Events request: Clicks Count Request Created ");

                String responseJsonString="Success";
                if(isResponseRequired)
                {
                    // Process synchronous requests
                    try {
                        responseJsonString=conversionUtils.convertObjectToJson(requestDelegator.transfer(serviceRequest));
                    }
                    catch (IOException e) {
                        throw new JsonException(ESBConstants.EX_RESPONSE_JSON_FMT);
                    } catch (JSONException e) {
                        throw new JsonException(ESBConstants.EX_RESPONSE_JSON_FMT);
                    }
                }
                else
                {
                    // Sending the input request to the MQ
                    logger.info("Event (Clicks) Request : Sending the input request to the MQ");
                    jmsTemplate.convertAndSend(serviceRequest);
                    responseJsonString=ESBConstants.RESPONSE_SUCCESS;
                }
                logger.info("Insert Clicks Event Success: "+responseJsonString);
                //return responseJsonString;
                logger.info("Click stats : " + emailKey + "," + clickCount);
            }
        }
    }


    //Fetch Sent , BOUNCES
    public void fetchSentBounceStats(){
        String apikey = mailchimpconfig.getProperty("apiKey");

        //Campaign List API
        CampaignsResultMethod campaignsResultMethod = new CampaignsResultMethod();
        List<CampaignsResultInfoMethod> campaignsResultInfoMethod = new ArrayList<CampaignsResultInfoMethod>();

        //Campaign Members API
        CampaignMembersResultMethod campaignMembersResultMethod = new CampaignMembersResultMethod();
        List<CampaignMemberRecordsMethod> campaignMemberRecordsMethod = new ArrayList<CampaignMemberRecordsMethod>();

        //Campaign OPENED Emails details API
        //CampaignOpenedAIMResult campaignOpenedAIMResult = new CampaignOpenedAIMResult();
        //List<CampaignOpenedAIMResultInfo> campaignOpenedAIMResultInfos = new ArrayList<CampaignOpenedAIMResultInfo>();

        MailchimpAPI mailchimpAPI = new MailchimpAPI(apikey);

        //retrieve list of campaigns
        campaignsResultMethod = mailchimpAPI.getCampaignList();

        //retrieve individual ids
        campaignsResultInfoMethod = campaignsResultMethod.data;

        for(int i=0;i<campaignsResultInfoMethod.size();i++){

            String campaignId = campaignsResultInfoMethod.get(i).id;
            campaignMembersResultMethod = mailchimpAPI.getCampaignMembers(campaignId);
            campaignMemberRecordsMethod = campaignMembersResultMethod.data;

            for(int j=0;j<campaignMemberRecordsMethod.size();j++){
                String createEventsJson1;
                String storedProcedureName;
                logger.info(campaignMemberRecordsMethod.get(j).email+" : "+ campaignMemberRecordsMethod.get(j).status+" ");
                if ((campaignMemberRecordsMethod.get(j).status).equals("sent")){
                    createEventsJson1 = "[{\"primaryEmail\":\""+campaignMemberRecordsMethod.get(j).email+"\",\"totalSent\":\"1\""+",\"launchId\":\""+campaignId+"\"}]";
                    storedProcedureName = "sentInterProc";
                }else{
                    createEventsJson1 = "[{\"primaryEmail\":\""+campaignMemberRecordsMethod.get(j).email+"\",\"totalBounces\":\"1\""+",\"launchId\":\""+campaignId+"\"}]";
                    storedProcedureName = "bounceInterProc";
                }

                logger.info(createEventsJson1+" ");
                boolean isResponseRequired = false;
                //(wraps up information to be passed to the Next Layer)
                ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createEventsJson1, ActionType.CREATE, isResponseRequired, DataFormat.JSON, EventDTO.class, SourceSystem.MARKET,storedProcedureName);
                logger.info("Events request: Service Request Created ");

                String responseJsonString="Success";
                if(isResponseRequired)
                {
                    // Process synchronous requests
                    try {
                        responseJsonString=conversionUtils.convertObjectToJson(requestDelegator.transfer(serviceRequest));
                    }
                    catch (IOException e) {
                        throw new JsonException(ESBConstants.EX_RESPONSE_JSON_FMT);
                    } catch (JSONException e) {
                        throw new JsonException(ESBConstants.EX_RESPONSE_JSON_FMT);
                    }
                }
                else
                {
                    // Sending the input request to the MQ
                    logger.info("Event Request : Sending the input request to the MQ");
                    jmsTemplate.convertAndSend(serviceRequest);
                    responseJsonString=ESBConstants.RESPONSE_SUCCESS;
                }
                logger.info("Insert Event Success: "+responseJsonString);
                //return responseJsonString;
            }

        }
    }

    public void fetchLastOpenAndClickDate(){
        String apikey = mailchimpconfig.getProperty("apiKey");

        //Campaign List API
        CampaignsResultMethod campaignsResultMethod = new CampaignsResultMethod();
        List<CampaignsResultInfoMethod> campaignsResultInfoMethod = new ArrayList<CampaignsResultInfoMethod>();

        //Retrieve the opens and clicks history with timestamp for all users of a given campaign
        CampaignEmailStatsAIMAllResult campaignEmailStatsAIMAllResult = new CampaignEmailStatsAIMAllResult();
        Map<String, List<Map>> campaignEmailStatsAIMAllInfo = new HashMap<String, List<Map>>();

        //Campaign OPENED Emails details API
        //CampaignOpenedAIMResult campaignOpenedAIMResult = new CampaignOpenedAIMResult();
        //List<CampaignOpenedAIMResultInfo> campaignOpenedAIMResultInfos = new ArrayList<CampaignOpenedAIMResultInfo>();

        MailchimpAPI mailchimpAPI = new MailchimpAPI(apikey);

        //retrieve list of campaigns
        campaignsResultMethod = mailchimpAPI.getCampaignList();

        //retrieve individual ids
        campaignsResultInfoMethod = campaignsResultMethod.data;

        for(int i=0;i<campaignsResultInfoMethod.size();i++){

            String campaignId = campaignsResultInfoMethod.get(i).id;

            campaignEmailStatsAIMAllResult = mailchimpAPI.getLastOpenClickHistory(campaignId);

            campaignEmailStatsAIMAllInfo = campaignEmailStatsAIMAllResult.data;
           // System.out.println(campaignEmailStatsAIMAllInfo);
            Iterator emailClickOpenHistory = campaignEmailStatsAIMAllInfo.keySet().iterator();
            for (Map.Entry<String, List<Map>> campaignEmailStatsAIMAllInfoDetail : campaignEmailStatsAIMAllInfo.entrySet()) {

                String emailKey = campaignEmailStatsAIMAllInfoDetail.getKey();
                List<Map> emailHistory = campaignEmailStatsAIMAllInfoDetail.getValue();

                Map emailLastClicksOpenStats = new HashMap();

                for(int j=0;j<emailHistory.size();j++){
                    emailLastClicksOpenStats.put(emailHistory.get(j).get("action"),emailHistory.get(j).get("timestamp"));
                }

                //Call proc for opens
                if(emailLastClicksOpenStats.get("open")!=null){
                    String createEventsJson1;
                    String storedProcedureName;

                    createEventsJson1 = "[{\"primaryEmail\":\""+emailKey+"\",\"lastOpenDate\":\""+emailLastClicksOpenStats.get("open")+"\""+",\"launchId\":\""+campaignId+"\"}]";
                    storedProcedureName = "lastOpenInterProc";

                    logger.info(createEventsJson1+" ");
                    boolean isResponseRequired = false;
                    //(wraps up information to be passed to the Next Layer)
                    ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createEventsJson1, ActionType.CREATE, isResponseRequired, DataFormat.JSON, EventDTO.class, SourceSystem.MARKET,storedProcedureName);
                    logger.info("Events request: Service Request Created ");

                    String responseJsonString="Success";
                    if(isResponseRequired)
                    {
                        // Process synchronous requests
                        try {
                            responseJsonString=conversionUtils.convertObjectToJson(requestDelegator.transfer(serviceRequest));
                        }
                        catch (IOException e) {
                            throw new JsonException(ESBConstants.EX_RESPONSE_JSON_FMT);
                        } catch (JSONException e) {
                            throw new JsonException(ESBConstants.EX_RESPONSE_JSON_FMT);
                        }
                    }
                    else
                    {
                        // Sending the input request to the MQ
                        logger.info("Event Request : Sending the input request to the MQ");
                        jmsTemplate.convertAndSend(serviceRequest);
                        responseJsonString=ESBConstants.RESPONSE_SUCCESS;
                    }
                    logger.info("Insert Event Success: "+responseJsonString);
                    //return responseJsonString;
                }


                //Call proc for clicks
                if(emailLastClicksOpenStats.get("click")!=null){
                    String createEventsJson1;
                    String storedProcedureName;

                    createEventsJson1 = "[{\"primaryEmail\":\""+emailKey+"\",\"lastClickDate\":\""+emailLastClicksOpenStats.get("click")+"\""+",\"launchId\":\""+campaignId+"\"}]";
                    storedProcedureName = "lastClickInterProc";

                    logger.info(createEventsJson1+" ");
                    boolean isResponseRequired = false;
                    //(wraps up information to be passed to the Next Layer)
                    ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createEventsJson1, ActionType.CREATE, isResponseRequired, DataFormat.JSON, EventDTO.class, SourceSystem.MARKET,storedProcedureName);
                    logger.info("Events request: Service Request Created ");

                    String responseJsonString="Success";
                    if(isResponseRequired)
                    {
                        // Process synchronous requests
                        try {
                            responseJsonString=conversionUtils.convertObjectToJson(requestDelegator.transfer(serviceRequest));
                        }
                        catch (IOException e) {
                            throw new JsonException(ESBConstants.EX_RESPONSE_JSON_FMT);
                        } catch (JSONException e) {
                            throw new JsonException(ESBConstants.EX_RESPONSE_JSON_FMT);
                        }
                    }
                    else
                    {
                        // Sending the input request to the MQ
                        logger.info("Event Request : Sending the input request to the MQ");
                        jmsTemplate.convertAndSend(serviceRequest);
                        responseJsonString=ESBConstants.RESPONSE_SUCCESS;
                    }
                    logger.info("Insert Event Success: "+responseJsonString);
                    //return responseJsonString;
                }

            }

        }
    }

    public void callCampaignEventAggregator(){
        //Insert into inter table
        String createEventsJson1 = "";
        boolean isResponseRequired = false;
        String storedProcedureName = "campaignEventMap";
        //(wraps up information to be passed to the Next Layer)
        ServiceRequest serviceRequest = serviceRequestFactoryBean.createServiceRequest(createEventsJson1, ActionType.CREATE, isResponseRequired, DataFormat.JSON, EventDTO.class, SourceSystem.MARKET,storedProcedureName);
        logger.info("Events request: Clicks Count Request Created ");

        String responseJsonString="Success";
        if(isResponseRequired)
        {
            // Process synchronous requests
            try {
                responseJsonString=conversionUtils.convertObjectToJson(requestDelegator.transfer(serviceRequest));
            }
            catch (IOException e) {
                throw new JsonException(ESBConstants.EX_RESPONSE_JSON_FMT);
            } catch (JSONException e) {
                throw new JsonException(ESBConstants.EX_RESPONSE_JSON_FMT);
            }
        }
        else
        {
            // Sending the input request to the MQ
            logger.info("Event (Clicks) Request : Sending the input request to the MQ");
            jmsTemplate.convertAndSend(serviceRequest);
            responseJsonString=ESBConstants.RESPONSE_SUCCESS;
        }
        logger.info("Campaign event map Success: "+responseJsonString);
    }
}
