package com.lister.esb.dto;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

import java.util.Date;

/**
 * Purchase related details.
 *
 * @author uthayakumar_p
 *
 */
public class PurchaseDTO extends BaseDTO{

    @NotNull
	private Long memberId;

    private String memberName;

	private Long maxOrder;

	private Long minOrder;

    private Long maxDiscount;

    private Long totalOrders;

    private Long lastPurchase;

	private String lastPurchaseDate;

    private String sku;

    private String skuName;

    private String categoryId;

    private String categoryName;

    private Long amount;

    private Long totalAmount;

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    private Long discount;

    private String currency;

    private int qty;


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
	public String getLastPurchaseDate() {
		return lastPurchaseDate;
	}

	/**
	 * @param lastPurchaseDateVal the lastPurchaseDate to set
	 */
	public void setLastPurchaseDate(String lastPurchaseDateVal) {
		this.lastPurchaseDate = lastPurchaseDateVal;
	}

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
