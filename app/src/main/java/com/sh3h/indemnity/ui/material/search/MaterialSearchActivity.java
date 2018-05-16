package com.sh3h.indemnity.ui.material.search;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivityMaterialSearchBinding;
import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.indemnity.ui.material.search.apply.MaterialApplySearchFragment;
import com.sh3h.indemnity.ui.material.search.confirm.MaterialConfirmSearchFragment;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.mobileutil.util.ApplicationsUtil;

import javax.inject.Inject;


/**
 * Created by xulongjun on 2017/4/6.
 */

public class MaterialSearchActivity extends ParentActivity implements MaterialSearchMvpView {
    private static final String TAG = "MaterialSearchActivity";

    @Inject
    MaterialSearchPresenter mMaterialSearchPresenter;

    @Inject
    PreferencesHelper mPreferencesHelper;

    private ActivityMaterialSearchBinding mBinding;

    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_material_search);
        mBinding.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        mBinding.toolbar.setTitle(R.string.activity_material_search);
        setSupportActionBar(mBinding.toolbar);
        getActivityComponent().inject(this);
        mMaterialSearchPresenter.attachView(this);
        myAdapter = new MyAdapter(getSupportFragmentManager(), new Bundle());
        mBinding.viewPager.setAdapter(myAdapter);
        mBinding.tlTabs.setupWithViewPager(mBinding.viewPager);
        mBinding.tlTabs.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMaterialSearchPresenter.detachView();
    }

    @Override
    public void onLoadData() {
        //no need data load
    }

    @Override
    public void onExit(int resId) {
        ApplicationsUtil.showMessage(this, resId);
        finish();
    }

    @Override
    public void onCompareUser() {
        //主程序的userId与子程序的userId不一样 子程序必须重新加载用户信息 无网络连接则退出
        Intent intent = getIntent();
        int userId = intent.getIntExtra(Constants.INTENT_PARAM_USERID, 0);
        if(userId == 0){
            onExit(R.string.text_toast_host_app_user_info_error);
            return;
        }
        if(mPreferencesHelper.getUserSession().getUserId() != userId){
            if(!NetworkUtil.isNetworkConnected(this)) finish();
            mMaterialSearchPresenter.getUserInfo(userId);
        }else{
            onLoadData();
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        //对比主程序uri与子程序uri 避免主程序和子程序访问不同服务器数据
        mMaterialSearchPresenter.compareUri(getIntent().getStringExtra(Constants.INTENT_PARAM_BASEURI));
    }

    private class MyAdapter extends FragmentPagerAdapter {
        public static final int MATERIALAPPLYSEARCH_FRAGMENT = 0;
        public static final int MATERIALCONFIRMSEARCH_FRAGMENT = 1;
        public static final int FRAGMENT_COUNT = 2;
        private FragmentManager mFragmentManager;
        private Bundle mBundle;

        public MyAdapter(FragmentManager fragmentManager, Bundle bundle) {
            super(fragmentManager);
            mFragmentManager = fragmentManager;
            mBundle = bundle;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case MATERIALAPPLYSEARCH_FRAGMENT:
                    fragment = new MaterialApplySearchFragment();
                    break;
                case MATERIALCONFIRMSEARCH_FRAGMENT:
                    fragment = new MaterialConfirmSearchFragment();
                    break;
                default:
                    fragment = new MaterialApplySearchFragment();
                    break;
            }

            if (mBundle != null) {
                fragment.setArguments(mBundle);
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            switch (position) {
                case MATERIALAPPLYSEARCH_FRAGMENT:
                    title = getResources().getString(R.string.text_material_apply_search);
                    break;
                case MATERIALCONFIRMSEARCH_FRAGMENT:
                    title = getResources().getString(R.string.text_material_confirm_search);
                    break;
                default:
                    title = getResources().getString(R.string.text_material_apply_search);
                    break;
            }
            return title;
        }
    }
}
