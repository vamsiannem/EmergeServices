package com.lister.esb.MailchimpWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 22/3/13
 * Time: 2:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class CampaignStatsTimeSeriesResult extends com.ecwid.mailchimp.MailChimpObject {

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.String timestamp;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer emails_sent;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer unique_opens;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer recipients_click;
}
