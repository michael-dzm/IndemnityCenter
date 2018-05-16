package com.sh3h.indemnity.ui.base;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sh3h.indemnity.ActivityManager;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.service.SyncService;
import com.sh3h.indemnity.service.action.SyncAction;
import com.sh3h.indemnity.ui.upload.UploadManagerActivity;
import com.sh3h.indemnity.util.Constants;

import java.util.Calendar;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

public class ParentActivity extends BaseActivity implements SwipeBackActivityBase {

    public static final String TAG = ParentActivity.class.getSimpleName();
    private static final String RESTORING_STATE = "restoringState";

    private SwipeBackActivityHelper mHelper;

    public ParentActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBothAnimation();
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();

        if (savedInstanceState != null) {
            boolean restoringState = savedInstanceState.getBoolean(RESTORING_STATE, false);
        }
        ActivityManager.getInstance().add(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
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
        ActivityManager.getInstance().remove(this);
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if ((v == null) && (mHelper != null)) {
            return mHelper.findViewById(id);
        }
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    protected void vibrate(long duration) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {
                0, duration
        };
        vibrator.vibrate(pattern, -1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(RESTORING_STATE, true);
    }

    public DatePickerDialog createDatePickerDialog(DatePickerDialog.OnDateSetListener listener, long minDate){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.style_date_picker_dialog, listener, year, month, day);
        if(minDate <= 0){
            dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        }else{
            dialog.getDatePicker().setMinDate(minDate);
        }
        dialog.setTitle(R.string.text_dialog_title_select_date);
        return dialog;
    }

    public DatePickerDialog createDatePickerDialog(DatePickerDialog.OnDateSetListener listener){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.style_date_picker_dialog, listener, year, month, day);
        dialog.setTitle(R.string.text_dialog_title_select_date);
        return dialog;
    }

    public void startSyncService(int flag){
        Intent intent = new Intent(this, SyncService.class);
        //set param
        intent.putExtra(Constants.INTENT_PARAM_FLAG, flag);
        startService(intent);
    }

    public void startSyncService(int flag, int type){
        Intent intent = new Intent(this, SyncService.class);
        //set param
        intent.putExtra(Constants.INTENT_PARAM_FLAG, flag);
        intent.putExtra(Constants.INTENT_PARAM_PATROL_TYPE, type);
        startService(intent);
    }

    public void showUploadDialog(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.text_dialog_title_prompt)
                .setMessage(R.string.text_dialog_content_check_upload_local_data)
                .setPositiveButton(R.string.text_dialog_button_upload, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), UploadManagerActivity.class);
                        intent.putExtra(Constants.INTENT_PARAM_FROM, TAG);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.text_dialog_button_cancel, null)
                .show();
    }

}
