package com.sh3h.indemnity.ui.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.indemnity.MainApplication;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.dialog.LoadDialog;
import com.sh3h.indemnity.injection.component.ActivityComponent;
import com.sh3h.indemnity.injection.component.DaggerActivityComponent;
import com.sh3h.indemnity.injection.module.ActivityModule;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BaseActivity extends AppCompatActivity {

    public static final String TAG = BaseActivity.class.getSimpleName();

    private static final int PERMISSION_REQUEST_CODE = 0x0000001;

    @Inject
    ConfigHelper mConfigHelper;

    private ActivityComponent mActivityComponent;

    private LoadDialog mProgressDialog;

    private boolean mStartAnimation;
    private boolean mEndAnimation;

    private boolean mIsInitConfig;
    //程序必要权限
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    public BaseActivity() {
        mActivityComponent = null;
        mStartAnimation = true;
        mEndAnimation = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setForwardAnimation();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkPermissions();
        }else{
            this.initConfig();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (mStartAnimation) {
            // 设置切换动画，从右边进入，左边退出
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (mStartAnimation) {
            // 设置切换动画，从右边进入，左边退出
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (mEndAnimation) {
            // 设置切换动画，从左边进入，右边退出
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for(int grantResult : grantResults){
                if(grantResult == PackageManager.PERMISSION_DENIED){
                    ApplicationsUtil.showMessage(this, R.string.text_toast_denied_permission);
                    finish();//缺少必要权限关闭页面
                    return;
                }
            }
            //申请权限完成 初始化配置文件
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                this.initConfig();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermissions(){
        List<String> permissions = new ArrayList<>();
        for(String permission : PERMISSIONS){
            if(checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED){
                permissions.add(permission);
            }
        }
        if(permissions.size() > 0){//申请权限
            requestPermissions(permissions.toArray(new String[permissions.size()]), PERMISSION_REQUEST_CODE);
        }else{//无权限申请 初始化配置文件
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                this.initConfig();
            }
        }
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(MainApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

    public void setStartAnimation(boolean animation) {
        mStartAnimation = animation;
    }

    public void setEndAnimation(boolean animation) {
        mEndAnimation = animation;
    }

    public void destroy() {
        finish();
    }

    public void setForwardAnimation() {
        setStartAnimation(true);
        setEndAnimation(false);
    }

    public void setBackwardAnimation() {
        setStartAnimation(false);
        setEndAnimation(true);
    }

    public void setBothAnimation() {
        setStartAnimation(true);
        setEndAnimation(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void showProgress(int resId) {
        if(mProgressDialog == null ){
            mProgressDialog = new LoadDialog(this, R.style.progressDialog);
            mProgressDialog.setMessage(resId);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.setMessage(resId);
        mProgressDialog.show();
    }

    public void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void hideKeyboard(IBinder binder, int flags) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binder, flags);
    }

    /**
     * 初始化配置文件
     */
    protected void initConfig() {
        if(mIsInitConfig){
            return;
        }
        Log.i(TAG, "---initConfig---");
        mConfigHelper.initDefaultConfigs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "---initConfig onCompleted---");
                        LogUtil.initLogger(mConfigHelper.getLogFilePath().getPath());
                        mIsInitConfig = true;
                        //初始化完成 延迟加载
                        lazyLoad();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "---initConfig onToast---" + e.getMessage());
                        mIsInitConfig = false;
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        Log.i(TAG, "---initConfig onNext---");
                    }
                });
    }

    /**
     * 延迟加载
     */
    protected void lazyLoad(){

    }

}
