package com.sh3h.dataprovider.data.remote;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.sh3h.dataprovider.data.entity.DUFile;
import com.sh3h.dataprovider.data.entity.DUFileResult;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.exception.DUException;
import com.sh3h.dataprovider.injection.annotation.ApplicationContext;
import com.sh3h.dataprovider.util.PackageUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

@Singleton
public class Downloader {
    private static final int TIMEOUT = 10 * 1000;// 超时
    private final Context mContext;
    private final ConfigHelper mConfigHelper;
    private int newApkVersion = 0;
    private int newDataVersion = 0;

    @Inject
    public Downloader(@ApplicationContext Context context,
                      ConfigHelper configHelper) {
        mContext = context;
        mConfigHelper = configHelper;
    }

    /**
     * download a file
     * @param duFile
     * @return
     */
    public Observable<DUFileResult> downloadFile(final DUFile duFile) {
        return Observable.create(new Observable.OnSubscribe<DUFileResult>() {
            @Override
            public void call(Subscriber<? super DUFileResult> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                if (TextUtil.isNullOrEmpty(duFile.getUrl())) {
                    throw new NullPointerException(DUException.PARAM_NULL.getName());
                }

                try {
                    if (duFile.getFileType() == DUFile.FileType.APK) {
                        if (checkLocalApk(duFile)) {
                            subscriber.onNext(new DUFileResult(duFile, 100));
                            subscriber.onCompleted();
                            return;
                        }
                    }

                    downloadFile(duFile, subscriber);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                }
            }
        });
    }

    /**
     * check whether the apk exists in local folder
     * @param duFile
     * @return
     */
    private boolean checkLocalApk(DUFile duFile) {
        String url = duFile.getUrl();
        int start = url.lastIndexOf("/") + 1;
        int end = url.lastIndexOf("_Apk_");
        if ((start == -1) || (start >= end)) {
            return false;
        }

        String name = url.substring(start, end);
        if (TextUtil.isNullOrEmpty(name)) {
            return false;
        }

        File file = new File(mConfigHelper.getApksFolderPath(), name + ".apk");
        if (!file.exists()) {
            return false;
        }

        PackageInfo packageInfo = PackageUtil.getPackageInfo(mContext, file.getPath());
        if (packageInfo == null) {
            return false;
        }

        boolean b = (duFile.getVersionCode() == packageInfo.versionCode);
        if (b) {
            duFile.setPath(file.getPath());
            duFile.setExistingLocal(true);
        }

        return b;
    }

    /**
     * download a file
     * @param duFile
     * @param subscriber
     * @throws Exception
     */
    private void downloadFile(DUFile duFile, Subscriber<? super DUFileResult> subscriber)
            throws Exception {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(duFile.getUrl()).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            long totalLength = response.body().contentLength();
            int index = duFile.getUrl().lastIndexOf("/");
            if (index <= 0) {
                return;
            }

            String name = duFile.getUrl().substring(index + 1);
            File updateFolderPath = mConfigHelper.getUpdateFolderPathByUser();
            if (!updateFolderPath.exists()) {
                updateFolderPath.mkdirs();
            }
            File file = new File(updateFolderPath, name);
            String strPath = file.getPath();
            duFile.setPath(strPath);
            fos = new FileOutputStream(file);
            byte[] buf = new byte[2048];
            int length = 0;
            int curLength = 0;
            int percent = 0;
            int lastPercent = 0;
            int tmpPercent = 0;
            while ((length = is.read(buf)) != -1) {
                fos.write(buf, 0, length);
                curLength += length;
                percent = (int) (curLength * 100.0 / totalLength);
                if (percent <= lastPercent) {
                    continue;
                }

                lastPercent = percent;
                if (tmpPercent + 20 <= percent) {
                    tmpPercent = percent;
                    subscriber.onNext(new DUFileResult(duFile, percent));
                }
            }

            if (tmpPercent != 100) {
                subscriber.onNext(new DUFileResult(duFile, 100));
            }

            fos.flush();
            subscriber.onCompleted();
        } catch (IOException e) {
            subscriber.onError(e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }

                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
