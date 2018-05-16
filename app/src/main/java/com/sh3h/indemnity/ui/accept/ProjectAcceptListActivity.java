package com.sh3h.indemnity.ui.accept;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sh3h.dataprovider.data.entity.DUOperate;
import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.dataprovider.data.entity.enums.Upload;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.BR;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivityProjectAcceptListBinding;
import com.sh3h.indemnity.event.BusEvent;
import com.sh3h.indemnity.service.action.SyncAction;
import com.sh3h.indemnity.service.manager.IMainServiceManager;
import com.sh3h.indemnity.ui.accept.budget.BudgetAcceptListActivity;
import com.sh3h.indemnity.ui.adapter.BaseRecyclerViewAdapter;
import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.ipc.location.MyLocation;
import com.sh3h.ipc.module.MyModule;
import com.sh3h.loadmore.listener.OnPullListener;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Created by xulongjun on 2017/3/10.
 */

public class ProjectAcceptListActivity extends ParentActivity implements ProjectAcceptListMvpView, OnPullListener{
    public static final int INDEX = 1;//起始位置
    public static final int OFFSET = 40;//偏移量

    public static final int ACTION_LOAD = 0x00001;
    public static final int ACTION_REFRESH = 0x00002;
    public static final int ACTION_LOADMORE = 0x00003;

    @Inject
    ProjectAcceptListPresenter mProjectAcceptListPresenter;

    @Inject
    Bus mBus;

    @Inject
    PreferencesHelper mPreferencesHelper;

    private ActivityProjectAcceptListBinding mBinding;

    private BaseRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_project_accept_list);
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        mBinding.include.toolbar.setTitle(R.string.activity_project_list);
        setSupportActionBar(mBinding.include.toolbar);
        getActivityComponent().inject(this);
        mProjectAcceptListPresenter.attachView(this);
        mBus.register(this);
        mBinding.setPullListener(this);
        mBinding.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseRecyclerViewAdapter(R.layout.item_project_accept_list, new ArrayList(), BR.duProject);
        mAdapter.setPresenter(mProjectAcceptListPresenter, BR.presenter);
        mBinding.setAdapter(mAdapter);
    }

    @Override
    public void onIntent(DUProject duProject) {
        Intent intent = new Intent(this, BudgetAcceptListActivity.class);
        intent.putExtra(Constants.INTENT_PARAM_PROJECT, duProject);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onLoadData();
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
    public void onError(int action, int resId) {
        onToast(resId);
        onLoadFinish(action);
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
    public void onNotify(int action, List<DUProject> duProjects) {
        if (duProjects!=null){
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(MyModule.PACKAGE_NAME, getApplicationContext().getPackageName());
                jsonObject.put(MyModule.ACTIVITY_NAME, ProjectAcceptListActivity.class.getName());
                JSONArray subJSONArray = new JSONArray();
                subJSONArray.put("count#" + duProjects.size());
                jsonObject.put(MyModule.DATA, subJSONArray);
                MyModule myModule = new MyModule(jsonObject.toString());
                IMainServiceManager.getInstance(this).getService().setMyModule(myModule);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(action == ACTION_LOAD || action == ACTION_REFRESH){
            mAdapter.setDatas(duProjects);
            return;
        }
        mAdapter.addDatas(duProjects);
    }

    @Override
    public void onToast(int resId) {
        ApplicationsUtil.showMessage(this, resId);
    }

    @Override
    public void onSuccess(int resId) {
        onToast(resId);
        onLoadData();
    }

    @Override
    public void onUploadOperates() {
        startSyncService(SyncAction.UPLOAD_ALL_OPERATE.ordinal());
    }

    @Override
    public void onFinishProject(DUProject duProject) {
        MyLocation location = IMainServiceManager.getInstance(this).getLocation();
        duProject.setState(DUProject.State.FINISH.getValue());
        duProject.setEndTime(System.currentTimeMillis());
        DUOperate duOperate = new DUOperate();
        duOperate.setProjectId(duProject.getProjectId());
        duOperate.setOperateType(DUOperate.Type.FINISH.getValue());
        duOperate.setEndTime(System.currentTimeMillis());
        duOperate.setOperator(mPreferencesHelper.getUserSession().getUserName());
        duOperate.setOperateTime(System.currentTimeMillis());
        duOperate.setLongitude(location == null ? 0 : location.getLongitude());
        duOperate.setLatitude(location == null ? 0 : location.getLatitude());
        duOperate.setUpload(Upload.DEFAULT.getValue());
        mProjectAcceptListPresenter.saveOperate(duProject, duOperate, null, NetworkUtil.isNetworkConnected(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProjectAcceptListPresenter.detachView();
        mBus.unregister(this);
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

    @Subscribe
    public void onUploadOperateResult(BusEvent.UploadOperate event) {
        if (event.isSuccess()) {
            onSuccess(R.string.text_toast_finish_project_upload_success);
            onLoadData();
        } else {
            onToast(R.string.text_toast_finish_project_upload_failed);
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
        if (userId == 0) {
            onExit(R.string.text_toast_host_app_user_info_error);
            return;
        }
        mProjectAcceptListPresenter.loadWords();
        if (mPreferencesHelper.getUserSession().getUserId() != userId) {
            if (!NetworkUtil.isNetworkConnected(this)) finish();
            mProjectAcceptListPresenter.getUserInfo(userId);
        } else {
            onLoadData();
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        //对比主程序uri与子程序uri 避免主程序和子程序访问不同服务器数据
        mProjectAcceptListPresenter.compareUri(getIntent().getStringExtra(Constants.INTENT_PARAM_BASEURI));
    }

    private void loadProjects(int action, boolean isNetworkConnected, int index, int offset) {
        List<Integer> projectStates = new ArrayList<>();
        projectStates.add(DUProject.State.START.getValue());
        projectStates.add(DUProject.State.RESTART.getValue());
        if (isNetworkConnected) {
            mProjectAcceptListPresenter.loadProjects(action, mPreferencesHelper.getUserSession().getUserId(), projectStates, index, offset);
            if(!projectStates.contains(DUProject.State.FINISH.getValue())){
                //下载已完工的工程，解决多台设备登录同一账号导致工程不同步的问题
                mProjectAcceptListPresenter.loadProjects(mPreferencesHelper.getUserSession().getUserId(), DUProject.State.FINISH.getValue());
            }
        } else {//本地数据库起始位置为0
            mProjectAcceptListPresenter.loadProjects(action, mPreferencesHelper.getUserSession().getConstructionTeam(), projectStates, index - 1, offset);
        }
    }
}
