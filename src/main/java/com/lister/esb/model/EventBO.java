package com.lister.esb.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Customer entity.
 * @author sudharsan_s
 *
 */
@Table(name="em_mem_cmp_engag_inter")
@Entity
public class EventBO extends BaseBO {
	
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 7418647701582106420L;

    @Id
	@Column(name="MEMBER_ID")
	private Long memberId;

    @Column(name="Primary_Email")
    private String primaryEmail;

    @Column(name="EM_CAMPAIGN_ID")
	private Long emCampaignId;

    @Column(name="LAUNCH_ID")
    private String launchId;

    @Column(name="EXT_CAMP_ID")
    private Long extCampId;

    @Column(name="LAST_SENT_DATE")
    private Date lastSentDate;

    @Column(name="TOTAL_SENT")
    private String totalSent;

    @Column(name="LAST_OPEN_DATE")
    private Date lastOpenDate;

    @Column(name="TOTAL_OPENS")
    private String totalOpens;

    @Column(name="LAST_CLICK_DATE")
    private Date lastClickDate;

    @Column(name="TOTAL_CLICKS")
    private String totalClicks;

    @Column(name="LAST_BOUNCE_DATE")
    private Date lastBounceDate;

    @Column(name="TOTAL_BOUNCES")
    private String totalBounces;

    @Column(name="LAST_SKIP_DATE")
    private Date lastSkipDate;

    @Column(name="TOTAL_SKIPS")
    private String totalSkips;

    @Column(name="LAST_SPAM_DATE")
    private Date lastSpamDate;

    @Column(name="TOTAL_SPAMS")
    private String totalSpams;

    @Column(name="LAST_UNSUBSCRIBE_DATE")
    private Date lastUnsubscribeDate;

    @Column(name="TOTAL_UNSUBSCRIBES")
    private String totalUnsubscribes;

    @Column(name="LAST_CONVERSION_DATE")
    private Date lastConversionDate;

    @Column(name="TOTAL_CONVERSIONS")
    private String totalConversions;

    @Column(name="CREATED_DATE")
    private Date createdDate;

    @Column(name="CREATED_BY")
    private String createdBy;

    @Column(name="MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name="MODIFIED_BY")
    private String modifiedBy;

    @Column(name="EM_MEMBERS_MEMBER_ID")
    private Long emMembersMemberId;

    public Long getId(){
        return this.getMemberId();
    }

    public Long getMemberId() {
        return memberId;
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

    public String getLaunchId() {
        return launchId;
    }

    public void setLaunchId(String launchId) {
        this.launchId = launchId;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }
}