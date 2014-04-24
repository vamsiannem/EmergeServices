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

@Table(name="EM_VIEW_EVENTS")
@Entity
public class BrowseBO extends BaseBO {

    private static final long serialVersionUID = 3213324100708182240L;

    @Id
    @Column(name="SESSION_KEY")
	private String sessionKey;

	@Column(name="VIEW_TYPE")
	private String viewType;

	@Column(name="VIEW_NAME")
	private String viewName;

	@Column(name="VIEW_DATE")
	private String viewDate;

	@Column(name="MEMBERID")
	private String memberId;

    @Column(name="CREATED_DATE")
	private String createdDate;

	@Column(name="DISPLAY_COOKIEID")
	private int displayCookieId;

    @java.lang.Override
    public Long getId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getViewDate() {
        return viewDate;
    }

    public void setViewDate(String viewDate) {
        this.viewDate = viewDate;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
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
