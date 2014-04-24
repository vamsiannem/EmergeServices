package com.lister.esb.MailchimpWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 24/3/13
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */

@com.ecwid.mailchimp.MailChimpMethod.Name("campaignEmailDomainPerformance")
public class CampaignEmailDomainPerformanceMethod extends com.ecwid.mailchimp.method.campaign.CampaingRelatedMethod<CampaignEmailDomainPerformanceResult>{

    @Override
    public java.lang.Class<CampaignEmailDomainPerformanceResult> getResultType() {
        return CampaignEmailDomainPerformanceResult.class;
    }

}
