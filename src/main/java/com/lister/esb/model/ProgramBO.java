package com.lister.esb.model;

import javax.persistence.*;
import java.util.Date;
/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 11/16/12
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */

@Table(name="em_program_master")
@Entity
public class ProgramBO extends BaseBO{

    private static final long serialVersionUID = 3213324100708182234L;

    @Id
    @GeneratedValue
	@Column(name="PROGRAM_ID")
	private Long programId;

	@Column(name="PROGRAM_NAME")
	private String programName;

	@Column(name="PROGRAM_DESCRIPTION")
	private String programDescription;

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

    public Long getId(){
        return this.getProgramId();
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
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
}
