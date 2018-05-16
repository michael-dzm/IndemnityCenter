package com.sh3h.indemnity.ui.material.confirm;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.view.View;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUMaterial;
import com.sh3h.dataprovider.data.entity.DUMaterialVerify;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.entity.response.DUReplyResult;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.ui.base.FunctionPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xulongjun on 2017/3/8.
 */

public class MaterialConfirmPresenter extends FunctionPresenter<MaterialConfirmMvpView> {
    public static final String TAG = MaterialConfirmPresenter.class.getSimpleName();

    public ObservableArrayList<String> mMaterialCategoryData = new ObservableArrayList<>();
    public ObservableArrayList<String> mMaterialNameData = new ObservableArrayList<>();
    public ObservableArrayList<String> mMaterialFormatData = new ObservableArrayList<>();
    public ObservableField<String> mMaterialUnit = new ObservableField<>();
    public ObservableFloat confirmCount = new ObservableFloat(0);

    private float multiple = 1;

    @Inject
    public MaterialConfirmPresenter(DataManager dataManager, ConfigHelper configHelper) {
        super(dataManager, configHelper);
    }

    public void resetApplyCount(DUMaterial duMaterial){
        mMaterialUnit.set(duMaterial.getMaterialUnit());
        confirmCount.set(0);
        multiple = duMaterial.getMultiple();
    }

    public void setApplyCount(float count){
        confirmCount.set(count);
    }

    public float getMaterialCount(){
        return confirmCount.get();
    }

    public void subCount(View view) {
        if (confirmCount.get() == 0) {
            return;
        }
        confirmCount.set(confirmCount.get() - multiple);
    }

    public void addCount(View view) {
        confirmCount.set(confirmCount.get() + multiple);
    }

    public void loadMaterials() {
        LogUtil.i(TAG, "loadMaterials");
        mSubscription.add(mDataManager.loadMaterials()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "loadMaterials:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "loadMaterials:onError:".concat(e.getMessage()));
                        getMvpView().onLoadError(R.string.text_toast_material_download_failed);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "loadMaterials:onCompleted");
                        if (!aBoolean) {
                            getMvpView().onLoadError(R.string.text_toast_material_download_failed);
                            return;
                        }
                        getMvpView().onLoadSuccess();
                    }
                }));
    }

    /**
     * Spinner点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getMvpView().onItemSelected(parent, view, position, id);
    }

    public void getAllMaterialCategory() {
        LogUtil.i(TAG, "getAllMaterialCategory");
        mSubscription.add(mDataManager.getAllMaterialCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUMaterial>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getAllMaterialCategory:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getAllMaterialCategory:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(List<DUMaterial> duMaterials) {
                        LogUtil.i(TAG, "getAllMaterialCategory:onNext");
                        if (duMaterials == null || duMaterials.size() == 0) {
                            return;
                        }
                        mMaterialCategoryData.clear();
                        for (DUMaterial duMaterial : duMaterials) {
                            mMaterialCategoryData.add(duMaterial.getMaterialCategory());
                        }
                        getMvpView().onNotifySpinner(R.id.sp_material_category, duMaterials);
                    }
                }));
    }

    public void getMaterialNameByCategory(String materialCategory) {
        LogUtil.i(TAG, "getAllMaterialCategory");
        mSubscription.add(mDataManager.getMaterialNameByCategory(materialCategory)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUMaterial>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getAllMaterialCategory:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getAllMaterialCategory:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(List<DUMaterial> duMaterials) {
                        LogUtil.i(TAG, "getAllMaterialCategory:onNext");
                        if (duMaterials == null || duMaterials.size() == 0) {
                            return;
                        }
                        mMaterialNameData.clear();
                        for (DUMaterial duMaterial : duMaterials) {
                            mMaterialNameData.add(duMaterial.getMaterialName());
                        }
                        getMvpView().onNotifySpinner(R.id.sp_material_name, duMaterials);
                    }
                }));
    }

    public void getMaterialFormatByName(String materialCategory, String materialName) {
        LogUtil.i(TAG, "getMaterialFormatByName");
        mSubscription.add(mDataManager.getMaterialFormatByName(materialCategory, materialName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUMaterial>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getMaterialFormatByName:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getMaterialFormatByName:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(List<DUMaterial> duMaterials) {
                        LogUtil.i(TAG, "getMaterialFormatByName:onNext");
                        if (duMaterials == null || duMaterials.size() == 0) {
                            return;
                        }
                        mMaterialFormatData.clear();
                        for (DUMaterial duMaterial : duMaterials) {
                            mMaterialFormatData.add(duMaterial.getMaterialFormat());
                        }
                        getMvpView().onNotifySpinner(R.id.sp_material_format, duMaterials);
                    }
                }));
    }

    public void uploadMaterialConfirm(List<DUMaterialVerify> verifies, int userId) {
        LogUtil.i(TAG, "uploadMaterialConfirm");
        mSubscription.add(mDataManager.uploadMaterialConfirm(verifies, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUReplyResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "uploadMaterialConfirm:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "uploadMaterialConfirm:onError:".concat(e.getMessage()));
                        getMvpView().onUploadError(R.string.text_toast_material_confirm_upload_failed);
                    }

                    @Override
                    public void onNext(DUReplyResult duReplyResult) {
                        LogUtil.i(TAG, "uploadMaterialConfirm:onNext");
                        if (!duReplyResult.isSuccess()) {
                            getMvpView().onUploadError(duReplyResult.getMessage());
                            return;
                        }
                        getMvpView().onUploadSuccess(R.string.text_toast_material_confirm_upload_success, duReplyResult.getServerId());
                    }
                }));
    }

    public void uploadMultiMedias(List<DUMultiMedia> duMediaList) {
        LogUtil.i(TAG, "uploadMultiMedias");
        mSubscription.add(mDataManager.uploadOnlineMultiMedias(duMediaList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "uploadMultiMedias:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "uploadMultiMedias:onError:".concat(e.getMessage()));
                        getMvpView().onUploadPhotoError(R.string.text_toast_photo_upload_failed);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "uploadMultiMedias:onNext");
                        if (aBoolean) {
                            getMvpView().onUploadPhotoSuccess(R.string.text_toast_photo_upload_success);
                        }
                    }
                }));

    }


    public void onCameraClick(View view) {
        getMvpView().onTakePhoto();
    }
}
