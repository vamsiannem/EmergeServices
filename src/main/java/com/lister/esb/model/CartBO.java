package com.lister.esb.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 11/22/12
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */

@Table(name="em_events")
@Entity
public class CartBO extends BaseBO {

    private static final long serialVersionUID = 3213324100708182240L;

    @Id
    @Column(name="SESSION_KEY")
	private String sessionKey;

	@Column(name="CARTID")
	private String cartId;

	@Column(name="EVENTID")
	private int eventId;

	@Column(name="EVENT_VALUE")
	private String eventValue;

	@Column(name="member_id")
	private int memberId;

    @java.lang.Override
    public Long getId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Column(name="CREATED_DATE")
	private String createdDate;

	@Column(name="DISPLAY_COOKIEID")
	private int displayCookieId;

    /*public Long getId(){
        return (Long)1.0;
    } */

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

    public void setDisplayCookieId(int displayCookieIdd) {
        this.displayCookieId = displayCookieId;
    }
}
