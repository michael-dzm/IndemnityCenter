package com.sh3h.dataprovider.data.local.xml;

import android.content.Context;
import android.content.pm.PackageInfo;

import com.sh3h.dataprovider.data.entity.DUConfigXmlFile;
import com.sh3h.dataprovider.data.entity.DUUpdateXmlFile;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.exception.DUException;
import com.sh3h.dataprovider.injection.annotation.ApplicationContext;
import com.sh3h.dataprovider.util.PackageUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/6/21.
 */

@Singleton
public class XmlHelper {
    private final Context mContext;
    private ConfigXml mConfigXml;
    private UpdateXml mUpdateXml;

    @Inject
    public XmlHelper(@ApplicationContext Context context, ConfigHelper configHelper) {
        mContext = context;
        mConfigXml = new ConfigXml(configHelper);
        mUpdateXml = new UpdateXml(configHelper);
    }

    /**
     *
     * @return
     */
    public synchronized DUConfigXmlFile getDuConfigXmlFile() {
        return mConfigXml.getDuConfigXmlFile();
    }

    /**
     *
     * @param duConfigXmlFile
     */
    public synchronized void setDuConfigXmlFile(DUConfigXmlFile duConfigXmlFile) {
        mConfigXml.setDuConfigXmlFile(duConfigXmlFile);
    }

    /**
     *
     * @return
     */
    public synchronized boolean saveDuConfigXmlFile() {
        return mConfigXml.write();
    }

    public synchronized DUUpdateXmlFile getDuUpdateXmlFile() {
        return mUpdateXml.getDuUpdateXmlFile();
    }

    /**
     * only used for parse module
     * @return
     */
    public synchronized DUUpdateXmlFile getDuUpdateXmlFileClone() {
        DUUpdateXmlFile duUpdateXmlFile = getDuUpdateXmlFile();
        DUUpdateXmlFile duUpdateXmlFileClone = null;
        if (duUpdateXmlFile != null) {
            try {
                duUpdateXmlFileClone = duUpdateXmlFile.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                duUpdateXmlFileClone = null;
            }
        }

        return duUpdateXmlFileClone;
    }

    /**
     *
     * @param duUpdateXmlFile
     */
    public synchronized void setDuUpdateXmlFile(DUUpdateXmlFile duUpdateXmlFile) {
        mUpdateXml.setDuUpdateXmlFile(duUpdateXmlFile);
    }

    /**
     *
     * @return
     */
    public synchronized boolean saveDuUpdateXmlFile() {
        return mUpdateXml.write();
    }

//    public Observable<Boolean> loadXmls() {
//        return Observable.mergeDelayError(loadConfigXml(), loadUpdateXml());
//    }
//
//    private Observable<Boolean> loadConfigXml() {
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                if (subscriber.isUnsubscribed()) {
//                    return;
//                }
//
//                DUConfigXmlFile duConfigXmlFile = getDuConfigXmlFile();
//                if (duConfigXmlFile == null) {
//                    subscriber.onError(new Throwable("the returned value is null"));
//                    return;
//                }
//                subscriber.onNext(true);
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    private Observable<Boolean> loadUpdateXml() {
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                if (subscriber.isUnsubscribed()) {
//                    return;
//                }
//
//                DUUpdateXmlFile duUpdateXmlFile = getDuUpdateXmlFile();
//                if (duUpdateXmlFile == null) {
//                    subscriber.onError(new Throwable("the returned value is null"));
//                    return;
//                }
//                subscriber.onNext(true);
//                subscriber.onCompleted();
//            }
//        });
//    }

    public Observable<Boolean> loadAndMatchXml() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                // config.xml
                DUConfigXmlFile duConfigXmlFile = getDuConfigXmlFile();
                // update.xml
                DUUpdateXmlFile duUpdateXmlFile = getDuUpdateXmlFile();

                if ((duConfigXmlFile == null)
                        || (duConfigXmlFile.getComponentList() == null)
                        || (duConfigXmlFile.getComponentList().size() <= 0)) {
                    if (duUpdateXmlFile != null) {
                        duUpdateXmlFile.setApplicationList(null);
                    }
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                    return;
                }

                if ((duUpdateXmlFile == null)
                        || (duUpdateXmlFile.getApplicationList() == null)
                        || (duUpdateXmlFile.getApplicationList().size() <= 0)) {
                    subscriber.onNext(false);
                    subscriber.onCompleted();
                    return;
                }

                // check installed apks
                List<PackageInfo> packageInfoList = PackageUtil.getInstalledPackages(mContext);
                if ((packageInfoList == null) || (packageInfoList.size() <= 0)) {
                    duConfigXmlFile.setComponentList(null);
                    duUpdateXmlFile.setApplicationList(null);
                    subscriber.onNext(false);
                    subscriber.onCompleted();
                    return;
                }

                List<DUUpdateXmlFile.Application> applicationList = duUpdateXmlFile.getApplicationList();
                for (DUUpdateXmlFile.Application application : applicationList) {
                    application.setInstalled(false);
                    if (application.getData() == null) {
                        continue;
                    }

                    for (PackageInfo packageInfo : packageInfoList) {
                        if ((!TextUtil.isNullOrEmpty(application.getPackageName()))
                                && (!TextUtil.isNullOrEmpty(packageInfo.packageName))
                                && application.getPackageName().equals(packageInfo.packageName)) {
                            application.setInstalled(true);
                            break;
                        }
                    }
                }

                // check components
                List<DUConfigXmlFile.Component> componentList = duConfigXmlFile.getComponentList();
                for (DUConfigXmlFile.Component component : componentList) {
                    component.setValid(false);
                    for (DUUpdateXmlFile.Application application : applicationList) {
                        if (application.isInstalled()
                                && (!TextUtil.isNullOrEmpty(component.getPackageName()))
                                && (!TextUtil.isNullOrEmpty(application.getPackageName()))
                                && component.getPackageName().equals(application.getPackageName())) {
                            component.setValid(true);
                            break;
                        }
                    }
                }

                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> saveComponentList(final List<DUConfigXmlFile.Component> componentList) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if ((componentList == null) || (componentList.size() <= 0)) {
                        throw new IllegalArgumentException(DUException.PARAM_ERROR.getName());
                    }

                    DUConfigXmlFile duConfigXmlFile = new DUConfigXmlFile();
                    duConfigXmlFile.setComponentList(componentList);
                    setDuConfigXmlFile(duConfigXmlFile);
                    subscriber.onNext(saveDuConfigXmlFile());
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                }

                subscriber.onCompleted();
            }
        });
    }
}
