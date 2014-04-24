package com.lister.esb.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Campaign entity.
 *
 * @author sudharsan_s
 *
 */
@Table(name="em_campaign_master")
@Entity
public class CampaignBO extends BaseBO {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 3213324100708182238L;

	@Id
    @GeneratedValue
	@Column(name="CAMPAIGN_ID")
	private Long campaignId;

	@Column(name="STORE_CODE")
	private String storeCode;

	@Column(name="CAMPAIGN_NAME")
	private String campaignName;

	@Column(name="M_CATEGORY")
	private String mCategory;

	@Column(name="M_LINK_TRACKING")
	private String mLinkTracking;

	@Column(name="SEED_LIST")
	private String seedList;

	@Column(name="M_CAMPAIGN_STATUS")
	private String mCampaignStatus;

	@Column(name="M_LAUNCH_STYLE")
	private String mLaunchStyle;

    @Column(name="M_LAUNCH_FREQ")
	private String mLaunchFreq;

    @Column(name="LAUNCH_TIME")
	private String launchTime;

    @Column(name="OCCURENCE_DATE")
    private String occurenceDate;

    @Column(name="STOP_MODE")
    private String stopMode;

	@Column(name="CAMPAIGN_ST_DT")
	private Date campaignStDate;

	@Column(name="CAMPAIGN_END_DT")
	private Date campaignEndDate;

	@Column(name="CREATED_DATE")
	private String createdDate;

	@Column(name="MODIFIED_DATE")
	private String modifiedDate;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="TIMESTAMP")
	private Long timeStamp;

    @Column(name="PROGRAM_ID")
	private Long programId;

    @Column(name="TEMPLATE_ID")
	private Long templateId;

    public Long getId(){
        return this.getCampaignId();
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

    /**
	 * @return the campaignId
	 */
	public Long getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaignIdVal the campaignId to set
	 */
	public void setCampaignId(Long campaignIdVal) {
		this.campaignId = campaignIdVal;
	}

    public String getmLinkTracking() {
        return mLinkTracking;
    }

    public void setmLinkTracking(String mLinkTracking) {
        this.mLinkTracking = mLinkTracking;
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

    public String getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(String launchTime) {
        this.launchTime = launchTime;
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

    /**
	 * @return the storeCode
	 */
	public String getStoreCode() {
		return storeCode;
	}

	/**
	 * @param storeCodeVal the storeCode to set
	 */
	public void setStoreCode(String storeCodeVal) {
		this.storeCode = storeCodeVal;
	}

	/**
	 * @return the campaignName
	 */
	public String getCampaignName() {
		return campaignName;
	}

	/**
	 * @param campaignNameVal the campaignName to set
	 */
	public void setCampaignName(String campaignNameVal) {
		this.campaignName = campaignNameVal;
	}

	/**
	 * @return the mCategory
	 */
	public String getMCategory() {
		return mCategory;
	}

	/**
	 * @param mCategory the mCategory to set
	 */
	public void setMCategory(String mCategory) {
		this.mCategory = mCategory;
	}

	/**
	 * @return the mLinkTracing
	 */



	/**
	 * @return the seedList
	 */
	public String getSeedList() {
		return seedList;
	}

	/**
	 * @param seedList the seedList to set
	 */
	public void setSeedList(String seedList) {
		this.seedList = seedList;
	}

	/**
	 * @return the mCampaignStatus
	 */
	public String getMCampaignStatus() {
		return mCampaignStatus;
	}

	/**
	 * @param mCampaignStatus the mCampaignStatus to set
	 */
	public void setMCampaignStatus(String mCampaignStatus) {
		this.mCampaignStatus = mCampaignStatus;
	}

	/**
	 * @return the mLaunchStyle
	 */
	public String getMLaunchStyle() {
		return mLaunchStyle;
	}

	/**
	 * @param mLaunchStyle the mLaunchStyle to set
	 */
	public void setMLaunchStyle(String mLaunchStyle) {
		this.mLaunchStyle = mLaunchStyle;
	}

	/**
	 * @return the campaignStDate
	 */
	public Date getCampaignStDate() {
		return campaignStDate;
	}

	/**
	 * @param campaignStDate the campaignStDate to set
	 */
	public void setCampaignStDate(Date campaignStDate) {
		this.campaignStDate = campaignStDate;
	}

	/**
	 * @return the campaignEndDate
	 */
	public Date getCampaignEndDate() {
		return campaignEndDate;
	}

	/**
	 * @param campaignEndDate the campaignEndDate to set
	 */
	public void setCampaignEndDate(Date campaignEndDate) {
		this.campaignEndDate = campaignEndDate;
	}

    public String getmLaunchFreq() {
        return mLaunchFreq;
    }

    public void setmLaunchFreq(String mLaunchFreq) {
        this.mLaunchFreq = mLaunchFreq;
    }

    /**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the modifiedDate
	 */
	public String getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the timeStamp
	 */
	public Long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
}
