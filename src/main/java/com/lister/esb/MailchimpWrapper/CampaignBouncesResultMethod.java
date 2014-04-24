package com.lister.esb.MailchimpWrapper;


/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 23/3/13
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class CampaignBouncesResultMethod extends com.ecwid.mailchimp.MailChimpObject{

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer total;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.util.List<? extends com.ecwid.mailchimp.MailChimpObject> data;
}
