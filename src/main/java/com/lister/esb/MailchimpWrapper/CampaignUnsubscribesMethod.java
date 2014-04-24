package com.lister.esb.MailchimpWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 24/3/13
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */

@com.ecwid.mailchimp.MailChimpMethod.Name("campaignUnsubscribes")
public class CampaignUnsubscribesMethod extends com.ecwid.mailchimp.method.campaign.CampaingRelatedMethod<CampaignUnsubscribesResultMethod>{

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer start;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer limit;

    @Override
    public java.lang.Class<CampaignUnsubscribesResultMethod> getResultType() {
        return CampaignUnsubscribesResultMethod.class;
    }

}
