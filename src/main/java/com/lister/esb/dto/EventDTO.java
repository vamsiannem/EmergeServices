package com.lister.esb.dto;

import com.lister.esb.model.DataFormat;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

import java.util.Date;

/**
 * Customer related details.
 *
 * @author sudharsan_s
 *
 */
public class EventDTO extends BaseDTO{

    @NotNull
	private Long memberId;

    private String primaryEmail;

	private Long emCampaignId;

    private String launchId;

    private Long extCampId;

    private Date lastSentDate;

    private String totalSent;

    private Date lastOpenDate;

    private String totalOpens;

    private Date lastClickDate;

    private String totalClicks;

    private Date lastBounceDate;

    private String totalBounces;

    private Date lastSkipDate;

    private String totalSkips;

    private Date lastSpamDate;

    private String totalSpams;

    private Date lastUnsubscribeDate;

    private String totalUnsubscribes;

    private Date lastConversionDate;

    private String totalConversions;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private Long emMembersMemberId;

    public Long getMemberId() {
        return memberId;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getLaunchId() {
        return launchId;
    }

    public void setLaunchId(String launchId) {
        this.launchId = launchId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getEmCampaignId() {
        return emCampaignId;
    }

    public void setEmCampaignId(Long emCampaignId) {
        this.emCampaignId = emCampaignId;
    }

    public Long getExtCampId() {
        return extCampId;
    }

    public void setExtCampId(Long extCampId) {
        this.extCampId = extCampId;
    }

    public Date getLastSentDate() {
        return lastSentDate;
    }

    public void setLastSentDate(Date lastSentDate) {
        this.lastSentDate = lastSentDate;
    }

    public String getTotalSent() {
        return totalSent;
    }

    public void setTotalSent(String totalSent) {
        this.totalSent = totalSent;
    }

    public Date getLastOpenDate() {
        return lastOpenDate;
    }

    public void setLastOpenDate(Date lastOpenDate) {
        this.lastOpenDate = lastOpenDate;
    }

    public String getTotalOpens() {
        return totalOpens;
    }

    public void setTotalOpens(String totalOpens) {
        this.totalOpens = totalOpens;
    }

    public Date getLastClickDate() {
        return lastClickDate;
    }

    public void setLastClickDate(Date lastClickDate) {
        this.lastClickDate = lastClickDate;
    }

    public String getTotalClicks() {
        return totalClicks;
    }

    public void setTotalClicks(String totalClicks) {
        this.totalClicks = totalClicks;
    }

    public Date getLastBounceDate() {
        return lastBounceDate;
    }

    public void setLastBounceDate(Date lastBounceDate) {
        this.lastBounceDate = lastBounceDate;
    }

    public String getTotalBounces() {
        return totalBounces;
    }

    public void setTotalBounces(String totalBounces) {
        this.totalBounces = totalBounces;
    }

    public Date getLastSkipDate() {
        return lastSkipDate;
    }

    public void setLastSkipDate(Date lastSkipDate) {
        this.lastSkipDate = lastSkipDate;
    }

    public String getTotalSkips() {
        return totalSkips;
    }

    public void setTotalSkips(String totalSkips) {
        this.totalSkips = totalSkips;
    }

    public Date getLastSpamDate() {
        return lastSpamDate;
    }

    public void setLastSpamDate(Date lastSpamDate) {
        this.lastSpamDate = lastSpamDate;
    }

    public String getTotalSpams() {
        return totalSpams;
    }

    public void setTotalSpams(String totalSpams) {
        this.totalSpams = totalSpams;
    }

    public Date getLastUnsubscribeDate() {
        return lastUnsubscribeDate;
    }

    public void setLastUnsubscribeDate(Date lastUnsubscribeDate) {
        this.lastUnsubscribeDate = lastUnsubscribeDate;
    }

    public String getTotalUnsubscribes() {
        return totalUnsubscribes;
    }

    public void setTotalUnsubscribes(String totalUnsubscribes) {
        this.totalUnsubscribes = totalUnsubscribes;
    }

    public Date getLastConversionDate() {
        return lastConversionDate;
    }

    public void setLastConversionDate(Date lastConversionDate) {
        this.lastConversionDate = lastConversionDate;
    }

    public String getTotalConversions() {
        return totalConversions;
    }

    public void setTotalConversions(String totalConversions) {
        this.totalConversions = totalConversions;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Long getEmMembersMemberId() {
        return emMembersMemberId;
    }

    public void setEmMembersMemberId(Long emMembersMemberId) {
        this.emMembersMemberId = emMembersMemberId;
    }
}
