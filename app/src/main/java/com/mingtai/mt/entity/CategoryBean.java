package com.mingtai.mt.entity;

/**
 * 商品分类
 * Created by LG on 2020/1/3.
 */
public class CategoryBean {

    private String CategoryID;
    private String CategoryParentId;
    private String CategoryName;
    private String CategoryImg;
    private int CategoryLevel;
    private String CategoryPath;
    private boolean IsUse;
    private boolean IsLeaf;
    private int SortRank;

    public CategoryBean() {
    }

    public CategoryBean(String categoryID, String categoryParentId, String categoryName, String categoryImg, int categoryLevel, String categoryPath, boolean isUse, boolean isLeaf, int sortRank) {
        CategoryID = categoryID;
        CategoryParentId = categoryParentId;
        CategoryName = categoryName;
        CategoryImg = categoryImg;
        CategoryLevel = categoryLevel;
        CategoryPath = categoryPath;
        IsUse = isUse;
        IsLeaf = isLeaf;
        SortRank = sortRank;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryParentId() {
        return CategoryParentId;
    }

    public void setCategoryParentId(String categoryParentId) {
        CategoryParentId = categoryParentId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryImg() {
        return CategoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        CategoryImg = categoryImg;
    }

    public int getCategoryLevel() {
        return CategoryLevel;
    }

    public void setCategoryLevel(int categoryLevel) {
        CategoryLevel = categoryLevel;
    }

    public String getCategoryPath() {
        return CategoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        CategoryPath = categoryPath;
    }

    public boolean isUse() {
        return IsUse;
    }

    public void setUse(boolean use) {
        IsUse = use;
    }

    public boolean isLeaf() {
        return IsLeaf;
    }

    public void setLeaf(boolean leaf) {
        IsLeaf = leaf;
    }

    public int getSortRank() {
        return SortRank;
    }

    public void setSortRank(int sortRank) {
        SortRank = sortRank;
    }
}