package com.lister.esb.dto;

import java.util.Date;
/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 11/23/12
 * Time: 7:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class TemplateDTO extends BaseDTO{

    private Long templateId;

    private String templateName;

    private String mailSubject;

    private String templateContentHtml;

    private String fromAddress;

    private String fromAddresse;

    private String replyToAddress;

    private String replyToAddresse;

    private Long areaX;

    private Long areaY;

    private String mActiveFlag;

    private Date createdDate;

	private Date modifiedDate;

	private String createdBy;

	private String modifiedBy;

	private Long timeStamp;


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
