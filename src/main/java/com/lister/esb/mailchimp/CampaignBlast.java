package com.lister.esb.mailchimp;

import com.ecwid.mailchimp.*;
import com.ecwid.mailchimp.MailChimpException;
import com.ecwid.mailchimp.method.campaign.*;
import com.ecwid.mailchimp.method.list.*;

import java.lang.*;

import java.util.Map;
import java.util.Iterator;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import java.util.*;
import java.sql.*;

import com.lister.esb.MailchimpWrapper.*;

/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 2/5/13
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */

@Service
public class CampaignBlast {
    MailChimpClient mailChimpClient = new MailChimpClient();
    private String apiKey = "451f483c468c7da110156343f748c48c-us3";
    private String listId = "f60ae8c6bb";
    List<String> emailList = new ArrayList<String>();
    Connection con;
    Statement sql;
    ResultSet result;
    String sqlText;
    Boolean False=new Boolean(false);
    Boolean True=new Boolean(true);

    public String getListId(){
        return this.listId;
    }

    public String getApiKey(){
        return this.apiKey;
    }

    public static class MergeVars extends MailChimpObject {
        public MergeVars() { }
    }

    public static class CampaignOpts extends MailChimpObject{
        public CampaignOpts(){ }
    }

    public static class TemplateContent extends MailChimpObject{
        public TemplateContent(){ }
    }

    public static class SegmentOpts extends MailChimpObject{

        public SegmentOpts(){ }

        @Field
        String match;

        @Field
        List<SegmentConditions> conditions;
    }

    public static class SegmentConditions extends MailChimpObject{

        @Field
        String field;

        @Field
        String op;

        @Field
        int value;

        public SegmentConditions(){ }
    }

    private static Logger logger = LoggerFactory.getLogger(CampaignBlast.class);

    protected Connection connectToDB() throws ClassNotFoundException, SQLException
    {
        Connection con;
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/udm_training","root","root");
        return con;
    }

    /*
    * Get Live launch Ids for a particular run
    * */
    @Scheduled(fixedRate = 600000)
    public void getLiveLaunchIds(){
        logger.info("Starting Mailchimp Cron...");
        int ctr = 0;
        String launchId;
        int segmentId;
        String campaignId;

        try
        {
            con = connectToDB();
            sql = con.createStatement();
            sqlText = "Select * from final_launchIds";
            result = sql.executeQuery(sqlText);
            while(result.next()) {
                launchId = result.getString("LAUNCH_ID");
                logger.info("Launch id -  "+launchId);

                addMergeVars(launchId);  // Add Merge vars to the List
                listBatchSubscribe(launchId); // Batch Subscribe members to the list
                segmentId = listStaticSegmentAdd(launchId); //Add a static Segment to the list
                if(segmentId!=0){
                    listStaticSegmentMembersAdd(segmentId);
                    //campaignSegmentTest(segmentId);
                    campaignId = campaignCreate(launchId,segmentId);
                    logger.info("campaign Created :"+campaignId);
                    if(campaignId!=""){
                        sendCampaign(campaignId);
                        sqlText = "update hceq_campaign_template_map set MAILCHIMP_LAUNCH_ID = '"+campaignId+"' WHERE LIVE_CAMPAIGN_ID = "+launchId;
                        sql.executeUpdate(sqlText);
                    }
                }

            }

            sqlText = "truncate final_launchIds";
            result = sql.executeQuery(sqlText);
            sqlText = "truncate final_campaign_ids";
            result = sql.executeQuery(sqlText);

        }catch(SQLException s){
            logger.info(s.toString());
        }catch(ClassNotFoundException e){
            logger.info(e.toString());
        }
    }


    /*
    * Add Merge Vars to the list
    */
    public void addMergeVars(String launchId){
        ResultSet mergeResult;
        try
        {
            con = connectToDB();
            sql = con.createStatement();
            sqlText = "SELECT ALIAS,COLUMN_NAME FROM "+launchId+"_attributes";
            mergeResult = sql.executeQuery(sqlText);

            while(mergeResult.next()) {

                logger.info(mergeResult.getString("ALIAS")+" "+mergeResult.getString("COLUMN_NAME"));
                ListMergeVarAddMethod listMergeVarAddMethod = new ListMergeVarAddMethod();
                listMergeVarAddMethod.apikey = this.getApiKey();
                listMergeVarAddMethod.id = this.getListId();
                listMergeVarAddMethod.tag = mergeResult.getString("ALIAS");
                listMergeVarAddMethod.name = mergeResult.getString("COLUMN_NAME");

                try{
                    mailChimpClient.execute(listMergeVarAddMethod);
                    logger.info("Merge vars added");
                }catch(MailChimpException me){
                    logger.info(me.message);
                }catch (java.io.IOException ioException){
                    logger.info("IO Exception");
                }

            }

        }catch(SQLException s){
            logger.info(s.toString()+"Outer");
        }catch(ClassNotFoundException e){
            logger.info(e.toString());
        }

    }

