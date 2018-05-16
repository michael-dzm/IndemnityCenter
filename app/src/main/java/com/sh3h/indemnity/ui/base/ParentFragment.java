package com.sh3h.indemnity.ui.base;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sh3h.indemnity.R;
import com.sh3h.indemnity.dialog.LoadDialog;
import com.sh3h.indemnity.service.SyncService;
import com.sh3h.indemnity.service.action.SyncAction;
import com.sh3h.indemnity.util.Constants;

import java.text.NumberFormat;
import java.util.Calendar;

public abstract class ParentFragment extends Fragment {

    private LoadDialog mProgressDialog;

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;

    public NumberFormat _f = NumberFormat.getInstance();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {


    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    public DatePickerDialog createDatePickerDialog(DatePickerDialog.OnDateSetListener listener, long minDate){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.style_date_picker_dialog, listener, year, month, day);
        if(minDate <= 0){
            dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        }else{
            dialog.getDatePicker().setMinDate(minDate);
        }
        dialog.setTitle(R.string.text_dialog_title_select_date);
       return dialog;
    }

    public void startSyncService(int flag){
        Intent intent = new Intent(getContext(), SyncService.class);
        //set param
        intent.putExtra(Constants.INTENT_PARAM_FLAG, flag);
        getContext().startService(intent);
    }

    public void startSyncService(int flag, int type){
        Intent intent = new Intent(getContext(), SyncService.class);
        //set param
        intent.putExtra(Constants.INTENT_PARAM_FLAG, flag);
        intent.putExtra(Constants.INTENT_PARAM_PATROL_TYPE, type);
        getContext().startService(intent);
    }

    public void showProgress(int resId) {
        if(mProgressDialog == null ){
            mProgressDialog = new LoadDialog(getActivity(), R.style.progressDialog);
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

}
