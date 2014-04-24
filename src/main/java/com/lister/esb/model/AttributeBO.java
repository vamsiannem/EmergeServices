package com.lister.esb.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 11/22/12
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */

    @Table(name="em_segment_attrib_master")
    @Entity
public class AttributeBO extends BaseBO {

    private static final long serialVersionUID = 3213324100708182240L;

    @Id
	@Column(name="ATTRIBUTE_ID")
    @GeneratedValue
	private Long attributeId;

	@Column(name="M_ATTRIBUTE_TYPE")
	private String attributeType;

	@Column(name="ATTRIBUTE_NAME")
	private String attributeName;

	@Column(name="TABLE_NAME")
	private String tableName;

	@Column(name="COLUMN_NAME")
	private String columnName;

	@Column(name="M_ACTIVE_FLAG")
	private String activeFlag;

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
        return this.getAttributeId();
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
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
