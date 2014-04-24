package com.lister.esb.MailchimpWrapper;

/**
 * Created by IntelliJ IDEA.
 * User: aishwarya_r
 * Date: 1/8/14
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */

@com.ecwid.mailchimp.MailChimpMethod.Name("campaigns")
public class CampaignsMethod extends com.ecwid.mailchimp.method.campaign.CampaingRelatedMethod<CampaignsResultMethod>{

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer limit;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer start;


    @Override
    public java.lang.Class<CampaignsResultMethod> getResultType() {
        return CampaignsResultMethod.class;
    }

}
