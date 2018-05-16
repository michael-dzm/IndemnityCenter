package com.sh3h.indemnity.util;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class SystemUtil {
    public static int getVersionCode(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.versionCode;
        } else {
            return 0;
        }
    }

    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.versionName;
        } else {
            return "";
        }
    }

    public static PackageInfo getPackageInfo(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int dp2px(Context context, float dp) {
        int dip = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (dp * (dip / 160));
    }

    public static int px2dp(Context context, float px) {
        int dip = context.getResources().getDisplayMetrics().densityDpi;
        return (int) ((px * 160) / dip);
    }
}
