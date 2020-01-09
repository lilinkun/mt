package com.mingtai.mt.entity;

/**
 * Created by LG on 2020/1/9.
 */
public class ArticleBean {
    private String ArticleId;
    private String CategoryId;
    private String Title;
    private String Author;
    private String ArticleContent;
    private int Flag;
    private boolean IsLink;
    private boolean IsAuditing;
    private String SortRank;
    private String CreateDate;
    private String CategoryName;
    private String Link;
    private String FileUrl;
    private String Summary;
    private String Thumbnail;
    private String Color;
    private String Font;
    private int FontSize;

    public String getArticleId() {
        return ArticleId;
    }

    public void setArticleId(String articleId) {
        ArticleId = articleId;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getArticleContent() {
        return ArticleContent;
    }

    public void setArticleContent(String articleContent) {
        ArticleContent = articleContent;
    }

    public int getFlag() {
        return Flag;
    }

    public void setFlag(int flag) {
        Flag = flag;
    }

    public boolean isLink() {
        return IsLink;
    }

    public void setLink(boolean link) {
        IsLink = link;
    }

    public boolean isAuditing() {
        return IsAuditing;
    }

    public void setAuditing(boolean auditing) {
        IsAuditing = auditing;
    }

    public String getSortRank() {
        return SortRank;
    }

    public void setSortRank(String sortRank) {
        SortRank = sortRank;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String fileUrl) {
        FileUrl = fileUrl;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getFont() {
        return Font;
    }

    public void setFont(String font) {
        Font = font;
    }

    public int getFontSize() {
        return FontSize;
    }

    public void setFontSize(int fontSize) {
        FontSize = fontSize;
    }
}