    /*
    * Subscribe Members to the List
    */
    public void listBatchSubscribe(String launchId){
        ResultSet listResult;
        ResultSetMetaData listResultMetaData;
        List<MergeVars> batchMembers = new ArrayList<MergeVars>();

        try
        {
            con = connectToDB();
            sql = con.createStatement();
            sqlText = "Select * from "+launchId+"_mailchimp";
            listResult = sql.executeQuery(sqlText);
            listResultMetaData = listResult.getMetaData();
            int columnCount = listResultMetaData.getColumnCount();

            ListBatchSubscribeMethod listBatchSubscribeMethod = new ListBatchSubscribeMethod();
            listBatchSubscribeMethod.apikey = this.getApiKey();
            listBatchSubscribeMethod.id = this.getListId();
            listBatchSubscribeMethod.double_optin=this.False;
            listBatchSubscribeMethod.update_existing=this.True;

            while(listResult.next()) {
                emailList.add(listResult.getString("EMAIL"));
                MergeVars mv = new MergeVars();
                for(int i=1;i<=columnCount;i++){
                    mv.put(listResultMetaData.getColumnName(i),(Object)listResult.getString(i));
                    logger.info(listResultMetaData.getColumnName(i)+"=>"+listResult.getString(i));
                }
                batchMembers.add(mv);

            }

            listBatchSubscribeMethod.batch = batchMembers;

            try{
                    mailChimpClient.execute(listBatchSubscribeMethod);
            }catch(MailChimpException me){
                    logger.info(me.message);
            }catch (java.io.IOException ioException){
                    logger.info("IO Exception");
            }

        }catch(SQLException s){
            logger.info(s.toString()+"Outer");
        }catch(ClassNotFoundException e){
            logger.info(e.toString());
        }
    }

    /*
    * Add a Static Segment To the List
    */
    public int listStaticSegmentAdd(String launchId){

        Integer segmentId = new Integer(0);
        ListStaticSegmentAddMethod listStaticSegmentAddMethod =  new ListStaticSegmentAddMethod();
        listStaticSegmentAddMethod.apikey=this.getApiKey();
        listStaticSegmentAddMethod.id=this.getListId();
        listStaticSegmentAddMethod.name="Segment_"+launchId;
        try{
            segmentId = mailChimpClient.execute(listStaticSegmentAddMethod);
            logger.info("segment created");
        }catch(MailChimpException me){
            logger.info(me.message);
        }catch (java.io.IOException ioException){
            logger.info("IO Exception");
        }

        return segmentId.intValue();
    }

    /*
    *  Add members to the static segment
    */
    public void listStaticSegmentMembersAdd(int segmentId){
        ListStaticSegmentMembersAddMethod listStaticSegmentMembersAddMethod = new ListStaticSegmentMembersAddMethod();
        listStaticSegmentMembersAddMethod.apikey=this.getApiKey();
        listStaticSegmentMembersAddMethod.id=this.getListId();
        listStaticSegmentMembersAddMethod.seg_id=new Integer(segmentId);
        listStaticSegmentMembersAddMethod.batch=this.emailList;
        try{
            mailChimpClient.execute(listStaticSegmentMembersAddMethod);
            logger.info("Members successfully added to the segment");
        }catch(MailChimpException me){
            logger.info(me.message);
        }catch (java.io.IOException ioException){
            logger.info("IO Exception");
        }
    }

    /*
    * Campaign Segment CampaignBlast
    */
    public void campaignSegmentTest(int segmentId){
        //java.util.Map<String,String> cond = new HashMap<String,String>();

        SegmentOpts segmentOpts = new SegmentOpts();
        segmentOpts.match = "all";
        segmentOpts.conditions = new ArrayList<SegmentConditions>();
        SegmentConditions segmentConditions = new SegmentConditions();
        segmentConditions.field="static_segment";
        segmentConditions.op="eq";
        segmentConditions.value=segmentId;
        segmentOpts.conditions.add(segmentConditions);
        CampaignSegmentTestMethod campaignSegmentTestMethod = new CampaignSegmentTestMethod();
        campaignSegmentTestMethod.apikey=this.getApiKey();
        campaignSegmentTestMethod.list_id=this.getListId();
        campaignSegmentTestMethod.options = segmentOpts;
        try{
                    mailChimpClient.execute(campaignSegmentTestMethod);
                    logger.info("Campaign Succesfully tested ofr the segment");
                 }catch(MailChimpException me){
                    logger.info(me.message);
                 }catch (java.io.IOException ioException){
                    logger.info("IO Exception");
                 }
    }

