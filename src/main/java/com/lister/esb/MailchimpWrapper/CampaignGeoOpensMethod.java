package com.lister.esb.MailchimpWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 24/3/13
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */

@com.ecwid.mailchimp.MailChimpMethod.Name("campaignGeoOpens")
public class CampaignGeoOpensMethod extends com.ecwid.mailchimp.method.campaign.CampaingRelatedMethod<CampaignGeoOpensResultMethod>{

    @Override
    public java.lang.Class<CampaignGeoOpensResultMethod> getResultType() {
        return CampaignGeoOpensResultMethod.class;
    }

}
