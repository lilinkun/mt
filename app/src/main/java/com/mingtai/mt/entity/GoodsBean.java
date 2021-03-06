package com.mingtai.mt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LG on 2020/1/3.
 */
public class GoodsBean implements Parcelable {
    private String GoodsId;
    private String CategoryId;
    private String CategoryPath;
    private String BrandId;
    private String GoodsSn;
    private String GoodsName;
    private String GoodsSmallName;
    private String GoodsSpec1;
    private String GoodsSpec2;
    private String GoodsUnit;
    private String GoodsGG;
    private int BrowserCount;
    private int GoodsNumber;
    private int UseNumber;
    private int GoodsWeight;
    private double MarketPrice;
    private double Price;
    private String GoodsBrief;
    private String GoodsDesc;
    private String GoodsImg;
    private String GoodsImgList;
    private int GoodsIndexType;
    private String GoodsIndexImg;
    private boolean IsCarriage;
    private boolean IsOnSale;
    private boolean IsDelete;
    private int GoodsFlag;
    private int GoodsType;
    private String GoodsTypeName;
    private String GoodsIndexTypeName;
    private String GoodsFlagName;
    private int Integral;
    private int ReturnIntegral;
    private String CreateDate;
    private String LastUpdate;
    private String MobileDesc;
    private int Qty;
    private String BackSay;
    private String SortRank;
    private String BeginDate;
    private String EndDate;
    private int IsSetMeal;
    private int UpgradeIntegral;
    private int UpgradePrice;
    private int IsPresell;

    protected GoodsBean(Parcel in) {
        GoodsId = in.readString();
        CategoryId = in.readString();
        CategoryPath = in.readString();
        BrandId = in.readString();
        GoodsSn = in.readString();
        GoodsName = in.readString();
        GoodsSmallName = in.readString();
        GoodsSpec1 = in.readString();
        GoodsSpec2 = in.readString();
        GoodsUnit = in.readString();
        GoodsGG = in.readString();
        BrowserCount = in.readInt();
        GoodsNumber = in.readInt();
        UseNumber = in.readInt();
        GoodsWeight = in.readInt();
        MarketPrice = in.readDouble();
        Price = in.readDouble();
        GoodsBrief = in.readString();
        GoodsDesc = in.readString();
        GoodsImg = in.readString();
        GoodsImgList = in.readString();
        GoodsIndexType = in.readInt();
        GoodsIndexImg = in.readString();
        IsCarriage = in.readByte() != 0;
        IsOnSale = in.readByte() != 0;
        IsDelete = in.readByte() != 0;
        GoodsFlag = in.readInt();
        GoodsType = in.readInt();
        GoodsTypeName = in.readString();
        GoodsIndexTypeName = in.readString();
        GoodsFlagName = in.readString();
        Integral = in.readInt();
        ReturnIntegral = in.readInt();
        CreateDate = in.readString();
        LastUpdate = in.readString();
        MobileDesc = in.readString();
        Qty = in.readInt();
        BackSay = in.readString();
        SortRank = in.readString();
        BeginDate = in.readString();
        EndDate = in.readString();
        IsSetMeal = in.readInt();
        UpgradeIntegral = in.readInt();
        UpgradePrice = in.readInt();
        IsPresell = in.readInt();
    }

    public static final Creator<GoodsBean> CREATOR = new Creator<GoodsBean>() {
        @Override
        public GoodsBean createFromParcel(Parcel in) {
            return new GoodsBean(in);
        }

        @Override
        public GoodsBean[] newArray(int size) {
            return new GoodsBean[size];
        }
    };

    public String getGoodsId() {
        return GoodsId;
    }

