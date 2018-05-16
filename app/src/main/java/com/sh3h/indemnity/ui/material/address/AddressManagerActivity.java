package com.sh3h.indemnity.ui.material.address;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.sh3h.dataprovider.data.entity.DUAddress;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.BR;
import com.sh3h.indemnity.databinding.ActivityAddressManagerListBinding;
import com.sh3h.indemnity.ui.adapter.BaseRecyclerViewAdapter;
import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.mobileutil.util.ApplicationsUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Created by xulongjun on 2017/3/9.
 */

public class AddressManagerActivity extends ParentActivity implements AddressManagerMvpView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    AddressManagerPresenter mAddressManagerPresenter;

    @Inject
    PreferencesHelper mPreferencesHelper;

    private ActivityAddressManagerListBinding mBinding;

    private BaseRecyclerViewAdapter mAdapter;

    private EditText mAddressEditText;

    private String mIntentFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_address_manager_list);
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        mBinding.include.toolbar.setTitle(R.string.activity_address_manager);
        setSupportActionBar(mBinding.include.toolbar);
        getActivityComponent().inject(this);
        mAddressManagerPresenter.attachView(this);
        mBinding.setPresenter(mAddressManagerPresenter);
        mAdapter = new BaseRecyclerViewAdapter(R.layout.item_address_manager_list, new ArrayList(), BR.duAddress);
        mAdapter.setPresenter(mAddressManagerPresenter, BR.presenter);
        mBinding.setLayoutManager(new LinearLayoutManager(this));
        mBinding.setAdapter(mAdapter);
        mBinding.setRefreshListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddressManagerPresenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK, null);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void changeDefaultAddress(View view, DUAddress duAddress) {
        if(NetworkUtil.isNetworkConnected(this)){
            if(((CheckBox)view).isChecked()){
                duAddress.setIsDefault(DUAddress.Default.YES.getValue());
            }else{
                duAddress.setIsDefault(DUAddress.Default.NO.getValue());
            }
        }
        //do update
        mAddressManagerPresenter.updateAddress(NetworkUtil.isNetworkConnected(getApplicationContext()), duAddress);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNotify(List<DUAddress> duAddresses) {
        mBinding.swiperefreshLayout.setRefreshing(false);
        mAdapter.setDatas(duAddresses);
    }

    @Override
    public void onToast(int resId) {
        ApplicationsUtil.showMessage(this, resId);
    }

    @Override
    public void onError(int resId) {
        onToast(resId);
        mBinding.swiperefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(DUAddress duAddress) {
        if(mIntentFlag == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(Constants.INTENT_PARAM_ADDRESS, duAddress);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void showAddAddressDialog() {
        mAddressEditText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.text_add_address_title));
        builder.setView(mAddressEditText, 20, 20, 20, 20);
        builder.setNegativeButton(getString(R.string.text_dialog_button_cancel), null);
        builder.setPositiveButton(getString(R.string.text_dialog_button_sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String address = mAddressEditText.getText().toString().trim();
                if("".equals(address)) return;
                DUAddress duAddress = new DUAddress();
                duAddress.setAddressId(null);
                duAddress.setUserId((long)mPreferencesHelper.getUserSession().getUserId());
                duAddress.setAddressContent(address);
                duAddress.setIsDefault(DUAddress.Default.NO.getValue());
                duAddress.setOperateTime(System.currentTimeMillis());
                mAddressManagerPresenter.addAddress(NetworkUtil.isNetworkConnected(getApplicationContext()), duAddress, mPreferencesHelper.getUserSession().getUserId());
            }
        });
        builder.show();
    }

    @Override
    public void showDeleteAddressDialog(final DUAddress duAddress) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.text_dialog_title_prompt));
        builder.setMessage(getString(R.string.text_is_delete_address_title));
        builder.setNegativeButton(getString(R.string.text_dialog_button_cancel), null);
        builder.setPositiveButton(getString(R.string.text_dialog_button_sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAddressManagerPresenter.deleteAddress(NetworkUtil.isNetworkConnected(getApplicationContext()), duAddress);
            }
        });
        builder.show();
    }

    @Override
    public void showUpdateAddressDialog(final DUAddress duAddress) {
        mAddressEditText = new EditText(this);
        mAddressEditText.setText(duAddress.getAddressContent());
        mAddressEditText.setSelection(duAddress.getAddressContent().length());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.text_update_address_title));
        builder.setView(mAddressEditText, 20, 20, 20, 20);
        builder.setNegativeButton(getString(R.string.text_dialog_button_cancel), null);
        builder.setPositiveButton(getString(R.string.text_dialog_button_sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String address = mAddressEditText.getText().toString().trim();
                if("".equals(address)) return;
                if(address.equals(duAddress.getAddressContent())) return;
                //do update
                if(NetworkUtil.isNetworkConnected(getApplicationContext())){
                    duAddress.setAddressContent(address);
                }
                mAddressManagerPresenter.updateAddress(NetworkUtil.isNetworkConnected(getApplicationContext()), duAddress);
            }
        });
        builder.show();
    }

    @Override
    public void onLoadData() {
        //load address data
        loadAddresses();
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
        if(mPreferencesHelper.getUserSession().getUserId() != userId){
            if(!NetworkUtil.isNetworkConnected(this)) finish();
            mAddressManagerPresenter.getUserInfo(userId);
        }else{
            onLoadData();
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        Intent intent = getIntent();
        mIntentFlag = intent.getStringExtra(Constants.INTENT_PARAM_FLAG);
        if(mIntentFlag != null){//从材料申领界面进入不需要同步用户信息
            onLoadData();
            return;
        }
        //对比主程序uri与子程序uri 避免主程序和子程序访问不同服务器数据
        mAddressManagerPresenter.compareUri(intent.getStringExtra(Constants.INTENT_PARAM_BASEURI));
    }

    @Override
    public void onRefresh() {
        loadAddresses();
    }

    private void loadAddresses(){
        mBinding.swiperefreshLayout.setRefreshing(true);
        mAddressManagerPresenter.loadAddresses(NetworkUtil.isNetworkConnected(this), mPreferencesHelper.getUserSession().getUserId());
    }
}
