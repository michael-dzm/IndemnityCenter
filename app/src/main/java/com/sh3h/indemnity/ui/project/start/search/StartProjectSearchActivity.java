package com.sh3h.indemnity.ui.project.start.search;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivityStartProjectSearchBinding;
import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.indemnity.ui.project.start.StartProjectActivity;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.zxing.activity.CaptureActivity;

import javax.inject.Inject;

/**
 * Created by dengzhimin on 2017/3/7.
 */

public class StartProjectSearchActivity extends ParentActivity implements StartProjectSearchMvpView{

    public static final String TAG = StartProjectSearchActivity.class.getSimpleName();
    public static final int REQUEST_CODE_SCAN = 0x0000001;

    @Inject
    StartProjectSearchPresenter mPresenter;

    @Inject
    PreferencesHelper mPreferencesHelper;

    private ActivityStartProjectSearchBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_start_project_search);
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        mBinding.include.toolbar.setTitle(R.string.activity_start_project_search);
        setSupportActionBar( mBinding.include.toolbar);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            mBinding.setProjectNumber(bundle.getString("result"));
        }
    }

    @Override
    public void onToast(int resId) {
        ApplicationsUtil.showMessage(this, resId);
    }

    @Override
    public void onError(int resId) {
        hideProgress();
        onToast(resId);
    }

    @Override
    public void onSuccess(DUProject project) {
        hideProgress();
        Intent intent = new Intent(this, StartProjectActivity.class);
        intent.putExtra(Constants.INTENT_PARAM_PROJECT, project);
        startActivity(intent);
    }

    @Override
    public void onScanCode() {
        Intent openCameraIntent = new Intent(this, CaptureActivity.class);
        startActivityForResult(openCameraIntent, REQUEST_CODE_SCAN);
    }

    @Override
    public void onSearchProject(String projectNumber) {
        if(TextUtils.isEmpty(projectNumber)){
            onToast(R.string.text_toast_project_number_not_empty);
            return;
        }
        showProgress(R.string.text_dialog_searching);
        mPresenter.searchProject(NetworkUtil.isNetworkConnected(this), projectNumber);
    }

    @Override
    public void onLoadData() {
        //no need load data
    }

    @Override
    public void onExit(int resId) {
        onToast(resId);
        finish();
    }

    @Override
    public void onCompareUser() {
        //主程序的userId与子程序的userId不一样 子程序必须重新加载用户信息 无网络连接则退出
        int userId = getIntent().getIntExtra(Constants.INTENT_PARAM_USERID, 0);
        if(userId == 0){
            onExit(R.string.text_toast_host_app_user_info_error);
            return;
        }
        mPresenter.loadWords();
        if(mPreferencesHelper.getUserSession().getUserId() != userId){
            if(!NetworkUtil.isNetworkConnected(this)) finish();
            mPresenter.getUserInfo(userId);
        }else{
            onLoadData();
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        //对比主程序uri与子程序uri 避免主程序和子程序访问不同服务器数据
        mPresenter.compareUri(getIntent().getStringExtra(Constants.INTENT_PARAM_BASEURI));
    }
}
