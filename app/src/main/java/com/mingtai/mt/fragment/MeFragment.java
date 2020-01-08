package com.mingtai.mt.fragment;

import android.os.Bundle;
import android.view.View;

import com.mingtai.mt.R;
import com.mingtai.mt.activity.OrderListActivity;
import com.mingtai.mt.base.BaseFragment;
import com.mingtai.mt.util.UiHelper;

import butterknife.OnClick;

/**
 * Created by LG on 2019/12/25.
 */
public class MeFragment extends BaseFragment {

    @Override
    public int getlayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initEventAndData() {

    }

    @OnClick({R.id.rl_my_all_order,R.id.ll_wait_pay,R.id.ll_wait_receiver,R.id.ll_wait_deliver})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_my_all_order:

                setDumpOrderList(0);

                break;

            case R.id.ll_wait_pay:

                setDumpOrderList(1);
                break;

            case R.id.ll_wait_deliver:

                setDumpOrderList(2);

                break;
            case R.id.ll_wait_receiver:

                setDumpOrderList(3);

                break;
        }
    }


    public void setDumpOrderList(int positon){

        Bundle bundle = new Bundle();
        bundle.putInt("position", positon);
        UiHelper.launcherBundle(getActivity(), OrderListActivity.class, bundle);
    }
}
