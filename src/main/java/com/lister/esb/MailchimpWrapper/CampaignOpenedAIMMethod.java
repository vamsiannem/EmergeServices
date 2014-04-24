package com.lister.esb.MailchimpWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 24/3/13
 * Time: 7:44 PM
 * To change this template use File | Settings | File Templates.
 */

@com.ecwid.mailchimp.MailChimpMethod.Name("campaignOpenedAIM")
public class CampaignOpenedAIMMethod extends com.ecwid.mailchimp.method.campaign.CampaingRelatedMethod<CampaignOpenedAIMResult>{

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer start;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer limit;

    @Override
    public java.lang.Class<CampaignOpenedAIMResult> getResultType() {
        return CampaignOpenedAIMResult.class;
    }

}
