package com.mingtai.mt.fragment;

import android.os.Bundle;
import android.view.View;

import com.mingtai.mt.R;
import com.mingtai.mt.activity.RegisterActivity;
import com.mingtai.mt.activity.SaleActivity;
import com.mingtai.mt.base.BaseFragment;
import com.mingtai.mt.util.UiHelper;

import butterknife.OnClick;

/**
 * Created by LG on 2019/12/25.
 */
public class DeclarationFragment extends BaseFragment {

    @Override
    public int getlayoutId() {
        return R.layout.fragment_declaration;
    }

    @Override
    public void initEventAndData() {

    }

    @OnClick({R.id.ll_register,R.id.ll_sale,R.id.ll_update,R.id.ll_tiaobo})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ll_register:

                UiHelper.launcher(getActivity(), RegisterActivity.class);

                break;

            case R.id.ll_sale:

                Bundle bundle = new Bundle();
                bundle.putInt("type",1);
                UiHelper.launcherBundle(getActivity(), SaleActivity.class,bundle);

                break;

            case R.id.ll_update:

                Bundle bundle1 = new Bundle();
                bundle1.putInt("type",2);
                UiHelper.launcherBundle(getActivity(), SaleActivity.class,bundle1);
                break;

            case R.id.ll_tiaobo:

                Bundle bundle2 = new Bundle();
                bundle2.putInt("type",3);
                UiHelper.launcherBundle(getActivity(), SaleActivity.class,bundle2);

                break;

        }
    }
}
