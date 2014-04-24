package com.lister.esb.MailchimpWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 24/3/13
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */

@com.ecwid.mailchimp.MailChimpMethod.Name("campaignEmailStatsAIMAll")
public class CampaignEmailStatsAIMAllMethod extends com.ecwid.mailchimp.method.campaign.CampaingRelatedMethod<CampaignEmailStatsAIMAllResult>{

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer start;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer limit;

    @Override
    public java.lang.Class<CampaignEmailStatsAIMAllResult> getResultType() {
        return CampaignEmailStatsAIMAllResult.class;
    }

}
