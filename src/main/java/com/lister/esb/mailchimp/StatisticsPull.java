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
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 22/3/13
 * Time: 11:55 AM
 * To change this template use File | Settings | File Templates.
 */

@Service
public class StatisticsPull {

    MailChimpClient mailChimpClient = new MailChimpClient();

    @Autowired
    private Properties mailchimpconfig;

    Connection con;

    Statement sql;

    ResultSet result;

    String sqlText;

    Boolean False=new Boolean(false);

    Boolean True=new Boolean(true);

    private static Logger logger = LoggerFactory.getLogger(StatisticsPull.class);

    protected Connection connectToDB() throws ClassNotFoundException, SQLException
    {
        Connection con;
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/udm1","root","root");
        return con;
    }

    //@Scheduled(fixedRate = 5000)
    public void getCampaignStats(){
        logger.info("HI");
        String launchId;
        String liveCampaignId;
        String campaignId;
        String programId;

        try
        {
            con = connectToDB();
            sql = con.createStatement();
            sqlText = "Select * from campaign_launch_dtl";
            result = sql.executeQuery(sqlText);
            while(result.next()) {

                launchId = result.getString("mailchimp_launch_id");
                liveCampaignId = result.getString("live_campaign_id");
                campaignId = result.getString("campaign_id");
                programId = result.getString("program_id");

                // Campaign Master Stats and Time series info
                pullCampaignMasterStats(launchId,liveCampaignId,campaignId,programId);

                //Campaign Domain Performance
                pullCampaignDomainPerfStats(launchId,liveCampaignId,campaignId,programId);

                //Campaign GEO Opens Stats
                pullCampaignGeoPerfStats(launchId,liveCampaignId,campaignId,programId);

                // Campaign Click Stats
                pullCampaignClickStats(launchId,liveCampaignId,campaignId,programId);

            }

        }catch(SQLException s){
            logger.info(s.toString());
        }catch(ClassNotFoundException e){
            logger.info(e.toString());
        }
    }

    // Campaign Master Stats and Time series info
    public void pullCampaignMasterStats(String launchId, String liveCampaignId, String campaignId, String programId){

        Statement stmt,stmt1;
        String stmtTxt,stmtTxt1;
        CampaignStatsMethod campaignStatsMethod = new CampaignStatsMethod();
        CampaignStatsResult campaignStatsResult = new CampaignStatsResult();
        List<CampaignStatsTimeSeriesResult> campaignStatsTimeSeriesResult = new ArrayList<CampaignStatsTimeSeriesResult>();

        campaignStatsMethod.apikey = mailchimpconfig.getProperty("apiKey");
        campaignStatsMethod.cid  = launchId;

        try{
            campaignStatsResult = mailChimpClient.execute(campaignStatsMethod);
            campaignStatsTimeSeriesResult = campaignStatsResult.timeseries;

            logger.info("Campaign Stats Obtained: ");
            try
            {
                stmt = con.createStatement();
                stmtTxt = "call event_campaign_master_stats("+liveCampaignId+",'"+launchId+"',"+campaignId+","+programId+","+campaignStatsResult.opens+","+campaignStatsResult.clicks+","+campaignStatsResult.forwards+","+campaignStatsResult.hard_bounces+","+campaignStatsResult.soft_bounces+","+campaignStatsResult.unsubscribes+","+campaignStatsResult.abuse_reports+","+campaignStatsResult.unique_opens+","+campaignStatsResult.unique_clicks+","+campaignStatsResult.emails_sent+",'"+campaignStatsResult.last_open+"')";
                logger.info(stmtTxt);
                stmt.executeQuery(stmtTxt);
            }catch(SQLException s){
                logger.info(s.toString());
            }

            for(int i=0;i<campaignStatsTimeSeriesResult.size();i++){
                try
                {
                    stmt1 = con.createStatement();
                    stmtTxt1 = "call event_campaign_time_series("+liveCampaignId+",'"+campaignStatsTimeSeriesResult.get(i).timestamp+"',"+campaignStatsTimeSeriesResult.get(i).emails_sent+","+campaignStatsTimeSeriesResult.get(i).unique_opens+","+campaignStatsTimeSeriesResult.get(i).recipients_click+")";
                    logger.info(stmtTxt1);
                    stmt1.executeQuery(stmtTxt1);
                }catch(SQLException s){
                    logger.info(s.toString());
                }
            }

        }catch(MailChimpException me){
            logger.info(me.message);
        }catch (java.io.IOException ioException){
            logger.info("IO Exception");
        }

    }


