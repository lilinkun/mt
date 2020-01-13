package com.mingtai.mt.ui;

import android.content.Context;
import android.widget.ImageView;

import com.mingtai.mt.R;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.entity.FlashBean;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

/**
 * Created by LG on 2020/1/9.
 */
public class CustomBannerView {

    /**
     * @param flashBeans
     * @param banner
     * @param context
     * @param isImageLoader 是否使用自带的imageloader
     */
    public static void startBanner(final ArrayList<FlashBean> flashBeans, Banner banner, final Context context, boolean isImageLoader) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < flashBeans.size(); i++) {
            strings.add("111111" + i);
        }

       /* //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);

        if (isImageLoader) {
            //设置图片加载器，图片加载器在下方
            banner.setImageLoader(new ImageLoaderInterface() {
                @Override
                public void displayImage(Context context, Object path, View imageView) {
                    Picasso.with(context).load(ProApplication.BANNERIMG + ((FlashBean) path).getFlashPic()).error(R.color.line).into((ImageView) imageView);
                }

                @Override
                public View createImageView(Context context) {
                    return null;
                }
            });
        } else {
            //设置图片加载器，图片加载器在下方
            banner.setImageLoader(new CustomRoundedImageLoader());
        }
        //设置图片网址或地址的集合
        banner.setImages(flashBeans);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.RotateDown);

        banner.setPageTransformer(true, new BannerTransform());

        //设置轮播图的标题集合
        banner.setBannerTitles(strings);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {

                    }
                })
                //必须最后调用的方法，启动轮播图。
                .start();*/


        //设置banner样式(显示圆形指示器)
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置（指示器居中）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Picasso.with(context).load(ProApplication.BANNERIMG + ((FlashBean)path).getFlashPic()).error(R.color.line).into(imageView);
            }
        });
        //设置图片集合
        banner.setImages(flashBeans);
        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
//        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }

    /**
     * @param flashBeans
     * @param banner
     * @param context
     * @param isImageLoader 是否使用自带的imageloader
     */
    public static void startB(final ArrayList<String> list, Banner banner, final Context context, boolean isImageLoader) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            strings.add("111111" + i);
        }


        //设置banner样式(显示圆形指示器)
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置（指示器居中）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Picasso.with(context).load(ProApplication.BANNERIMG + path).error(R.color.line).into(imageView);
            }
        });
        //设置图片集合
        banner.setImages(list);
        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
//        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }


}
