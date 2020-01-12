package com.mingtai.mt.entity;

/**
 * Created by LG on 2020/1/9.
 */
public class BalanceDetailBean {
    private int ExpenditureId;
    private int UserId;
    private String UserName;
    private double ExpenditureMoney;//此次交易额
    private int ReceiptsOrOut;//支出为-1，收入为1
    private double Balance;//剩余额度
    private String TradeType;
    private String StatusName;
    private int Status;
    private String TradeSay;
    private String CretateTime;
    private String AdminName;
    private String OrderId;

    public int getExpenditureId() {
        return ExpenditureId;
    }

    public void setExpenditureId(int expenditureId) {
        ExpenditureId = expenditureId;
    }

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

    public double getExpenditureMoney() {
        return ExpenditureMoney;
    }

    public void setExpenditureMoney(double expenditureMoney) {
        ExpenditureMoney = expenditureMoney;
    }

    public int getReceiptsOrOut() {
        return ReceiptsOrOut;
    }

    public void setReceiptsOrOut(int receiptsOrOut) {
        ReceiptsOrOut = receiptsOrOut;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public String getTradeType() {
        return TradeType;
    }

    public void setTradeType(String tradeType) {
        TradeType = tradeType;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getTradeSay() {
        return TradeSay;
    }

    public void setTradeSay(String tradeSay) {
        TradeSay = tradeSay;
    }

    public String getCretateTime() {
        return CretateTime;
    }

    public void setCretateTime(String cretateTime) {
        CretateTime = cretateTime;
    }

    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String adminName) {
        AdminName = adminName;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }
}