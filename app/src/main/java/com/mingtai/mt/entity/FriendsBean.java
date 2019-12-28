package com.mingtai.mt.entity;

/**
 * Created by lilinkun
 * on 2019/12/28
 */
public class FriendsBean {
    private String NickName;
    private int UserLevel;
    private int IsWd;
    private String UserLevelName;
    private int PDTypeId;

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public int getUserLevel() {
        return UserLevel;
    }

    public void setUserLevel(int userLevel) {
        UserLevel = userLevel;
    }

    public int getIsWd() {
        return IsWd;
    }

    public void setIsWd(int isWd) {
        IsWd = isWd;
    }

    public String getUserLevelName() {
        return UserLevelName;
    }

    public void setUserLevelName(String userLevelName) {
        UserLevelName = userLevelName;
    }

    public int getPDTypeId() {
        return PDTypeId;
    }

    public void setPDTypeId(int PDTypeId) {
        this.PDTypeId = PDTypeId;
    }
}