package com.lister.esb.dto;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: bhavani_r
 * Date: 11/22/12
 * Time: 3:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class SegmentDTO  extends BaseDTO {

     private Long segmentId;

     private String segmentName;

     private String conditionSerialized;

     private String mSegementType;

     private String segmentQuery;

     private String createdDate;

     private String createdBy;

     private String modifiedDate;

     private String modifiedBy;

     private Long timestamp;

     private String mActiveFlag;

     private String segmentDescription;

     private String routineName;

    public Long getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Long segmentId) {
        this.segmentId = segmentId;
    }

    public String getSegmentDescription() {
        return segmentDescription;
    }

    public void setSegmentDescription(String segmentDescription) {
        this.segmentDescription = segmentDescription;
    }

    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    public String getConditionSerialized() {
        return conditionSerialized;
    }

    public void setConditionSerialized(String conditionSerialized) {
        this.conditionSerialized = conditionSerialized;
    }

    public String getmSegementType() {
        return mSegementType;
    }

    public void setmSegementType(String mSegementType) {
        this.mSegementType = mSegementType;
    }

    public String getSegmentQuery() {
        return segmentQuery;
    }

    public void setSegmentQuery(String segmentQuery) {
        this.segmentQuery = segmentQuery;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getmActiveFlag() {
        return mActiveFlag;
    }

    public void setmActiveFlag(String mActiveFlag) {
        this.mActiveFlag = mActiveFlag;
    }

    public String getRoutineName() {
        return this.routineName;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }
}
