package com.sh3h.indemnity;

import android.app.Activity;

import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.indemnity.ui.upload.UploadManagerActivity;
import com.sh3h.mobileutil.util.LogUtil;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by dengzhimin on 2017/5/2.
 */

public class ActivityManager {

    public static final String TAG = ActivityManager.class.getSimpleName();

    private static ActivityManager mManager;

    private LinkedList<WeakReference<Activity>> mActivityReference;

    private ActivityManager(){
        mActivityReference = new LinkedList<>();
    }

    public static ActivityManager getInstance(){
        if(mManager == null){
            synchronized (ActivityManager.class){
                if (mManager == null){
                    mManager = new ActivityManager();
                }
            }
        }
        return mManager;
    }

    public void add(Activity activity){
        if(mActivityReference == null){
            throw new NullPointerException("mActivityReference is null");
        }
        mActivityReference.add(new WeakReference<>(activity));
    }

    public void remove(Activity activity){
        if(mActivityReference == null || mActivityReference.size() == 0){
            return;
        }
        for(Iterator<WeakReference<Activity> > it = mActivityReference.iterator(); it.hasNext();){
            WeakReference<Activity> activityReference = it.next();
            if (activityReference.get() == activity) it.remove();
        }
    }

    public void postUpload(){
        if(mActivityReference == null || mActivityReference.size() == 0){
            return;
        }
        Activity activity = mActivityReference.getLast().get();
        if(activity instanceof UploadManagerActivity) return;
        try{
            ((ParentActivity)activity).showUploadDialog();
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.i(TAG, "ActivityManager postUpload() is error");
        }
    }

}