    public void setGoodsId(String goodsId) {
        GoodsId = goodsId;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryPath() {
        return CategoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        CategoryPath = categoryPath;
    }

    public String getBrandId() {
        return BrandId;
    }

    public void setBrandId(String brandId) {
        BrandId = brandId;
    }

    public String getGoodsSn() {
        return GoodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        GoodsSn = goodsSn;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

    public String getGoodsSmallName() {
        return GoodsSmallName;
    }

    public void setGoodsSmallName(String goodsSmallName) {
        GoodsSmallName = goodsSmallName;
    }

    public String getGoodsSpec1() {
        return GoodsSpec1;
    }

    public void setGoodsSpec1(String goodsSpec1) {
        GoodsSpec1 = goodsSpec1;
    }

    public String getGoodsSpec2() {
        return GoodsSpec2;
    }

    public void setGoodsSpec2(String goodsSpec2) {
        GoodsSpec2 = goodsSpec2;
    }

    public String getGoodsUnit() {
        return GoodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        GoodsUnit = goodsUnit;
    }

    public String getGoodsGG() {
        return GoodsGG;
    }

    public void setGoodsGG(String goodsGG) {
        GoodsGG = goodsGG;
    }

    public int getBrowserCount() {
        return BrowserCount;
    }

    public void setBrowserCount(int browserCount) {
        BrowserCount = browserCount;
    }

    public int getGoodsNumber() {
        return GoodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        GoodsNumber = goodsNumber;
    }

    public int getUseNumber() {
        return UseNumber;
    }

    public void setUseNumber(int useNumber) {
        UseNumber = useNumber;
    }

    public int getGoodsWeight() {
        return GoodsWeight;
    }

    public void setGoodsWeight(int goodsWeight) {
        GoodsWeight = goodsWeight;
    }

    public double getMarketPrice() {
        return MarketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        MarketPrice = marketPrice;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getGoodsBrief() {
        return GoodsBrief;
    }

    public void setGoodsBrief(String goodsBrief) {
        GoodsBrief = goodsBrief;
    }

    public String getGoodsDesc() {
        return GoodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        GoodsDesc = goodsDesc;
    }

    public String getGoodsImg() {
        return GoodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        GoodsImg = goodsImg;
    }

    public String getGoodsImgList() {
        return GoodsImgList;
    }

    public void setGoodsImgList(String goodsImgList) {
        GoodsImgList = goodsImgList;
    }

    public int getGoodsIndexType() {
        return GoodsIndexType;
    }

    public void setGoodsIndexType(int goodsIndexType) {
        GoodsIndexType = goodsIndexType;
    }

    public String getGoodsIndexImg() {
        return GoodsIndexImg;
    }

    public void setGoodsIndexImg(String goodsIndexImg) {
        GoodsIndexImg = goodsIndexImg;
    }

    public boolean isCarriage() {
        return IsCarriage;
    }

    public void setCarriage(boolean carriage) {
        IsCarriage = carriage;
    }

    public boolean isOnSale() {
        return IsOnSale;
    }

    public void setOnSale(boolean onSale) {
        IsOnSale = onSale;
    }

    public boolean isDelete() {
        return IsDelete;
    }

    public void setDelete(boolean delete) {
        IsDelete = delete;
    }

    public int getGoodsFlag() {
        return GoodsFlag;
    }

    public void setGoodsFlag(int goodsFlag) {
        GoodsFlag = goodsFlag;
    }

    public int getGoodsType() {
        return GoodsType;
    }

    public void setGoodsType(int goodsType) {
        GoodsType = goodsType;
    }

    public String getGoodsTypeName() {
        return GoodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        GoodsTypeName = goodsTypeName;
    }

    public String getGoodsIndexTypeName() {
        return GoodsIndexTypeName;
    }

    public void setGoodsIndexTypeName(String goodsIndexTypeName) {
        GoodsIndexTypeName = goodsIndexTypeName;
    }

    public String getGoodsFlagName() {
        return GoodsFlagName;
    }

    public void setGoodsFlagName(String goodsFlagName) {
        GoodsFlagName = goodsFlagName;
    }

    public int getIntegral() {
        return Integral;
    }

    public void setIntegral(int integral) {
        Integral = integral;
    }

    public int getReturnIntegral() {
        return ReturnIntegral;
    }

    public void setReturnIntegral(int returnIntegral) {
        ReturnIntegral = returnIntegral;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        LastUpdate = lastUpdate;
    }

    public String getMobileDesc() {
        return MobileDesc;
    }

    public void setMobileDesc(String mobileDesc) {
        MobileDesc = mobileDesc;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public String getBackSay() {
        return BackSay;
    }

    public void setBackSay(String backSay) {
        BackSay = backSay;
    }

    public String getSortRank() {
        return SortRank;
    }

    public void setSortRank(String sortRank) {
        SortRank = sortRank;
    }

    public String getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(String beginDate) {
        BeginDate = beginDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public int getIsSetMeal() {
        return IsSetMeal;
    }

    public void setIsSetMeal(int isSetMeal) {
        IsSetMeal = isSetMeal;
    }

    public int getUpgradeIntegral() {
        return UpgradeIntegral;
    }

    public void setUpgradeIntegral(int upgradeIntegral) {
        UpgradeIntegral = upgradeIntegral;
    }

    public int getUpgradePrice() {
        return UpgradePrice;
    }

    public void setUpgradePrice(int upgradePrice) {
        UpgradePrice = upgradePrice;
    }

    public int getIsPresell() {
        return IsPresell;
    }

    public void setIsPresell(int isPresell) {
        IsPresell = isPresell;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(GoodsId);
        dest.writeString(CategoryId);
        dest.writeString(CategoryPath);
        dest.writeString(BrandId);
        dest.writeString(GoodsSn);
        dest.writeString(GoodsName);
        dest.writeString(GoodsSmallName);
        dest.writeString(GoodsSpec1);
        dest.writeString(GoodsSpec2);
        dest.writeString(GoodsUnit);
        dest.writeString(GoodsGG);
        dest.writeInt(BrowserCount);
        dest.writeInt(GoodsNumber);
        dest.writeInt(UseNumber);
        dest.writeInt(GoodsWeight);
        dest.writeDouble(MarketPrice);
        dest.writeDouble(Price);
        dest.writeString(GoodsBrief);
        dest.writeString(GoodsDesc);
        dest.writeString(GoodsImg);
        dest.writeString(GoodsImgList);
        dest.writeInt(GoodsIndexType);
        dest.writeString(GoodsIndexImg);
        dest.writeByte((byte) (IsCarriage ? 1 : 0));
        dest.writeByte((byte) (IsOnSale ? 1 : 0));
        dest.writeByte((byte) (IsDelete ? 1 : 0));
        dest.writeInt(GoodsFlag);
        dest.writeInt(GoodsType);
        dest.writeString(GoodsTypeName);
        dest.writeString(GoodsIndexTypeName);
        dest.writeString(GoodsFlagName);
        dest.writeInt(Integral);
        dest.writeInt(ReturnIntegral);
        dest.writeString(CreateDate);
        dest.writeString(LastUpdate);
        dest.writeString(MobileDesc);
        dest.writeInt(Qty);
        dest.writeString(BackSay);
        dest.writeString(SortRank);
        dest.writeString(BeginDate);
        dest.writeString(EndDate);
        dest.writeInt(IsSetMeal);
        dest.writeInt(UpgradeIntegral);
        dest.writeInt(UpgradePrice);
        dest.writeInt(IsPresell);
    }
}