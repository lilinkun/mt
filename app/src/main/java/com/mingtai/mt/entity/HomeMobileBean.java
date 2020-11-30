package com.mingtai.mt.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2020/1/9.
 */
public class HomeMobileBean<T> {
    private ArrayList<FlashBean> flash;
    private ArrayList<ArticleBean> News;
    private T NewsOpen;

    public ArrayList<FlashBean> getFlash() {
        return flash;
    }

    public void setFlash(ArrayList<FlashBean> flash) {
        this.flash = flash;
    }

    public ArrayList<ArticleBean> getNews() {
        return News;
    }

    public void setNews(ArrayList<ArticleBean> news) {
        News = news;
    }

    public T getNewsOpen() {
        return NewsOpen;
    }

    public void setNewsOpen(T newsOpen) {
        NewsOpen = newsOpen;
    }
}
