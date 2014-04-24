package com.lister.esb.MailchimpWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 24/3/13
 * Time: 2:41 PM
 * To change this template use File | Settings | File Templates.
 */

@com.ecwid.mailchimp.MailChimpMethod.Name("campaignClickDetailAIM")
public class CampaignClickDetailAIMMethod extends com.ecwid.mailchimp.method.campaign.CampaingRelatedMethod<CampaignClickDetailAIMResult>{

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.String url;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer start;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer limit;

    @Override
    public java.lang.Class<CampaignClickDetailAIMResult> getResultType() {
        return CampaignClickDetailAIMResult.class;
    }

}
