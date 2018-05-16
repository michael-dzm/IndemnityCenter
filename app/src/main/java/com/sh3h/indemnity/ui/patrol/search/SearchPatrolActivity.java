package com.sh3h.indemnity.ui.patrol.search;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;

import com.android.databinding.library.baseAdapters.BR;
import com.sh3h.dataprovider.data.entity.DUPatrolSearchResult;
import com.sh3h.dataprovider.data.entity.DUViolation;
import com.sh3h.dataprovider.data.entity.DUWord;
import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivitySearchProjectBinding;
import com.sh3h.indemnity.ui.adapter.BaseRecyclerViewAdapter;
import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.indemnity.ui.multimedia.brower.MultimediaBrowerActivity;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.indemnity.util.DateUtils;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dengzhimin on 2017/3/7.
 */

public class SearchPatrolActivity extends ParentActivity implements SearchPatrolMvpView {

    @Inject
    SearchPatrolPresenter mPresenter;

    private ActivitySearchProjectBinding mBinding;

    private DatePickerDialog mStartDatePickerDialog;
    private DatePickerDialog mEndDatePickerDialog;

    private long mStartDate;
    private long mEndDate;
    private String mConstructionType;
    private String mConstructionContent;
    private String mConstructionResult;

    private List<DUViolation> mViolations;
    private List<DUWord> mWords;

    private long mProjectId;

    private BaseRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_project);
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        mBinding.include.toolbar.setTitle(R.string.activity_project_patrol_search);
        setSupportActionBar( mBinding.include.toolbar);
        mBinding.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseRecyclerViewAdapter(R.layout.item_project_patrol, new ArrayList<DUPatrolSearchResult>(), BR.patrolResult);
        mAdapter.setPresenter(mPresenter, BR.presenter);
        mBinding.setAdapter(mAdapter);
        mBinding.setPresenter(mPresenter);
        //加载违规配置信息
        if(NetworkUtil.isNetworkConnected(this)){
            mPresenter.loadViolations();//在线加载
        }else {
            onToast(R.string.text_toast_not_network);
            mPresenter.getAllViolationTypes();//本地加载
        }
        mProjectId = getIntent().getLongExtra(Constants.INTENT_PARAM_PROJECT_ID, 0L);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.sp_construction_type:
                mConstructionType = position == 0 ? "all" : (String) parent.getSelectedItem();
                mPresenter.getViolationByType((String) parent.getSelectedItem());
                if(position == 0){//施工类型为全部时 施工内容和施工结果默认全部
                    mBinding.spConstructionContent.setSelection(position);
                    mBinding.spConstructionResult.setSelection(position);
                }
                break;
            case R.id.sp_construction_content:
                mConstructionContent = position == 0 ? "all" : String.valueOf(mViolations.get(position - 1).getViolationNumber());
                break;
            case R.id.sp_construction_result:
                mConstructionResult = position == 0 ? "all" : mWords.get(position - 1).getValue();
                break;
        }

    }

    @Override
    public void onItemClick(DUPatrolSearchResult patrol) {
        if(TextUtil.isNullOrEmpty(patrol.getImageUrl())){
            onToast(R.string.text_toast_no_has_photo);
            return;
        }
        List<String> urls = new ArrayList<>();
        urls.add(patrol.getImageUrl());
        Intent intent = new Intent(this, MultimediaBrowerActivity.class);
        intent.putStringArrayListExtra(Constants.INTENT_PARAM_MULTIMEDIA_URLS, (ArrayList<String>) urls);
        startActivity(intent);
    }

    @Override
    public void onCalendarView(int viewId) {
        switch (viewId){
            case R.id.btn_start_date://起始日期
                if(mStartDatePickerDialog == null){
                    mStartDatePickerDialog = createDatePickerDialog(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            mStartDate = DateUtils.parse(year, month + 1, dayOfMonth, DateUtils.FORMAT_DATE).getTime();
                            mBinding.setStartDate(mStartDate);
                            if(mEndDatePickerDialog != null){
                                mEndDatePickerDialog.getDatePicker().setMinDate(mStartDate);
                            }
                        }
                    });
                }
                mStartDatePickerDialog.show();
                break;
            case R.id.btn_end_date://截止日期
                if(mEndDatePickerDialog == null){
                    mEndDatePickerDialog = createDatePickerDialog(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            mEndDate = DateUtils.parse(year, month + 1, dayOfMonth, DateUtils.FORMAT_DATE).getTime();
                            mBinding.setEndDate(mEndDate);
                        }
                    }, mStartDate == 0 ? System.currentTimeMillis() : mStartDate);
                }
                mEndDatePickerDialog.show();
                break;
        }

    }

    @Override
    public void onSearch() {
        if(mStartDate == 0 || mEndDate == 0 ){
            onToast(R.string.text_toast_please_select_data);
            return;
        }
        //do search
        mBinding.swipeRefreshLayout.setRefreshing(true);
        mPresenter.searchPatrols(mProjectId, mStartDate, mEndDate, mConstructionType, mConstructionContent, mConstructionResult);
    }

    @Override
    public void onToast(int resId) {
        ApplicationsUtil.showMessage(this, resId);
    }

    @Override
    public void onError(int resId) {
        onToast(resId);
        mBinding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onNotifyTypeSpinner(List<DUViolation> violations) {
        if(violations != null && violations.size() > 0){
            List<String> violationTypes = new ArrayList<String>();
            violationTypes.add("全部");
            for(DUViolation duViolation : violations){
                violationTypes.add(duViolation.getViolationType());
            }
            mBinding.setConstructionTypes(violationTypes);
        }
    }

    @Override
    public void onNotifyContentSpinner(List<DUViolation> violations) {
        List<String> constructionContents = new ArrayList<>();
        constructionContents.add("全部");
        if(violations != null && violations.size() > 0){
            mViolations = violations;
            for(DUViolation duViolation : violations){
                constructionContents.add(duViolation.getViolationContent());
            }
        }
        mBinding.setConstructionContents(constructionContents);
        mBinding.spConstructionContent.setSelection(0);
    }

    @Override
    public void onNotifyResultSpinner(List<DUWord> words) {
        if(words != null && words.size() > 0){
            mWords = words;
            List<String> constructionResults = new ArrayList<>();
            constructionResults.add("全部");
            for(DUWord duWord : words){
                constructionResults.add(duWord.getName());
            }
            mBinding.setConstructionResults(constructionResults);
        }
    }

    @Override
    public void noNotifyDataChanged(List<DUPatrolSearchResult> datas) {
        mBinding.swipeRefreshLayout.setRefreshing(false);
        mAdapter.setDatas(datas);
    }

}
