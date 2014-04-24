package com.lister.esb.model;

import javax.persistence.*;
import java.util.Date;
/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 11/26/12
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */

@Table(name="em_campaign_segment")
@Entity
public class CampaignsegmentBO extends BaseBO{

    /**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 3213324100708182224L;


	@Column(name="CAMPAIGN_ID")
    private Long campaignId;

    @Id
    @GeneratedValue
    @Column(name="SEQUENCE_NO")
    private Long sequenceNo;

    @Column(name="SEGMENT_ID")
    private Long segmentId;

    @Column(name="M_SEGMENT_NATURE")
    private String segmentNature;

    @Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

    @Column(name="TIMESTAMP")
    private Long timeStamp;

    public Long getId(){
        return this.getCampaignId();
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Long getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Long sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Long getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Long segmentId) {
        this.segmentId = segmentId;
    }

    public String getSegmentNature() {
        return segmentNature;
    }

    public void setSegmentNature(String segmentNature) {
        this.segmentNature = segmentNature;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
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
}
