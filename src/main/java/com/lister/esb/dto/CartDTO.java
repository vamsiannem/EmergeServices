package com.lister.esb.dto;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 11/22/12
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class CartDTO extends BaseDTO{

    private String sessionKey;

    private String cartId;

    private int eventId;

    private String eventValue;

    private int memberId;

    private String createdDate;

	private int displayCookieId;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventValue() {
        return eventValue;
    }

    public void setEventValue(String eventValue) {
        this.eventValue = eventValue;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getDisplayCookieId() {
        return displayCookieId;
    }

    public void setDisplayCookieId(int displayCookieId) {
        this.displayCookieId = displayCookieId;
    }
}
