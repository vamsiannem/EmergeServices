package com.lister.esb.dto;

import java.util.Date;
/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 12/19/12
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class TemplatedetailDTO extends BaseDTO{

    private Long templateId;

    private Long sequenceNo;

    private String persFieldName;

    private Long attributeId;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Long sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getPersFieldName() {
        return persFieldName;
    }

    public void setPersFieldName(String persFieldName) {
        this.persFieldName = persFieldName;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }
}
