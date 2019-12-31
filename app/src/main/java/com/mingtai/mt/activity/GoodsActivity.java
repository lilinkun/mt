package com.mingtai.mt.activity;

import android.os.Handler;
import android.os.Message;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.ui.PagerSlidingTabStrip;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by kai
 * on 2019/12/31
 */
public class GoodsActivity extends BaseActivity {

    @BindView(R.id.tab_strip)
    PagerSlidingTabStrip pagerSlidingTabStrip;

    String[] strings = {"精选","护肤套装", "防晒脱毛", "彩妆香水", "面部精华", "男士服饰", "化妆品", "文体车品", "鞋包", "数码", "内衣"};
    ArrayList<String> arrayList = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods;
    }

    @Override
    public void initEventAndData() {

        for (int i = 0; i < strings.length;i++){
            arrayList.add(strings[i]);
        }

        pagerSlidingTabStrip.setTitles(arrayList, 0, handler);
    }
}