    //Campaign Domain Performance
    public void pullCampaignDomainPerfStats(String launchId,String liveCampaignId, String campaignId, String programId){

        Statement stmt;
        String stmtTxt;

        CampaignEmailDomainPerformanceMethod campaignEmailDomainPerformanceMethod = new CampaignEmailDomainPerformanceMethod();
        CampaignEmailDomainPerformanceResult campaignEmailDomainPerformanceResult = new CampaignEmailDomainPerformanceResult();

        campaignEmailDomainPerformanceMethod.apikey = mailchimpconfig.getProperty("apiKey");
        campaignEmailDomainPerformanceMethod.cid  = launchId;

        try{
            campaignEmailDomainPerformanceResult = mailChimpClient.execute(campaignEmailDomainPerformanceMethod);

            for(int i=0;i<campaignEmailDomainPerformanceResult.size();i++){
                try
                {
                    stmt = con.createStatement();
                    stmtTxt = "call event_campaign_domain_perf("+liveCampaignId+",'"+campaignId+"',"+programId+",'"+campaignEmailDomainPerformanceResult.get(i).domain+"',"+campaignEmailDomainPerformanceResult.get(i).emails+","+campaignEmailDomainPerformanceResult.get(i).opens+","+campaignEmailDomainPerformanceResult.get(i).clicks+","+campaignEmailDomainPerformanceResult.get(i).unsubs+","+campaignEmailDomainPerformanceResult.get(i).bounces+","+campaignEmailDomainPerformanceResult.get(i).emails_pct+","+campaignEmailDomainPerformanceResult.get(i).opens_pct+","+campaignEmailDomainPerformanceResult.get(i).clicks_pct+","+campaignEmailDomainPerformanceResult.get(i).unsubs_pct+","+campaignEmailDomainPerformanceResult.get(i).bounces_pct+")";
                    logger.info(stmtTxt);
                    stmt.executeQuery(stmtTxt);
                }catch(SQLException s){
                    logger.info(s.toString());
                }
            }

        }catch(MailChimpException me){
            logger.info(me.message);
        }catch (java.io.IOException ioException){
            logger.info("IO Exception");
        }

    }


    //Campaign GEO Opens Stats
    public void pullCampaignGeoPerfStats(String launchId, String liveCampaignId, String campaignId, String programId){

        Statement stmt;
        String stmtTxt;

        CampaignGeoOpensMethod campaignGeoOpensMethod = new CampaignGeoOpensMethod();
        CampaignGeoOpensResultMethod campaignGeoOpensResultMethod = new CampaignGeoOpensResultMethod();

        campaignGeoOpensMethod.apikey = mailchimpconfig.getProperty("apiKey");
        campaignGeoOpensMethod.cid = launchId;

        try{
            campaignGeoOpensResultMethod = mailChimpClient.execute(campaignGeoOpensMethod);

            for(int i=0;i<campaignGeoOpensResultMethod.size();i++){
                try
                {
                    stmt = con.createStatement();
                    stmtTxt = "call event_campaign_geo_perf("+liveCampaignId+",'"+campaignId+"',"+programId+",'"+campaignGeoOpensResultMethod.get(i).code+"','"+campaignGeoOpensResultMethod.get(i).name+"',"+campaignGeoOpensResultMethod.get(i).opens+")";
                    logger.info(stmtTxt);
                    stmt.executeQuery(stmtTxt);
                }catch(SQLException s){
                    logger.info(s.toString());
                }
            }

        }catch(MailChimpException me){
            logger.info(me.message);
        }catch (java.io.IOException ioException){
            logger.info("IO Exception");
        }
    }


    // Campaign Click Stats
    public void pullCampaignClickStats(String launchId, String liveCampaignId, String campaignId, String programId){

//        Statement stmt;
//        String stmtTxt;
//
//        CampaignClickStatsMethod campaignClickStatsMethod = new CampaignClickStatsMethod();
//        Map<String,Map<String,Long>> campaignClickStatsInfoMap = new HashMap<String, Map<String,Long>>();
//
//        campaignClickStatsMethod.apikey =this.getApiKey();
//        campaignClickStatsMethod.cid = launchId;
//
//        try{
//            campaignClickStatsInfoMap = mailChimpClient.execute(campaignClickStatsMethod);
//
//            for (Map.Entry<String, Map<String,Long>> entry : campaignClickStatsInfoMap.entrySet()) {
//                logger.info("Key : " + entry.getKey() + " Value : "
//                        + entry.getValue().get("clicks"));
//                try
//                {
//                    stmt = con.createStatement();
//                    stmtTxt = "call event_campaign_url_stats("+liveCampaignId+",'"+campaignId+"',"+programId+",'"+entry.getKey()+"',"++")";
//                    logger.info(stmtTxt);
//                    stmt.executeQuery(stmtTxt);
//                }catch(SQLException s){
//                    logger.info(s.toString());
//                }
//            }
//
//
//        }catch(MailChimpException me){
//            logger.info(me.message);
//        }catch (java.io.IOException ioException){
//            logger.info("IO Exception");
//        }

    }

}
