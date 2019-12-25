package com.mingtai.mt.fragment;

import android.view.View;

import com.mingtai.mt.R;
import com.mingtai.mt.activity.RegisterActivity;
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

    @OnClick({R.id.ll_register})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ll_register:

                UiHelper.launcher(getActivity(), RegisterActivity.class);


                break;

        }
    }
}
