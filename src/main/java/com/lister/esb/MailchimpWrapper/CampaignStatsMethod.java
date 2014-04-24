package com.lister.esb.MailchimpWrapper;



import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.MailChimpObject;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 22/3/13
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */

@MailChimpMethod.Name("campaignStats")
public class CampaignStatsMethod extends com.ecwid.mailchimp.method.campaign.CampaingRelatedMethod<CampaignStatsResult>{

    @Override
    public Class<CampaignStatsResult> getResultType() {
        return CampaignStatsResult.class;
    }

}