    /*
    * Create a new Campaign
    */
    public String campaignCreate(String launchId, int segmentId){
        ResultSet templateResult;
        String campaignId="";
        CampaignOpts campaignOpts = new CampaignOpts();
        TemplateContent templateContent = new TemplateContent();
        SegmentOpts segmentOpts = new SegmentOpts();


        try
        {
            con = connectToDB();
            sql = con.createStatement();
            sqlText = "SELECT * FROM hceq_campaign_template_map WHERE LIVE_CAMPAIGN_ID = "+launchId;
            templateResult = sql.executeQuery(sqlText);
             while(templateResult.next()) {

                 // template content
                 templateContent.put("html",(Object)templateResult.getString("DYNAMIC_CONTENT"));
                 templateContent.put("text",(Object)templateResult.getString("DYNAMIC_CONTENT"));

                 // campaign options
                 campaignOpts.put("subject",(Object)templateResult.getString("MAIL_SUBJECT"));
                 campaignOpts.put("from_email",(Object)"rajeev90km@gmail.com");
                 campaignOpts.put("from_name",(Object)"Rajeev");
                 campaignOpts.put("title",(Object)"CAMPAIGN_"+launchId);
                 campaignOpts.put("list_id",(Object)this.getListId());
                 //segment options

                 segmentOpts.match = "all";
                 segmentOpts.conditions = new ArrayList<SegmentConditions>();
                 SegmentConditions segmentConditions = new SegmentConditions();
                 segmentConditions.field="static_segment";
                 segmentConditions.op="eq";
                 segmentConditions.value=segmentId;
                 segmentOpts.conditions.add(segmentConditions);

                 //campaign create
                 CampaignCreateMethod campaignCreateMethod = new CampaignCreateMethod();
                 campaignCreateMethod.apikey=this.getApiKey();
                 campaignCreateMethod.content=templateContent;
                 campaignCreateMethod.options=campaignOpts;
                 campaignCreateMethod.segment_opts=segmentOpts;
                 campaignCreateMethod.type=CampaignType.regular;

                 try{
                    campaignId =  mailChimpClient.execute(campaignCreateMethod);
                    logger.info("Campaign Succesfully created");
                 }catch(MailChimpException me){
                    logger.info(me.message);
                 }catch (java.io.IOException ioException){
                    logger.info("IO Exception");
                 }


             }
        }catch(SQLException s){
            logger.info(s.toString()+"Outer");
        }catch(ClassNotFoundException e){
            logger.info(e.toString());
        }

       return campaignId;
    }


    public void sendCampaign(String campaignId){
        CampaignSendNowMethod campaignSendNowMethod = new CampaignSendNowMethod();
        campaignSendNowMethod.apikey=this.getApiKey();
        campaignSendNowMethod.cid=campaignId;
        try{
            mailChimpClient.execute(campaignSendNowMethod);
            logger.info("Campaign Mails Succesfully Sent");
        }catch(MailChimpException me){
            logger.info(me.message);
        }catch (java.io.IOException ioException){
            logger.info("IO Exception");
        }
    }

   // @Scheduled(fixedRate = 5000)
    public void campaignStats(){

        CampaignClickStatsMethod campaignClickStatsMethod = new CampaignClickStatsMethod();
        Map<String,Map<String,Long>> campaignClickStatsInfoMap = new HashMap<String, Map<String,Long>>();

        campaignClickStatsMethod.apikey =this.getApiKey();
        campaignClickStatsMethod.cid = "40555cfae6";

        try{
            campaignClickStatsInfoMap = mailChimpClient.execute(campaignClickStatsMethod);

//            logger.info(" "+campaignClickStatsInfoMap);
            for (Map.Entry<String, Map<String,Long>> entry : campaignClickStatsInfoMap.entrySet()) {
                logger.info("Key : " + entry.getKey() + " Value : "
                        + entry.getValue().get("clicks"));
            }


        }catch(MailChimpException me){
            logger.info(me.message);
        }catch (java.io.IOException ioException){
            logger.info("IO Exception");
        }
    }

}
