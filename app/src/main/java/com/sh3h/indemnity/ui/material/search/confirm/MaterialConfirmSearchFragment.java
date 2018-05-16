package com.sh3h.indemnity.ui.material.search.confirm;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.sh3h.dataprovider.data.entity.DUMaterialVerify;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.BR;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.FragmentMaterialConfirmSearchBinding;
import com.sh3h.indemnity.ui.adapter.BaseRecyclerViewAdapter;
import com.sh3h.indemnity.ui.base.ParentFragment;
import com.sh3h.indemnity.ui.material.search.MaterialSearchActivity;
import com.sh3h.indemnity.util.DateUtils;
import com.sh3h.mobileutil.util.ApplicationsUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by xulongjun on 2017/4/6.
 * 材料确认单查询
 */
public class MaterialConfirmSearchFragment extends ParentFragment implements MaterialConfirmSearchMvpView {
    public static final String TAG = "MaterialConfirmSearchFragment";

    @Inject
    MaterialConfirmSearchPresenter mMaterialConfirmSearchPresenter;

    @Inject
    PreferencesHelper mPreferencesHelper;

    private FragmentMaterialConfirmSearchBinding mBinding;

    private BaseRecyclerViewAdapter mAdapter;

    private DatePickerDialog mStartDatePickerDialog;
    private DatePickerDialog mEndDatePickerDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_material_confirm_search, container, false);
        ((MaterialSearchActivity) getActivity()).getActivityComponent().inject(this);
        mMaterialConfirmSearchPresenter.attachView(this);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.setPresenter(mMaterialConfirmSearchPresenter);
        mBinding.swipeRefreshLayout.setEnabled(false);
        mAdapter = new BaseRecyclerViewAdapter(R.layout.item_material_confirm_list, new ArrayList<>(), BR.duMaterialVerify);
        mAdapter.setPresenter(mMaterialConfirmSearchPresenter, BR.presenter);
        mBinding.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMaterialConfirmSearchPresenter.detachView();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onError(String message) {
        mBinding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCalendarView(int viewId) {
        switch (viewId) {
            case R.id.btn_start_project_date://起始日期
                if (mStartDatePickerDialog == null) {
                    mStartDatePickerDialog = createDatePickerDialog(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            mMaterialConfirmSearchPresenter.mStartTime.set(DateUtils.parse(year, month + 1,
                                    dayOfMonth, DateUtils.FORMAT_DATE).getTime());
                        }
                    }, 1);
                }
                mStartDatePickerDialog.show();
                break;
            case R.id.btn_end_project_date://ͣ截止日期
                if (mEndDatePickerDialog == null) {
                    mEndDatePickerDialog = createDatePickerDialog(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            mMaterialConfirmSearchPresenter.mEndTime.set(DateUtils.parse(year, month + 1,
                                    dayOfMonth, DateUtils.FORMAT_DATE).getTime());
                        }
                    }, 1);
                }
                mEndDatePickerDialog.show();
                break;
        }
    }

    @Override
    public void onSearch() {
        if (!NetworkUtil.isNetworkConnected(getContext())) {
            onToast(R.string.text_toast_not_network);
            return;
        }
        if (mMaterialConfirmSearchPresenter.mStartTime.get() == 0
                || mMaterialConfirmSearchPresenter.mEndTime.get() == 0) {
            onToast(R.string.text_toast_date_null);
            return;
        }
        if (mMaterialConfirmSearchPresenter.mStartTime.get()
                > mMaterialConfirmSearchPresenter.mEndTime.get()) {
            onToast(R.string.text_toast_date_end_gt_start);
            return;
        }
        mBinding.swipeRefreshLayout.setRefreshing(true);
        mMaterialConfirmSearchPresenter.searchMaterialConfirm(mPreferencesHelper.getUserSession().getUserId(),
                mMaterialConfirmSearchPresenter.mStartTime.get(), mMaterialConfirmSearchPresenter.mEndTime.get());
    }

    @Override
    public void onToast(int resId) {
        ApplicationsUtil.showMessage(getContext(), resId);
    }

    @Override
    public void onSearchMaterialVerify(List<DUMaterialVerify> duMaterialVerifies) {
        mBinding.swipeRefreshLayout.setRefreshing(false);
        if (duMaterialVerifies == null) {
            onToast(R.string.text_toast_data_null);
            return;
        }
        mAdapter.setDatas(duMaterialVerifies);

    }

}
