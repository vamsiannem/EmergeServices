package com.lister.esb.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Customer entity.
 * @author sudharsan_s
 *
 */
@Table(name="em_segment_master")
@Entity

/**
 * Created by IntelliJ IDEA.
 * User: bhavani_r
 * Date: 11/22/12
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class SegmentBO extends BaseBO {


	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 7418647701582106420L;

	 @Id
     @GeneratedValue
	 @Column(name="SEGMENT_ID")
     private Long segmentId;

     @Column(name="SEGMENT_NAME")
     private String segmentName;

     @Column(name="CONDITION_SERIALIZED")
     private String conditionSerialized;

     @Column(name="M_SEGMENT_TYPE")
     private String mSegementType;

     @Column(name="SEGMENT_QUERY")
     private String segmentQuery;

     @Column(name="CREATED_DATE")
     private String createdDate;

     @Column(name="CREATED_BY")
     private String createdBy;

     @Column(name="MODIFIED_DATE")
     private String modifiedDate;

     @Column(name="MODIFIED_BY")
     private String modifiedBy;

     @Column(name="TIMESTAMP")
     private Long timestamp;

     @Column(name="M_ACTIVE_FLAG")
     private String mActiveFlag;

     @Column(name="SEGMENT_DESCRIPTION")
     private String segmentDescription;

     @Column(name="ROUTINE_NAME")
     private String routineName;

    public Long getId(){
        return this.getSegmentId();
    }

    public Long getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Long segmentId) {
        this.segmentId = segmentId;
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

    public String getSegmentDescription() {
        return segmentDescription;
    }

    public void setSegmentDescription(String segmentDescription) {
        this.segmentDescription = segmentDescription;
    }

    public String getRoutineName() {
        return routineName;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }
}
