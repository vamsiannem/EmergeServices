package com.lister.esb.MailchimpWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 23/3/13
 * Time: 1:33 PM
 * To change this template use File | Settings | File Templates.
 */

@com.ecwid.mailchimp.MailChimpMethod.Name("campaignMembers")
public class CampaignMembersMethod extends com.ecwid.mailchimp.method.campaign.CampaingRelatedMethod<CampaignMembersResultMethod>{

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.String status;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer start;

    @com.ecwid.mailchimp.MailChimpObject.Field
    public java.lang.Integer limit;

    @Override
    public java.lang.Class<CampaignMembersResultMethod> getResultType() {
        return CampaignMembersResultMethod.class;
    }
}
