package com.sh3h.indemnity.ui.material.apply;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.view.View;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUAddress;
import com.sh3h.dataprovider.data.entity.DUMaterial;
import com.sh3h.dataprovider.data.entity.DUMaterialApply;
import com.sh3h.dataprovider.data.entity.response.DUReplyResult;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.ui.base.FunctionPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xulongjun on 2017/3/8.
 */

public class MaterialApplyPresenter extends FunctionPresenter<MaterialApplyMvpView> {
    public static final String TAG = MaterialApplyPresenter.class.getSimpleName();

    public ObservableArrayList<String> mMaterialCategoryData = new ObservableArrayList<>();
    public ObservableArrayList<String> mMaterialNameData = new ObservableArrayList<>();
    public ObservableArrayList<String> mMaterialFormatData = new ObservableArrayList<>();
    public ObservableField<String> mMaterialUnit = new ObservableField<>();
    public ObservableFloat applyCount = new ObservableFloat(0);

    private float multiple = 1;

    @Inject
    public MaterialApplyPresenter(DataManager dataManager, ConfigHelper configHelper) {
        super(dataManager, configHelper);
    }

    public void toAddressManagerActivity(View view) {
        getMvpView().toAddressManagerActivity();
    }

    public void resetApplyCount(DUMaterial duMaterial){
        mMaterialUnit.set(duMaterial.getMaterialUnit());
        applyCount.set(0);
        multiple = duMaterial.getMultiple();
    }

    public void setApplyCount(float count){
        applyCount.set(count);
    }

    public float getMaterialCount(){
        return applyCount.get();
    }

    public void subCount(View view) {
        if (applyCount.get() == 0) {
            return;
        }
        applyCount.set(applyCount.get() - multiple);
    }

    public void addCount(View view) {
        applyCount.set(applyCount.get() + multiple);
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

    public void getDefaultDUAddress(int userId) {
        LogUtil.i(TAG, "getDefaultDUAddress");
        mSubscription.add(mDataManager.getDefaultDUAddress(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUAddress>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getDefaultDUAddress:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getDefaultDUAddress:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(DUAddress duAddress) {
                        LogUtil.i(TAG, "getDefaultDUAddress:onNext");
                        if (duAddress == null) {
                            return;
                        }
                        getMvpView().onAddress(duAddress.getAddressContent());
                    }
                }));
    }

    public void uploadMaterialApply(DUMaterialApply apply, int userId){
        List<DUMaterialApply> duMaterialApplies = new ArrayList<>();
        duMaterialApplies.add(apply);
        LogUtil.i(TAG, "uploadMaterialApply");
        mSubscription.add(mDataManager.uploadMaterialApply(duMaterialApplies, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUReplyResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "uploadMaterialApply:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "uploadMaterialApply:onError:".concat(e.getMessage()));
                        getMvpView().onUploadError(R.string.text_toast_material_apply_upload_failed);
                    }

                    @Override
                    public void onNext(DUReplyResult result) {
                        LogUtil.i(TAG, "uploadMaterialApply:onNext");
                        if(!result.isSuccess()){
                            getMvpView().onUploadError(result.getMessage());
                            return;
                        }
                        getMvpView().onUploadSuccess(R.string.text_toast_material_apply_upload_success);
                    }
                }));

    }

}
