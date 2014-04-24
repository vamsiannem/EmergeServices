package com.lister.esb.mailchimp;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.support.PropertiesBasedNamedQueries;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
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

/**
 * Created by IntelliJ IDEA.
 * User: aishwarya_r
 * Date: 1/8/14
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class MailchimpAPI {

    MailChimpClient mailChimpClient = new MailChimpClient();

    private String apiKey;

    private String listId;

    Boolean False=new Boolean(false);

    Boolean True=new Boolean(true);

    public MailchimpAPI(String apikey){
        this.apiKey = apikey;
    }

    private static Logger logger = LoggerFactory.getLogger(MailchimpAPI.class);


    //Retrieve the list of campaigns ina list
    public CampaignsResultMethod getCampaignList(){

        CampaignsMethod campaignsMethod = new CampaignsMethod();
        campaignsMethod.apikey = this.apiKey;
        CampaignsResultMethod campaignsResultMethod = new CampaignsResultMethod();

        try{
            campaignsResultMethod = mailChimpClient.execute(campaignsMethod);
        }catch(MailChimpException me){
            logger.info(me.message);
        }catch (java.io.IOException ioException){
            logger.info("IO Exception");
        }

        return campaignsResultMethod;
    }

    //Retrieve Members to whom a campaign was sent to
    public CampaignMembersResultMethod getCampaignMembers(String campaignId){
        CampaignMembersMethod campaignMembersMethod = new CampaignMembersMethod();
        campaignMembersMethod.apikey = this.apiKey;
        campaignMembersMethod.cid = campaignId;
        CampaignMembersResultMethod campaignMembersResultMethod = new CampaignMembersResultMethod();

        try{
            campaignMembersResultMethod = mailChimpClient.execute(campaignMembersMethod);
        }catch(MailChimpException me){
            logger.info(me.message);
        }catch (java.io.IOException ioException){
            logger.info("IO Exception");
        }

        return campaignMembersResultMethod;
    }

    //Retrieve Campaign Open Status for all emails sent to a campaign who opened, given the campaign Id
    public CampaignOpenedAIMResult getCampaignOpenStats(String campaignId){
        CampaignOpenedAIMMethod campaignOpenedAIMMethod = new CampaignOpenedAIMMethod();
        CampaignOpenedAIMResult campaignOpenedAIMResult = new CampaignOpenedAIMResult();
        campaignOpenedAIMMethod.apikey = this.apiKey;
        campaignOpenedAIMMethod.cid = campaignId;

        try{
            campaignOpenedAIMResult = mailChimpClient.execute(campaignOpenedAIMMethod);
        }catch(MailChimpException me){
            logger.info(me.message);
        }catch (java.io.IOException ioException){
            logger.info("IO Exception");
        }

        return campaignOpenedAIMResult;
    }

    //Retrieve all Urls in a particular campaign, given the camapign ID
    public Map getCampaignClickStats(String campaignId){
        CampaignClickStatsMethod campaignClickStatsMethod = new CampaignClickStatsMethod();
        Map campaignClickStatsResult = new HashMap();

        campaignClickStatsMethod.apikey = this.apiKey;
        campaignClickStatsMethod.cid = campaignId;

        try{
            campaignClickStatsResult = mailChimpClient.execute(campaignClickStatsMethod);
        }catch(MailChimpException me){
            logger.info(me.message);
        }catch (java.io.IOException ioException){
            logger.info("IO Exception");
        }

        return campaignClickStatsResult;
    }


    //Retrieve list of emails, that clicked on a given URL, and how many times they clicked
    public CampaignClickDetailAIMResult getEmailsForURL(String campaignId, String url){
        CampaignClickDetailAIMMethod campaignClickDetailAIMMethod = new CampaignClickDetailAIMMethod();
        CampaignClickDetailAIMResult campaignClickDetailAIMResult = new CampaignClickDetailAIMResult();

        campaignClickDetailAIMMethod.apikey = this.apiKey;
        campaignClickDetailAIMMethod.cid = campaignId;
        campaignClickDetailAIMMethod.url = url;

        try{
            campaignClickDetailAIMResult = mailChimpClient.execute(campaignClickDetailAIMMethod);
        }catch(MailChimpException me){
            logger.info(me.message);
        }catch (java.io.IOException ioException){
            logger.info("IO Exception");
        }

        return campaignClickDetailAIMResult;
    }


    //Return the entire click and open history with timestamps of all emails ids in a particular campaign
    public CampaignEmailStatsAIMAllResult getLastOpenClickHistory(String campaignId){
        CampaignEmailStatsAIMAllMethod campaignEmailStatsAIMAllMethod = new CampaignEmailStatsAIMAllMethod();
        CampaignEmailStatsAIMAllResult campaignEmailStatsAIMAllResult = new CampaignEmailStatsAIMAllResult();

        campaignEmailStatsAIMAllMethod.apikey = this.apiKey;
        campaignEmailStatsAIMAllMethod.cid = campaignId;

        try{
            campaignEmailStatsAIMAllResult = mailChimpClient.execute(campaignEmailStatsAIMAllMethod);
        }catch(MailChimpException me){
            logger.info(me.message);
        }catch (java.io.IOException ioException){
            logger.info("IO Exception");
        }

        return campaignEmailStatsAIMAllResult;
    }
}
