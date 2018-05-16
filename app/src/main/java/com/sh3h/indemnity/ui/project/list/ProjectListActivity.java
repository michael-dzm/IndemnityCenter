package com.sh3h.indemnity.ui.project.list;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.databinding.library.baseAdapters.BR;
import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivityProjectListBinding;
import com.sh3h.indemnity.ui.accept.budget.BudgetAcceptListActivity;
import com.sh3h.indemnity.ui.adapter.BaseRecyclerViewAdapter;
import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.indemnity.ui.patrol.PatrolActivity;
import com.sh3h.indemnity.ui.project.restart.RestartProjectActivity;
import com.sh3h.indemnity.ui.patrol.search.SearchPatrolActivity;
import com.sh3h.indemnity.ui.project.stop.StopProjectActivity;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.loadmore.listener.OnPullListener;
import com.sh3h.mobileutil.util.ApplicationsUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dengzhimin on 2017/3/7.
 */

public class ProjectListActivity extends ParentActivity implements ProjectListMvpView, OnPullListener{

    public static final String TAG = ProjectListActivity.class.getSimpleName();

    public static final int ACTION_LOAD = 0x00001;
    public static final int ACTION_REFRESH = 0x00002;
    public static final int ACTION_LOADMORE = 0x00003;

    public static final int INDEX = 1;//起始位置
    public static final int OFFSET = 40;//偏移量

    @Inject
    ProjectListPresenter mPresenter;

    @Inject
    PreferencesHelper mPreferencesHelper;

    @Inject
    ConfigHelper mConfigHelper;

    private String mIntentFlag;

    private BaseRecyclerViewAdapter mAdapter;

