package com.lister.esb.MailchimpWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 24/3/13
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */

@com.ecwid.mailchimp.MailChimpMethod.Name("campaignClickStats")
public class CampaignClickStatsMethod extends com.ecwid.mailchimp.method.campaign.CampaingRelatedMethod<java.util.Map>{

    @Override
    public java.lang.Class<java.util.Map> getResultType() {
        return java.util.Map.class;
    }

}
