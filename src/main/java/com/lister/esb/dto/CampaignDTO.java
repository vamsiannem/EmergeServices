package com.lister.esb.dto;

import javax.persistence.GeneratedValue;
import java.util.Date;

/**
 * Campaign related details.
 * 
 * @author sudharsan_s
 *
 */
public class CampaignDTO extends BaseDTO {

    @GeneratedValue
	private Long campaignId;

	private String storeCode;

	private String campaignName;

	private String mLinkTracking;

	private String seedList;

    private String mCategory;

	private String mCampaignStatus;

	private String mLaunchStyle;

	private Date campaignStDate;

	private Date campaignEndDate;

    private String launchTime;

	private String mLaunchFreq;

    private String createdDate;

	private String modifiedDate;

	private String createdBy;

	private String modifiedBy;

	private Long timeStamp;

    private String occurenceDate;

    private String stopMode;

    private Long programId;

    private Long templateId;

    public String getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(String launchTime) {
        this.launchTime = launchTime;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getmLinkTracking() {
        return mLinkTracking;
    }

    public void setmLinkTracking(String mLinkTracking) {
        this.mLinkTracking = mLinkTracking;
    }

    public String getSeedList() {
        return seedList;
    }

    public void setSeedList(String seedList) {
        this.seedList = seedList;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmCampaignStatus() {
        return mCampaignStatus;
    }

    public void setmCampaignStatus(String mCampaignStatus) {
        this.mCampaignStatus = mCampaignStatus;
    }

    public String getmLaunchStyle() {
        return mLaunchStyle;
    }

    public void setmLaunchStyle(String mLaunchStyle) {
        this.mLaunchStyle = mLaunchStyle;
    }

    public Date getCampaignStDate() {
        return campaignStDate;
    }

    public void setCampaignStDate(Date campaignStDate) {
        this.campaignStDate = campaignStDate;
    }

    public Date getCampaignEndDate() {
        return campaignEndDate;
    }

    public void setCampaignEndDate(Date campaignEndDate) {
        this.campaignEndDate = campaignEndDate;
    }

    public String getmLaunchFreq() {
        return mLaunchFreq;
    }

    public void setmLaunchFreq(String mLaunchFreq) {
        this.mLaunchFreq = mLaunchFreq;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getOccurenceDate() {
        return occurenceDate;
    }

    public void setOccurenceDate(String occurenceDate) {
        this.occurenceDate = occurenceDate;
    }

    public String getStopMode() {
        return stopMode;
    }

    public void setStopMode(String stopMode) {
        this.stopMode = stopMode;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
}
