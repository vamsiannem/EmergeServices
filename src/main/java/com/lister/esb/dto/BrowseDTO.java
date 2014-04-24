package com.lister.esb.dto;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 11/22/12
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class BrowseDTO extends BaseDTO{

    private String sessionKey;

    private String viewType;

    private String viewName;

    private String viewDate;

    private String memberId;

    private String createdDate;

	private int displayCookieId;

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
