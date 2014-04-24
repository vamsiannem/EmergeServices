package com.lister.esb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Purchase entity.
 * @author uthayakumar_p
 *
 */
@Table(name="HCEQ_MEMBERS")
@Entity
public class PurchaseBO extends BaseBO {
	
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 7418647701582106420L;

	@Id
	@Column(name="MEMBER_ID")
	private Long memberId;
	
	@Column(name="MAX_ORDER_VALUE")
	private Long maxOrder;
	
	@Column(name="MIN_ORDER_VALUE")
	private Long minOrder;

    @Column(name="MAX_DISCOUNT_VALUE")
	private Long maxDiscount;

    @Column(name="TOTAL_NO_ORDERS")
	private Long totalOrders;

    @Column(name="LAST_PUR_VALUE")
	private Long lastPurchase;

	@Column(name="LAST_PUR_DATE")
	private String lastPurchaseDate;

    @Column(name="LAST_DROPOUT_DATE")
	private String lastDropoutDate;

    @Column(name="RECENCY_SCORE")
	private Long recencyScore;

    @Column(name="MONETARY_SCORE")
	private Long monetaryScore;

    @Column(name="FREQUENCY_SCORE")
	private Long frequencyScore;

    @Column(name="RFM_SCORE")
	private Long rfmScore;


    public Long getId(){
        return this.getMemberId();
    }

     /**
	 * @return the memberId
	 */
	public Long getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the maxOrder
	 */
	public Long getMaxOrder() {
		return maxOrder;
	}

	/**
	 * @param maxOrderVal the maxOrder to set
	 */
	public void setMaxOrder(Long maxOrderVal) {
		this.maxOrder = maxOrderVal;
	}

	/**
	 * @return the minOrder
	 */
	public Long getMinOrder() {
		return minOrder;
	}

	/**
	 * @param minOrderVal the minOrder to set
	 */
	public void setMinOrder(Long minOrderVal) {
		this.minOrder = minOrderVal;
	}


    /**
	* @return the maxDiscount
	*/
	public Long getMaxDiscount() {
		return maxDiscount;
	}

	/**
	 * @param maxDiscountVal the maxDiscount to set
	 */
	public void setMaxDiscount(Long maxDiscountVal) {
		this.maxDiscount = maxDiscountVal;
	}

    /**
	* @return the totalOrders
	*/
	public Long getTotalOrders() {
		return totalOrders;
	}

	/**
	 * @param totalOrdersVal the totalOrders to set
	 */
	public void setTotalOrders(Long totalOrdersVal) {
		this.totalOrders = totalOrdersVal;
	}

    /**
	* @return the lastPurchase
	*/
	public Long getLastPurchase() {
		return lastPurchase;
	}

	/**
	 * @param getLastPurchaseVal the lastPurchase to set
	 */
	public void setLastPurchase(Long getLastPurchaseVal) {
		this.lastPurchase = getLastPurchaseVal;
	}

	/**
	 * @return the lastPurchaseDate
	 */
	public String getlastPurchaseDate() {
		return lastPurchaseDate;
	}

	/**
	 * @param lastPurchaseDateVal the lastPurchaseDate to set
	 */
	public void setlastPurchaseDate(String lastPurchaseDateVal) {
		this.lastPurchaseDate = lastPurchaseDateVal;
	}

  	/**
	 * @return the lastDropoutDate
	 */
	public String getLastDropoutDate() {
		return lastDropoutDate;
	}

	/**
	 * @param lastDropoutDateVal the lastDropoutDate to set
	 */
	public void setLastDropoutDate(String lastDropoutDateVal) {
		this.lastDropoutDate = lastDropoutDateVal;
	}

     /**
	* @return the recencyScore
	*/
	public Long getRecencyScore() {
		return recencyScore;
	}

	/**
	 * @param recencyScoreVal the recencyScore to set
	 */
	public void setRecencyScore(Long recencyScoreVal) {
		this.recencyScore = recencyScoreVal;
	}

    /**
	* @return the monetaryScore
	*/
	public Long getMonetaryScore() {
		return monetaryScore;
	}

	/**
	 * @param monetaryScoreVal the monetaryScore to set
	 */
	public void setMonetaryScore(Long monetaryScoreVal) {
		this.monetaryScore = monetaryScoreVal;
	}

    /**
	* @return the frequencyScore
	*/
	public Long getFrequencyScore() {
		return frequencyScore;
	}

	/**
	 * @param frequencyScoreVal the frequencyScore to set
	 */
	public void setFrequencyScore(Long frequencyScoreVal) {
		this.frequencyScore = frequencyScoreVal;
	}

    /**
	* @return the rfmScore
	*/
	public Long getRfmScore() {
		return rfmScore;
	}

	/**
	 * @param rfmScoreVal the rfmScore to set
	 */
	public void setRfmScore(Long rfmScoreVal) {
		this.rfmScore = rfmScoreVal;
	}
}
