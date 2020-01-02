package com.mingtai.mt.entity;

/**
 * Created by LG on 2020/1/2.
 */
public class HomeBean {

    private String UpgradeUrl;
    private String UpgradeToken;
    private String RegisterRequirements;
    private int IsAuditing;
    private int IsAndroidAuditing;
    private int IsXPAuditing;
    private String ImgVer;
    private String KFMobile;
    private boolean IsPDType;

    public String getUpgradeUrl() {
        return UpgradeUrl;
    }

    public void setUpgradeUrl(String upgradeUrl) {
        UpgradeUrl = upgradeUrl;
    }

    public String getUpgradeToken() {
        return UpgradeToken;
    }

    public void setUpgradeToken(String upgradeToken) {
        UpgradeToken = upgradeToken;
    }

    public String getRegisterRequirements() {
        return RegisterRequirements;
    }

    public void setRegisterRequirements(String registerRequirements) {
        RegisterRequirements = registerRequirements;
    }

    public int getIsAuditing() {
        return IsAuditing;
    }

    public void setIsAuditing(int isAuditing) {
        IsAuditing = isAuditing;
    }

    public int getIsAndroidAuditing() {
        return IsAndroidAuditing;
    }

    public void setIsAndroidAuditing(int isAndroidAuditing) {
        IsAndroidAuditing = isAndroidAuditing;
    }

    public int getIsXPAuditing() {
        return IsXPAuditing;
    }

    public void setIsXPAuditing(int isXPAuditing) {
        IsXPAuditing = isXPAuditing;
    }

    public String getImgVer() {
        return ImgVer;
    }

    public void setImgVer(String imgVer) {
        ImgVer = imgVer;
    }

    public String getKFMobile() {
        return KFMobile;
    }

    public void setKFMobile(String KFMobile) {
        this.KFMobile = KFMobile;
    }

    public boolean isPDType() {
        return IsPDType;
    }

    public void setPDType(boolean PDType) {
        IsPDType = PDType;
    }
}
