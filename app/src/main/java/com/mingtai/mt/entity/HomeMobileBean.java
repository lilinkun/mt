package com.mingtai.mt.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2020/1/9.
 */
public class HomeMobileBean {
    private ArrayList<FlashBean> flash;
    private ArrayList<ArticleBean> News;

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
}
