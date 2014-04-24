package com.lister.esb.dto;

import java.util.Date;
/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 11/26/12
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class CampaignsegmentDTO extends BaseDTO{

    private Long campaignId;

    private Long sequenceNo;

    private Long segmentId;

    private String segmentNature;

    private Date createdDate;

	private Date modifiedDate;

	private String createdBy;

	private String modifiedBy;

	private Long timeStamp;

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
