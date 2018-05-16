package com.sh3h.dataprovider.data.remote;


import android.os.Handler;
import android.os.Looper;

import com.sh3h.mobileutil.util.LogUtil;
import com.squareup.otto.Bus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;


public class UnauthorisedInterceptor implements Interceptor {

    public static final String TAG = UnauthorisedInterceptor.class.getSimpleName();

    public UnauthorisedInterceptor() {}

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        try {
            response = chain.proceed(chain.request());
            if (response != null && response.code() == 401) {
                LogUtil.i(TAG, "response code is 401");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
