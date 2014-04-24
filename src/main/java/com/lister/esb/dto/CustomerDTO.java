package com.lister.esb.dto;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

import javax.persistence.Column;
import java.util.Date;

/**
 * Customer related details.
 *
 * @author sudharsan_s
 *
 */
public class CustomerDTO extends BaseDTO{

    @NotNull
	private Long memberId;

    private Long emId;

    private String firstName;

    private String lastName;

    private String dob;

    private String sex;

    private String city;

    private String state;

    private String pinCode;

    private String country;

    private String occupation;

    private String maritalStatus;

    private String email1;

    private String emailPermStatus;

    private String emailPermReason;

    private String emailPermDate;

    private String emailDelStatus;

    private String emailDelReason;

    private String emailDelDate;

    private String email2;

    private String mobileNumber;

    private String mobileOptinStatus;

    private String mobileDelStatus;

    private String mobileNumber2;

    private String memberType;

    private String activeStatus;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getEmId() {
        return emId;
    }

    public void setEmId(Long emId) {
        this.emId = emId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmailPermStatus() {
        return emailPermStatus;
    }

    public void setEmailPermStatus(String emailPermStatus) {
        this.emailPermStatus = emailPermStatus;
    }

    public String getEmailPermReason() {
        return emailPermReason;
    }

    public void setEmailPermReason(String emailPermReason) {
        this.emailPermReason = emailPermReason;
    }

    public String getEmailPermDate() {
        return emailPermDate;
    }

    public void setEmailPermDate(String emailPermDate) {
        this.emailPermDate = emailPermDate;
    }

    public String getEmailDelStatus() {
        return emailDelStatus;
    }

    public void setEmailDelStatus(String emailDelStatus) {
        this.emailDelStatus = emailDelStatus;
    }

    public String getEmailDelReason() {
        return emailDelReason;
    }

    public void setEmailDelReason(String emailDelReason) {
        this.emailDelReason = emailDelReason;
    }

    public String getEmailDelDate() {
        return emailDelDate;
    }

    public void setEmailDelDate(String emailDelDate) {
        this.emailDelDate = emailDelDate;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMobileOptinStatus() {
        return mobileOptinStatus;
    }

    public void setMobileOptinStatus(String mobileOptinStatus) {
        this.mobileOptinStatus = mobileOptinStatus;
    }

    public String getMobileDelStatus() {
        return mobileDelStatus;
    }

    public void setMobileDelStatus(String mobileDelStatus) {
        this.mobileDelStatus = mobileDelStatus;
    }

    public String getMobileNumber2() {
        return mobileNumber2;
    }

    public void setMobileNumber2(String mobileNumber2) {
        this.mobileNumber2 = mobileNumber2;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }
}
