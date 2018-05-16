package com.sh3h.indemnity.ui.setting;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.sh3h.dataprovider.data.entity.enums.Upload;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.util.FileUtil;
import com.sh3h.dataprovider.util.PackageUtil;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivitySettingBinding;
import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.mobileutil.util.ApplicationsUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by dengzhimin on 2017/4/24.
 */

public class SettingActivity extends ParentActivity implements SettingMvpView {

    @Inject
    SettingPresenter mPresenter;

    @Inject
    ConfigHelper mConfigHelper;

    private ActivitySettingBinding mBinding;

    private List<String> mNotDeleteFileNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        mBinding.include.toolbar.setTitle(R.string.activity_setting);
        setSupportActionBar(mBinding.include.toolbar);
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mBinding.setPresenter(mPresenter);
        mBinding.tvVersion.setText(PackageUtil.getVersionName(this));
        mPresenter.calculateFileSize(new int[]{Upload.CANNOT.getValue(), Upload.DEFAULT.getValue()});
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onNetWorkSetting() {
        View customView = LayoutInflater.from(this).inflate(R.layout.dialog_network_setting, null);
        final EditText et = (EditText) customView.findViewById(R.id.et_dialog_network_setting);
        et.setText(mConfigHelper.getBaseUri());
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.text_network_setting)
                .setView(customView)
                .setNegativeButton(R.string.text_cancel, null)
                .setPositiveButton(R.string.text_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = et.getText().toString().trim();
                        if(mConfigHelper.getBaseUri().equals(url)){
                            return;
                        }
                        if(!url.endsWith("/") || url.endsWith("//")){//验证url格式
                            onToast(R.string.text_toast_url_format_error);
                            return;
                        }
                        if(!mConfigHelper.saveNetWorkSetting(url)){
                            onToast(R.string.text_toast_save_fail);
                            return;
                        }
                        mPresenter.resetRestfulApiService();
                    }
                })
                .create();
        dialog.show();
    }

    @Override
    public void onClearCache() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.text_clear_cache)
                .setMessage(R.string.text_dialog_sure_clear_cache)
                .setNegativeButton(R.string.text_cancel, null)
                .setPositiveButton(R.string.text_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //delete data
                        FileUtil.deleteFile(mConfigHelper.getLogFolderFile());
                        FileUtil.deleteFile(mConfigHelper.getImageFolderFile(), mNotDeleteFileNames);
                        mPresenter.calculateFileSize(new int[]{Upload.CANNOT.getValue(), Upload.DEFAULT.getValue()});
                    }
                })
                .create();
        dialog.show();
    }

    @Override
    public void onToast(int resId) {
        ApplicationsUtil.showMessage(getApplicationContext(), resId);
    }

    @Override
    public void onTimeLimit() {
        View customView = LayoutInflater.from(this).inflate(R.layout.dialog_time_limit, null);
        final EditText etStrengthStepTwoTimeLimit = (EditText) customView.findViewById(R.id.et_dialog_strength_step_two_time_limit);
        final EditText etStrengthStepTwoToThreeTimeInterval = (EditText) customView.findViewById(R.id.et_dialog_strength_step_two_to_three_time_interval);
        final EditText etStrengthStepThreeTimeLimit = (EditText) customView.findViewById(R.id.et_dialog_strength_step_three_time_limit);
        final EditText etAirtightStepTwoTimeLimit = (EditText) customView.findViewById(R.id.et_dialog_airtight_step_two_time_limit);
        final EditText etAirtightStepTwoToThreeTimeInterval = (EditText) customView.findViewById(R.id.et_dialog_airtight_step_two_to_three_time_interval);
        final EditText etAirtightStepThreeTimeLimit = (EditText) customView.findViewById(R.id.et_dialog_airtight_step_three_time_limit);
        etStrengthStepTwoTimeLimit.setText(String.valueOf(mConfigHelper.getStrengthStepTwoTimeLimit()/(1000*60)));
        etStrengthStepTwoToThreeTimeInterval.setText(String.valueOf(mConfigHelper.getStrengthStepTwoToThreeTimeInterval()/(1000*60)));
        etStrengthStepThreeTimeLimit.setText(String.valueOf(mConfigHelper.getStrengthStepThreeTimeLimit()/(1000*60)));
        etAirtightStepTwoTimeLimit.setText(String.valueOf(mConfigHelper.getAirtightStepTwoTimeLimit()/(1000*60)));
        etAirtightStepTwoToThreeTimeInterval.setText(String.valueOf(mConfigHelper.getAirtightStepTwoToThreeTimeInterval()/(1000*60)));
        etAirtightStepThreeTimeLimit.setText(String.valueOf(mConfigHelper.getAirtightStepThreeTimeLimit()/(1000*60)));
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.text_accept_time_limit)
                .setView(customView)
                .setNegativeButton(R.string.text_cancel, null)
//                .setPositiveButton(R.string.text_ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String stepTwoTimeLimit = etStepTwoTimeLimit.getText().toString();
//                        String stepTwoToThreeTimeInterval = etStepTwoToThreeTimeInterval.getText().toString();
//                        String stepThreeTimeLimit = etStepThreeTimeLimit.getText().toString();
//                        if (TextUtils.isEmpty(stepTwoTimeLimit) || TextUtils.isEmpty(stepTwoToThreeTimeInterval) || TextUtils.isEmpty(stepThreeTimeLimit)){
//                            onToast(R.string.text_toast_time_input_error);
//                            return;
//                        }
//                        mConfigHelper.saveAcceptTimeLimit(Long.parseLong(stepTwoTimeLimit)*1000*60,
//                                Long.parseLong(stepTwoToThreeTimeInterval)*1000*60,
//                                Long.parseLong(stepThreeTimeLimit)*1000*60);
//                    }
//                })
                .create();
        dialog.show();
    }

    @Override
    public void onCalculateFileSize(List<String> mediaNames) {
        mNotDeleteFileNames = mediaNames;
        long fileSize = FileUtil.getFolderSize(mConfigHelper.getLogFolderPath()) + FileUtil.getFolderSize(mConfigHelper.getImageFolderPath(), mediaNames);
        mBinding.tvClearCache.setText(FileUtil.formatSize(fileSize));
    }
}
