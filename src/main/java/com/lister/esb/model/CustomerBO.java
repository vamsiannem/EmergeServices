package com.lister.esb.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Customer entity.
 * @author sudharsan_s
 *
 */
@Table(name="em_members")
@Entity
public class CustomerBO extends BaseBO {

    @Id
    @Column(name="MEMBER_ID")
    private Long memberId;

    @Column(name="EMID")
    private Long emId;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="DOB")
    private String dob;

    @Column(name="SEX")
    private String sex;

    @Column(name="CITY")
    private String city;

    @Column(name="STATE")
    private String state;

    @Column(name="PINCODE")
    private String pinCode;

    @Column(name="COUNTRY")
    private String country;

    @Column(name="OCCUPATION")
    private String occupation;

    @Column(name="MARITAL_STATUS")
    private String maritalStatus;

    @Column(name="EMAIL_PRIMARY")
    private String email1;

    @Column(name="Email_Perm_Status")
    private String emailPermStatus;

    @Column(name="Email_Perm_Reason")
    private String emailPermReason;

    @Column(name="Email_Perm_Date")
    private String emailPermDate;

    @Column(name="Email_Del_Status")
    private String emailDelStatus;

    @Column(name="Email_Del_Reason")
    private String emailDelReason;

    @Column(name="Email_Del_Date")
    private String emailDelDate;

    @Column(name="Email_Secondary")
    private String email2;

    @Column(name="MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name="Mobile_Optin_Status")
    private String mobileOptinStatus;

    @Column(name="Mobile_Del_Status")
    private String mobileDelStatus;

    @Column(name="Mobile_Secondary")
    private String mobileNumber2;

    @Column(name="Member_Type")
    private String memberType;

    @Column(name="Active_Status")
    private String activeStatus;

    @Column(name="CREATED_DATE")
    private String createdDate;

    @Column(name="MODIFIED_DATE")
    private String modifiedDate;

    @Column(name="CREATED_BY")
    private String createdBy;

    @Column(name="MODIFIED_BY")
    private String modifiedBy;

    public Long getId(){
        return this.getMemberId();
    }

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

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 7418647701582106420L;

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
}

