package com.lister.esb.MailchimpWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 24/3/13
 * Time: 7:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class CampaignOpenedAIMResult extends com.ecwid.mailchimp.MailChimpObject{

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer total;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.util.List<CampaignOpenedAIMResultInfo> data;

}