    private ActivityProjectListBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_project_list);
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        mBinding.include.toolbar.setTitle(R.string.activity_project_list);
        setSupportActionBar(mBinding.include.toolbar);
        mBinding.setPullListener(this);
        mBinding.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseRecyclerViewAdapter(R.layout.item_project_list, new ArrayList<DUProject>(), BR.project);
        mAdapter.setPresenter(mPresenter, BR.presenter);
        mBinding.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //过滤搜索结果
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
        searchView.setQueryHint(getString(R.string.text_menu_search_hint_project_number));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onLoadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onItemClick(DUProject project) {
        Intent intent = new Intent();
        intent.putExtra(Constants.INTENT_PARAM_PROJECT, project);
        switch (mIntentFlag){
            case Constants.FLAG_STOP_PROJECT:
                intent.setClass(this, StopProjectActivity.class);
                break;
            case Constants.FLAG_RESTART_PROJECT:
                intent.setClass(this, RestartProjectActivity.class);
                break;
            case Constants.FLAG_DAILY_PATROL:
                intent.setClass(this, PatrolActivity.class);
                intent.putExtra(Constants.INTENT_PARAM_FLAG, mIntentFlag);
                break;
            case Constants.FLAG_INDEMNITYCENTER_PATROL:
                intent.setClass(this, PatrolActivity.class);
                intent.putExtra(Constants.INTENT_PARAM_FLAG, mIntentFlag);
                break;
            case Constants.FLAG_ACCEPT_PROJECT:
                intent.setClass(this, BudgetAcceptListActivity.class);
                break;
            case Constants.FLAG_SEARCH_PROJECT:
                intent.setClass(this, SearchPatrolActivity.class);
                intent.putExtra(Constants.INTENT_PARAM_PROJECT_ID, project.getProjectId());
                break;
        }
        startActivityForResult(intent, 0);
    }

    @Override
    public boolean onVisible() {
        //如果是日常巡视工程列表不显示施工队
        if(Constants.FLAG_DAILY_PATROL.equals(mIntentFlag)){
            return false;
        }
        return true;
    }

    @Override
    public void onToast(int resId) {
        ApplicationsUtil.showMessage(this, resId);
    }

    @Override
    public void onError(int action, int resId) {
        onToast(resId);
        onLoadFinish(action);
    }

    @Override
    public void onNotify(int action, List<DUProject> projects) {
        if(action == ACTION_LOAD || action == ACTION_REFRESH){
            mAdapter.setDatas(projects);
            return;
        }
        mAdapter.addDatas(projects);
    }

    @Override
    public void onLoadFinish(int action) {
        switch (action){
            case ACTION_LOAD:
                hideProgress();
                break;
            case ACTION_REFRESH:
                mBinding.pullLayout.setRefreshing(false);
                break;
            case ACTION_LOADMORE:
                mBinding.pullLayout.setLoading(false);
                break;
        }
    }

    @Override
    public void onLoadData() {
        showProgress(R.string.text_loading);
        loadProjects(ACTION_LOAD, NetworkUtil.isNetworkConnected(this), INDEX, mAdapter.getItemCount() / OFFSET == 0 ?
                OFFSET : (int) Math.ceil(mAdapter.getItemCount() / OFFSET) * OFFSET);
    }

    @Override
    public void onRefresh() {
        //do refresh data
        loadProjects(ACTION_REFRESH, NetworkUtil.isNetworkConnected(this), INDEX, mAdapter.getItemCount() / OFFSET == 0 ?
                OFFSET : (int) Math.ceil(mAdapter.getItemCount() / OFFSET) * OFFSET);
    }

    @Override
    public void onLoadMore() {
        //do load more data
        if(mAdapter.getItemCount() % OFFSET != 0){
            mBinding.pullLayout.setLoading(false);
            onToast(R.string.text_toast_no_more_data);
            mAdapter.notifyDataSetChanged();
            return;
        }
        loadProjects(ACTION_LOADMORE, NetworkUtil.isNetworkConnected(this), mAdapter.getItemCount() + 1, OFFSET);
    }

    /**
     * 加载工程信息
     * @param action 1加载 2刷新 3加载更多
     * @param isNetworkConnected 网络状态
     * @param index 起始位置
     * @param offset 偏移量
     */
    private void loadProjects(int action, boolean isNetworkConnected, int index, int offset){
        List<Integer> projectStates = new ArrayList<>();
        switch (mIntentFlag){
            case Constants.FLAG_STOP_PROJECT:
                projectStates.add(DUProject.State.START.getValue());
                projectStates.add(DUProject.State.RESTART.getValue());
                break;
            case Constants.FLAG_RESTART_PROJECT:
                projectStates.add(DUProject.State.STOP.getValue());
                break;
            case Constants.FLAG_INDEMNITYCENTER_PATROL://保中巡视
                projectStates.add(DUProject.State.STOP.getValue());
            case Constants.FLAG_DAILY_PATROL://日常巡视
            case Constants.FLAG_ACCEPT_PROJECT://工程验收
                projectStates.add(DUProject.State.START.getValue());
                projectStates.add(DUProject.State.RESTART.getValue());
                break;
            case Constants.FLAG_SEARCH_PROJECT://工程查询
                projectStates.add(DUProject.State.START.getValue());
                projectStates.add(DUProject.State.STOP.getValue());
                projectStates.add(DUProject.State.RESTART.getValue());
                projectStates.add(DUProject.State.FINISH.getValue());
                break;
        }
        if(isNetworkConnected){
            mPresenter.loadProjects(action, mPreferencesHelper.getUserSession().getUserId(), projectStates, index, offset);
            if(!projectStates.contains(DUProject.State.FINISH.getValue())){
                //下载已完工的工程，解决多台设备登录同一账号导致工程不同步的问题
                mPresenter.loadProjects(mPreferencesHelper.getUserSession().getUserId(), DUProject.State.FINISH.getValue());
            }
        }else {//本地数据库起始位置为0
            mPresenter.loadProjects(action, mPreferencesHelper.getUserSession().getConstructionTeam(), projectStates, index - 1, offset);
        }
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
        Intent intent = getIntent();
        mIntentFlag = intent.getStringExtra(Constants.INTENT_PARAM_FLAG);
        //对比主程序uri与子程序uri 避免主程序和子程序访问不同服务器数据
        mPresenter.compareUri(intent.getStringExtra(Constants.INTENT_PARAM_BASEURI));
    }
}
