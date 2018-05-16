package com.sh3h.indemnity.ui.accept.budget;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.sh3h.dataprovider.data.entity.DUBudget;
import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.BR;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivityBudgetAcceptBinding;
import com.sh3h.indemnity.ui.accept.branch.AcceptBranchLRActivity;
import com.sh3h.indemnity.ui.accept.construction.AcceptConstructionLRActivity;
import com.sh3h.indemnity.ui.accept.project.AcceptProjectLRActivity;
import com.sh3h.indemnity.ui.adapter.BaseRecyclerViewAdapter;
import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.mobileutil.util.ApplicationsUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by xulongjun on 2017/3/22.
 */

public class BudgetAcceptListActivity extends ParentActivity implements BudgetAcceptListMvpView, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "BudgetAcceptListActivity";

    @Inject
    BudgetAcceptListPresenter mBudgetAcceptListPresenter;

    private ActivityBudgetAcceptBinding mBinding;

    private BaseRecyclerViewAdapter mAdapter;

    private DUProject mProject;

    private int mSelectIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_budget_accept);
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        mBinding.include.toolbar.setTitle(R.string.activity_budget_list);
        setSupportActionBar(mBinding.include.toolbar);
        getActivityComponent().inject(this);
        mBudgetAcceptListPresenter.attachView(this);
        mAdapter = new BaseRecyclerViewAdapter(R.layout.item_project_budget_list, new ArrayList(), BR.duBudget);
        mAdapter.setPresenter(mBudgetAcceptListPresenter, BR.presenter);
        mBinding.setLayoutManager(new LinearLayoutManager(this));
        mBinding.setRefreshListener(this);
        mBinding.setAdapter(mAdapter);

        Intent intent = getIntent();
        if (intent != null) {
            mProject = intent.getParcelableExtra(Constants.INTENT_PARAM_PROJECT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProjectBudgets();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_budget, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_select:
                showSelectDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSelectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.text_select));
        builder.setSingleChoiceItems(R.array.accept_state, mSelectIndex,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSelectIndex = which;
                        if(which == 0){
                            loadProjectBudgets();
                        }else{
                            loadProjectBudgets(which - 1);
                        }
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBudgetAcceptListPresenter.detachView();
    }

    @Override
    public void onError(int resId) {
        mBinding.swipeRefreshLayout.setRefreshing(false);
        onToast(resId);
    }

    @Override
    public void onToast(int resId) {
        ApplicationsUtil.showMessage(this, resId);
    }

    @Override
    public void onNotify(List<DUBudget> duBudgets) {
        mBinding.swipeRefreshLayout.setRefreshing(false);
        mAdapter.setDatas(duBudgets);
    }

    @Override
    public void onIntentByType(String type, DUBudget duBudget) {
        Intent intent = new Intent();
        intent.putExtra(Constants.INTENT_PARAM_BUDGET, duBudget);
        switch (type) {
            case Constants.FLAG_ACCEPT_STRENGTH:
                intent.setClass(this, AcceptBranchLRActivity.class);
                intent.putExtra(Constants.INTENT_PARAM_ACCEPT_TYPE, type);
                break;
            case Constants.FLAG_ACCEPT_AIRTIGHT:
                intent.setClass(this, AcceptBranchLRActivity.class);
                intent.putExtra(Constants.INTENT_PARAM_ACCEPT_TYPE, type);
                break;
            case Constants.FLAG_ACCEPT_PROJECT:
                intent.setClass(this, AcceptProjectLRActivity.class);
                break;
            case Constants.FLAG_ACCEPT_CONSTRUCTION:
                intent.setClass(this, AcceptConstructionLRActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        loadProjectBudgets();
    }

    private void loadProjectBudgets(){
        mBinding.swipeRefreshLayout.setRefreshing(true);
        mBudgetAcceptListPresenter.loadProjectBudgets(NetworkUtil.isNetworkConnected(this), mProject.getProjectId());
    }

    private void loadProjectBudgets(int state){
        mBinding.swipeRefreshLayout.setRefreshing(true);
        mBudgetAcceptListPresenter.loadProjectBudgets(NetworkUtil.isNetworkConnected(this), mProject.getProjectId(), state);
    }
}
