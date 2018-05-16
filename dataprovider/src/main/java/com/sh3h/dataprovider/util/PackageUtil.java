package com.sh3h.dataprovider.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by Administrator on 2016/6/19.
 */
public class PackageUtil {
    public static PackageInfo getPackageInfo(Context context, String archiveFilePath) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<PackageInfo> getInstalledPackages(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            return pm.getInstalledPackages(0); //PackageManager.GET_PERMISSIONS
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
}
