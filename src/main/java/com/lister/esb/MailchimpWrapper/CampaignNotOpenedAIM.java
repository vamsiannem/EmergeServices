package com.lister.esb.MailchimpWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 24/3/13
 * Time: 7:19 PM
 * To change this template use File | Settings | File Templates.
 */

@com.ecwid.mailchimp.MailChimpMethod.Name("campaignNotOpenedAIM")
public class CampaignNotOpenedAIM extends com.ecwid.mailchimp.method.campaign.CampaingRelatedMethod<CampaignNotOpenedAIMResult>{

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer start;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer limit;

    @Override
    public java.lang.Class<CampaignNotOpenedAIMResult> getResultType() {
        return CampaignNotOpenedAIMResult.class;
    }

}
