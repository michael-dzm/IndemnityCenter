package com.sh3h.indemnity.ui.multimedia;

import android.view.View;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by dengzhimin on 2017/3/10.
 */
public class MultimediaPresenter extends ParentPresenter<MultimediaMvpView> {

    private static final String TAG = MultimediaPresenter.class.getName();

    @Inject
    public MultimediaPresenter(DataManager dataManager) {
        super(dataManager);
    }

    /**
     * 保存新照片-(压缩照片)
     *
     * @param fileName
     * @param filePath
     */
    public void saveImage(final String fileName, String filePath) {
        LogUtil.i(TAG, "saveImage");
        if (TextUtil.isNullOrEmpty(fileName) || (filePath == null)) {
            getMvpView().onError(R.string.text_toast_image_compress_save_failed);
            return;
        }
        mSubscription.add(mDataManager.compressImageAndAddStamp(fileName, filePath)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "saveImage:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "saveImage:onError " + e.getMessage());
                        getMvpView().onError(R.string.text_toast_image_compress_save_failed);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "saveImage:onNext " + aBoolean);
                        getMvpView().onSaveImage(aBoolean);
                    }
                }));
    }

    public boolean onLongClick(View view, DUMultiMedia media){
        getMvpView().onDeleteImage(media);
        return true;
    }

    public void onItemClick(View view, DUMultiMedia media){
        getMvpView().onItemClick(media);
    }

}
