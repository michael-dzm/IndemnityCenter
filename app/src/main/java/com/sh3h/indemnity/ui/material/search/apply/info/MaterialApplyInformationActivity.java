package com.sh3h.indemnity.ui.material.search.apply.info;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.sh3h.dataprovider.data.entity.DUMaterialApply;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivityMaterialApplyInformationBinding;
import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.indemnity.util.Constants;

import javax.inject.Inject;

/**
 * Created by xulongjun on 2017/3/8.
 */

public class MaterialApplyInformationActivity extends ParentActivity implements MaterialApplyInformationMvpView{

    @Inject
    MaterialApplyInformationPresenter mMaterialApplyInformationPresenter;

    private ActivityMaterialApplyInformationBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_material_apply_information);
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        mBinding.include.toolbar.setTitle(R.string.activity_material_apply_information);
        setSupportActionBar( mBinding.include.toolbar);
        getActivityComponent().inject(this);
        mMaterialApplyInformationPresenter.attachView(this);

        Intent intent = getIntent();
        DUMaterialApply duMaterialApply = intent.getParcelableExtra(Constants.INTENT_PARAM_MATERIAL_APPLY);
        mBinding.setDuMaterialApply(duMaterialApply);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMaterialApplyInformationPresenter.detachView();
    }
}
