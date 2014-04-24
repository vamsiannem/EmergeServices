package com.lister.esb.MailchimpWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 24/3/13
 * Time: 11:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class CampaignUnsubscribedEmailsMethod extends com.ecwid.mailchimp.MailChimpObject{

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.String email;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.String reason;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.String reason_text;
}
