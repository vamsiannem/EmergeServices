package com.lister.esb.model;

import javax.persistence.*;
import java.util.Date;
/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 12/19/12
 * Time: 5:34 PM
 * To change this template use File | Settings | File Templates.
 */

@Table(name="em_template_pers_dtl")
@Entity
public class TemplatedetailBO extends BaseBO{

    @Id
    @GeneratedValue
	@Column(name="SEQUENCE_NO")
    private Long sequenceNo;

    @Column(name="TEMPLATE_ID")
    private Long templateId;

    @Column(name="PERS_FIELD_NAME")
    private String persFieldName;

    @Column(name="ATTRIBUTE_ID")
    private Long attributeId;

    public Long getId(){
        return this.getSequenceNo();
    }

    public Long getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Long sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
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
