package com.lister.esb.MailchimpWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 22/3/13
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class CampaignStatsResult extends com.ecwid.mailchimp.MailChimpObject{

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer syntax_errors;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer hard_bounces;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer soft_bounces;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer unsubscribes;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer abuse_reports;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer forwards;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer forward_opens;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer opens;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.String last_open;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer unique_opens;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer clicks;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer unique_clicks;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.String last_click;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer users_who_clicked;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer emails_sent;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer unique_likes;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer recipient_likes;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer facebook_likes;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.util.List<CampaignStatsABSplitResult> absplit;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.util.List<CampaignStatsTimeWarpResult> timewarp;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.util.List<CampaignStatsTimeSeriesResult> timeseries;

}
