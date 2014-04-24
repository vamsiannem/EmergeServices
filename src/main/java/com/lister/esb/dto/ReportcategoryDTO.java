package com.lister.esb.dto;

import org.apache.commons.beanutils.converters.StringArrayConverter;
import org.springframework.aop.interceptor.SimpleTraceInterceptor;

import javax.persistence.GeneratedValue;
import java.util.Date;
/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 2/14/13
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReportcategoryDTO extends BaseDTO {

    private int liveCampaignId;

    private String mailchimpLaunchId;

    private int campaignId;

    private int programId;

    private int opens;

    private int unopened;

    private int clicks;

    private int unsubscribers;

    private int hardBounces;

    private int softBounces;

    private int lastOpenDate;

    private int totalEmailsSent;

    public int getLiveCampaignId() {
        return liveCampaignId;
    }

    public void setLiveCampaignId(int liveCampaignId) {
        this.liveCampaignId = liveCampaignId;
    }

    public String getMailchimpLaunchId() {
        return mailchimpLaunchId;
    }

    public void setMailchimpLaunchId(String mailchimpLaunchId) {
        this.mailchimpLaunchId = mailchimpLaunchId;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getOpens() {
        return opens;
    }

    public void setOpens(int opens) {
        this.opens = opens;
    }

    public int getUnopened() {
        return unopened;
    }

    public void setUnopened(int unopened) {
        this.unopened = unopened;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getUnsubscribers() {
        return unsubscribers;
    }

    public void setUnsubscribers(int unsubscribers) {
        this.unsubscribers = unsubscribers;
    }

    public int getHardBounces() {
        return hardBounces;
    }

    public void setHardBounces(int hardBounces) {
        this.hardBounces = hardBounces;
    }

    public int getSoftBounces() {
        return softBounces;
    }

    public void setSoftBounces(int softBounces) {
        this.softBounces = softBounces;
    }

    public int getLastOpenDate() {
        return lastOpenDate;
    }

    public void setLastOpenDate(int lastOpenDate) {
        this.lastOpenDate = lastOpenDate;
    }

    public int getTotalEmailsSent() {
        return totalEmailsSent;
    }

    public void setTotalEmailsSent(int totalEmailsSent) {
        this.totalEmailsSent = totalEmailsSent;
    }
}
