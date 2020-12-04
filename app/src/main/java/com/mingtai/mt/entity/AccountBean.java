package com.mingtai.mt.entity;

import java.io.Serializable;

/**
 * Created by lilinkun
 * on 2019/12/28
 */
public class AccountBean implements Serializable {
    private int UserId;
    private String UserName;
    private String Mobile;
    private String UserSex;
    private String SurName;
    private String Resettlement;
    private String Referees;
    private int UserLevel;
    private String UserLevelName;
    private String Portrait;
    private String NickName;
    private String LastLoginTime;
    private int IsSingleCenter;
    private String SingleCenterName;
    private String VipValidity;
    private String ActivationTime;
    private boolean IsFrozeBank;
    private boolean IsFrozeTrade;
    private int IsWd;
    private int UserStatus;
    private int PDTypeId;
    private int FatherUserId;
    private int RegType;
    private int IsRealName;
    private int Honour;
    private int PersonalOrCorporate;
    private int UserLogins;
    private String StoreName;
    private String StoreNo;
    private boolean Agreement = false;
    private int UserStatusTwo;
    private String UserStatusTwoName;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getUserSex() {
        return UserSex;
    }

    public void setUserSex(String userSex) {
        UserSex = userSex;
    }

    public String getSurName() {
        return SurName;
    }

    public void setSurName(String surName) {
        SurName = surName;
    }

    public String getResettlement() {
        return Resettlement;
    }

    public void setResettlement(String resettlement) {
        Resettlement = resettlement;
    }

    public String getReferees() {
        return Referees;
    }

    public void setReferees(String referees) {
        Referees = referees;
    }

    public int getUserLevel() {
        return UserLevel;
    }

    public void setUserLevel(int userLevel) {
        UserLevel = userLevel;
    }

    public String getUserLevelName() {
        return UserLevelName;
    }

    public void setUserLevelName(String userLevelName) {
        UserLevelName = userLevelName;
    }

    public String getPortrait() {
        return Portrait;
    }

    public void setPortrait(String portrait) {
        Portrait = portrait;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getLastLoginTime() {
        return LastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        LastLoginTime = lastLoginTime;
    }

    public int getIsSingleCenter() {
        return IsSingleCenter;
    }

    public void setIsSingleCenter(int isSingleCenter) {
        IsSingleCenter = isSingleCenter;
    }

    public String getSingleCenterName() {
        return SingleCenterName;
    }

    public void setSingleCenterName(String singleCenterName) {
        SingleCenterName = singleCenterName;
    }

    public String getVipValidity() {
        return VipValidity;
    }

    public void setVipValidity(String vipValidity) {
        VipValidity = vipValidity;
    }

    public String getActivationTime() {
        return ActivationTime;
    }

    public void setActivationTime(String activationTime) {
        ActivationTime = activationTime;
    }

    public boolean isFrozeBank() {
        return IsFrozeBank;
    }

    public void setFrozeBank(boolean frozeBank) {
        IsFrozeBank = frozeBank;
    }

    public boolean isFrozeTrade() {
        return IsFrozeTrade;
    }

    public void setFrozeTrade(boolean frozeTrade) {
        IsFrozeTrade = frozeTrade;
    }

    public int getIsWd() {
        return IsWd;
    }

    public void setIsWd(int isWd) {
        IsWd = isWd;
    }

    public int getUserStatus() {
        return UserStatus;
    }

    public void setUserStatus(int userStatus) {
        UserStatus = userStatus;
    }

    public int getPDTypeId() {
        return PDTypeId;
    }

    public void setPDTypeId(int PDTypeId) {
        this.PDTypeId = PDTypeId;
    }

    public int getFatherUserId() {
        return FatherUserId;
    }

    public void setFatherUserId(int fatherUserId) {
        FatherUserId = fatherUserId;
    }

    public int getRegType() {
        return RegType;
    }

    public void setRegType(int regType) {
        RegType = regType;
    }

    public int getIsRealName() {
        return IsRealName;
    }

    public void setIsRealName(int isRealName) {
        IsRealName = isRealName;
    }

    public int getHonour() {
        return Honour;
    }

    public void setHonour(int honour) {
        Honour = honour;
    }

    public int getPersonalOrCorporate() {
        return PersonalOrCorporate;
    }

    public void setPersonalOrCorporate(int personalOrCorporate) {
        PersonalOrCorporate = personalOrCorporate;
    }

    public int getUserLogins() {
        return UserLogins;
    }

    public void setUserLogins(int userLogins) {
        UserLogins = userLogins;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getStoreNo() {
        return StoreNo;
    }

    public void setStoreNo(String storeNo) {
        StoreNo = storeNo;
    }

    public boolean isAgreement() {
        return Agreement;
    }

    public void setAgreement(boolean agreement) {
        Agreement = agreement;
    }

    public int getUserStatusTwo() {
        return UserStatusTwo;
    }

    public void setUserStatusTwo(int userStatusTwo) {
        UserStatusTwo = userStatusTwo;
    }

    public String getUserStatusTwoName() {
        return UserStatusTwoName;
    }

    public void setUserStatusTwoName(String userStatusTwoName) {
        UserStatusTwoName = userStatusTwoName;
    }

    @Override
    public String toString() {
        return "AccountBean{" +
                "UserId=" + UserId +
                ", UserName='" + UserName + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", UserSex='" + UserSex + '\'' +
                ", SurName='" + SurName + '\'' +
                ", Resettlement='" + Resettlement + '\'' +
                ", Referees='" + Referees + '\'' +
                ", UserLevel=" + UserLevel +
                ", UserLevelName='" + UserLevelName + '\'' +
                ", Portrait='" + Portrait + '\'' +
                ", NickName='" + NickName + '\'' +
                ", LastLoginTime='" + LastLoginTime + '\'' +
                ", IsSingleCenter=" + IsSingleCenter +
                ", SingleCenterName='" + SingleCenterName + '\'' +
                ", VipValidity='" + VipValidity + '\'' +
                ", ActivationTime='" + ActivationTime + '\'' +
                ", IsFrozeBank=" + IsFrozeBank +
                ", IsFrozeTrade=" + IsFrozeTrade +
                ", IsWd=" + IsWd +
                ", UserStatus=" + UserStatus +
                ", PDTypeId=" + PDTypeId +
                ", FatherUserId=" + FatherUserId +
                ", RegType=" + RegType +
                ", IsRealName=" + IsRealName +
                ", Honour=" + Honour +
                ", PersonalOrCorporate=" + PersonalOrCorporate +
                ", UserLogins=" + UserLogins +
                ", StoreName='" + StoreName + '\'' +
                ", StoreNo='" + StoreNo + '\'' +
                ", Agreement=" + Agreement +
                ", UserStatusTwo=" + UserStatusTwo +
                ", UserStatusTwoName='" + UserStatusTwoName + '\'' +
                '}';
    }
}