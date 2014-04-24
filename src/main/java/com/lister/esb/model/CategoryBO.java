package com.lister.esb.model;

import javax.persistence.*;
import java.util.Date;
/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 12/27/12
 * Time: 9:33 AM
 * To change this template use File | Settings | File Templates.
 */

@Table(name="em_cat_history")
@Entity
public class CategoryBO extends BaseBO{

    private static final long serialVersionUID = 3213324100708182247L;

    @Id
	@Column(name="MEMBER_ID")
    private Long memberId;

    @Column(name="CATEGORY_ID")
    private Long categoryId;

    @Column(name="CATEGORY_NAME")
    private String categoryName;

    @Column(name="TOTAL_AMOUNT")
    private Long totalAmount;

    @Column(name="OVERALL_DISCOUNT")
    private Long overallDiscount;

    @Column(name="CURRENCY")
    private String currency;

    @Column(name="NO_OF_ORDERS")
    private int noOfOrders;

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

    @Column(name="RECENCY")
	private int recency;

    public Long getId(){
        return this.getMemberId();
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getOverallDiscount() {
        return overallDiscount;
    }

    public void setOverallDiscount(Long overallDiscount) {
        this.overallDiscount = overallDiscount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getNoOfOrders() {
        return noOfOrders;
    }

    public void setNoOfOrders(int noOfOrders) {
        this.noOfOrders = noOfOrders;
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
    public int getRecency() {
        return recency;
    }

    public void setRecency(int recency) {
        this.recency = recency;
    }
}
