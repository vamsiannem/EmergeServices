package com.lister.esb.MailchimpWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 24/3/13
 * Time: 11:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class CampaignUnsubscribesResultMethod extends com.ecwid.mailchimp.MailChimpObject{

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer total;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.util.List<CampaignUnsubscribedEmailsMethod> data;
}
