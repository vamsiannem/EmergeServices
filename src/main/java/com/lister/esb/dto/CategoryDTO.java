package com.lister.esb.dto;

import java.util.Date;

import javax.persistence.GeneratedValue;
/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 12/27/12
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class CategoryDTO extends BaseDTO {

    private Long memberId;

    private Long categoryId;

    private String categoryName;

    private Long totalAmount;

    private Long overallDiscount;

    private String currency;

    private int noOfOrders;

    private String createdDate;

	private String modifiedDate;

	private String createdBy;

	private String modifiedBy;

    private Long timeStamp;

    private int recency;

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
