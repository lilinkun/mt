package com.mingtai.mt.entity;

import java.io.Serializable;

/**
 * Created by LG on 2020/1/10.
 */
public class UserBankBean implements Serializable {
    private String UserName;
    private String BankDetails;
    private int BankName;
    private String BankNameDesc;
    private String BankNo;
    private String BankUserName;
    private String UserCode;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getBankDetails() {
        return BankDetails;
    }

    public void setBankDetails(String bankDetails) {
        BankDetails = bankDetails;
    }

    public int getBankName() {
        return BankName;
    }

    public void setBankName(int bankName) {
        BankName = bankName;
    }

    public String getBankNameDesc() {
        return BankNameDesc;
    }

    public void setBankNameDesc(String bankNameDesc) {
        BankNameDesc = bankNameDesc;
    }

    public String getBankNo() {
        return BankNo;
    }

    public void setBankNo(String bankNo) {
        BankNo = bankNo;
    }

    public String getBankUserName() {
        return BankUserName;
    }

    public void setBankUserName(String bankUserName) {
        BankUserName = bankUserName;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }
}
