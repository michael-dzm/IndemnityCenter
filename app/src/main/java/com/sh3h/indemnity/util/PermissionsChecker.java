package com.sh3h.indemnity.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.sh3h.dataprovider.injection.annotation.ApplicationContext;

import javax.inject.Inject;


public class PermissionsChecker {


    private final Context mContext;

    @Inject
    public PermissionsChecker(@ApplicationContext Context mContext) {
        this.mContext = mContext;
    }

    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermissions(permission)) {
                return true;
            }
        }
        return false;
    }

    //判断是否缺少权限
    public boolean lacksPermissions(String permission) {
        return ContextCompat.checkSelfPermission(mContext,
                permission) == PackageManager.PERMISSION_DENIED;
    }
}
