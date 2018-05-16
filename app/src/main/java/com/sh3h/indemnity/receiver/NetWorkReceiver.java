package com.sh3h.indemnity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;

import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.ActivityManager;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.service.action.SyncAction;
import com.sh3h.mobileutil.util.ApplicationsUtil;

/**
 * Created by dengzhimin on 2017/4/27.
 */

public class NetWorkReceiver extends BroadcastReceiver {

    public boolean mIsConnect;//当前网络连接状态

    @Override
    public void onReceive(Context context, Intent intent) {
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            boolean isConnect = NetworkUtil.isNetworkConnected(context);
            if(mIsConnect != isConnect){//网络连接状态发生改变时
                mIsConnect = isConnect;
                if(!isConnect){//网络断开连接
                    ApplicationsUtil.showMessage(context, R.string.text_toast_network_disconnected);
                    return;
                }
                //网络重新连接 检测本地数据库是否有未上传的数据
                ActivityManager.getInstance().postUpload();
            }
        }
    }
}
