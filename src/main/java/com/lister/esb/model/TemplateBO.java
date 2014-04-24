package com.lister.esb.model;

import javax.persistence.*;
import java.util.Date;
/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 11/23/12
 * Time: 7:24 PM
 * To change this template use File | Settings | File Templates.
 */

@Table(name="em_template_master")
@Entity
public class TemplateBO extends BaseBO{

    private static final long serialVersionUID = 3213324100708182248L;

    @Id
    @GeneratedValue
	@Column(name="TEMPLATE_ID")
    private Long templateId;

    @Column(name="TEMPLATE_NAME")
    private String templateName;

    @Column(name="MAIL_SUBJECT")
    private String mailSubject;

    @Column(name="TEMPLATE_CONTENT_HTML")
    private String templateContentHtml;

    @Column(name="FROM_ADDRESS")
    private String fromAddress;

    @Column(name="FROM_ADDRESSE")
    private String fromAddresse;

    @Column(name="REPLY_TO_ADDRESS")
    private String replyToAddress;

    @Column(name="REPLY_TO_ADDRESSE")
    private String replyToAddresse;

    @Column(name="AREA_X")
    private Long areaX;

    @Column(name="AREA_Y")
    private Long areaY;

    @Column(name="M_ACTIVE_FLAG")
    private String mActiveFlag;

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
        return this.getTemplateId();
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getTemplateContentHtml() {
        return templateContentHtml;
    }

    public void setTemplateContentHtml(String templateContentHtml) {
        this.templateContentHtml = templateContentHtml;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromAddresse() {
        return fromAddresse;
    }

    public void setFromAddresse(String fromAddresse) {
        this.fromAddresse = fromAddresse;
    }

    public String getReplyToAddress() {
        return replyToAddress;
    }

    public void setReplyToAddress(String replyToAddress) {
        this.replyToAddress = replyToAddress;
    }

    public String getReplyToAddresse() {
        return replyToAddresse;
    }

    public void setReplyToAddresse(String replyToAddresse) {
        this.replyToAddresse = replyToAddresse;
    }

    public Long getAreaX() {
        return areaX;
    }

    public void setAreaX(Long areaX) {
        this.areaX = areaX;
    }

    public Long getAreaY() {
        return areaY;
    }

    public void setAreaY(Long areaY) {
        this.areaY = areaY;
    }

    public String getmActiveFlag() {
        return mActiveFlag;
    }

    public void setmActiveFlag(String mActiveFlag) {
        this.mActiveFlag = mActiveFlag;
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
