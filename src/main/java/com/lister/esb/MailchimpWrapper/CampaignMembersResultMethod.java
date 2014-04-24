package com.lister.esb.MailchimpWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 23/3/13
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class CampaignMembersResultMethod extends com.ecwid.mailchimp.MailChimpObject{

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer total;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.util.List<CampaignMemberRecordsMethod> data;

}
