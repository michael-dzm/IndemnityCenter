package com.sh3h.indemnity.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sh3h.indemnity.R;

/**
 * Created by dengzhimin on 2017/8/29.
 */

public class LoadDialog extends Dialog {

    private ProgressBar progressBar;

    private TextView textView;

    public LoadDialog(@NonNull Context context) {
        this(context, 0);
    }

    public LoadDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_load);
        progressBar = (ProgressBar) findViewById(R.id.pb_dialog);
        textView = (TextView) findViewById(R.id.tv_dialog_content);
        //android5.0以上不使用clip 否则加载不出图片
        if (android.os.Build.VERSION.SDK_INT > 22) {
            progressBar.setIndeterminateDrawable(context.getDrawable(R.drawable.anim_load_22));
        }
    }

    protected LoadDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    public synchronized void setMessage(int resId){
        textView.setText(resId);
    }

    public synchronized void setMessage(String message){
        textView.setText(message);
    }
}
