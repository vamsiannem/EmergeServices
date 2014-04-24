package com.lister.esb.MailchimpWrapper;

/**
 * Created by IntelliJ IDEA.
 * User: aishwarya_r
 * Date: 1/8/14
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class CampaignsResultMethod extends com.ecwid.mailchimp.MailChimpObject{

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer total;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.util.List<CampaignsResultInfoMethod> data;
}
