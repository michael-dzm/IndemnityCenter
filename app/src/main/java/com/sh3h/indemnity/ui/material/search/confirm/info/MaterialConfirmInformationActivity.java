package com.sh3h.indemnity.ui.material.search.confirm.info;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.sh3h.dataprovider.data.entity.DUMaterialVerify;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivityMaterialConfirmInformationBinding;
import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.indemnity.ui.multimedia.MultimediaFragment;
import com.sh3h.indemnity.util.Constants;

import javax.inject.Inject;

/**
 * Created by xulongjun on 2017/3/8.
 */

public class MaterialConfirmInformationActivity extends ParentActivity implements MaterialConfirmInformationMvpView, MultimediaFragment.CameraCallBack{

    @Inject
    MaterialConfirmInformationPresenter mMaterialConfirmInformationPresenter;

    private ActivityMaterialConfirmInformationBinding mBinding;

    private MultimediaFragment mMultimediaFragment;

    private DUMaterialVerify mDuMaterialVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_material_confirm_information);
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        mBinding.include.toolbar.setTitle(R.string.activity_material_confirm_information);
        setSupportActionBar( mBinding.include.toolbar);
        getActivityComponent().inject(this);
        mMaterialConfirmInformationPresenter.attachView(this);

        Intent intent = getIntent();
        mDuMaterialVerify = intent.getParcelableExtra(Constants.INTENT_PARAM_MATERIAL_VERIFY);
        mBinding.setDuMaterialVerify(mDuMaterialVerify);

        mMultimediaFragment = new MultimediaFragment();
        mMultimediaFragment.setCallBack(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.multimedia_container, mMultimediaFragment, mMultimediaFragment.getClass().getName()).commit();

        if(!TextUtils.isEmpty(mDuMaterialVerify.getImageUrl())){
            DUMultiMedia multiMedia = new DUMultiMedia();
            multiMedia.setFilePath(mDuMaterialVerify.getImageUrl());
            mMultimediaFragment.addMultiMedia(multiMedia);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMaterialConfirmInformationPresenter.detachView();
    }

    @Override
    public void onAddImage(String imageName, String imagePath) {

    }

    @Override
    public void onDeleteImage(String imageName, String imagePath) {

    }
}
