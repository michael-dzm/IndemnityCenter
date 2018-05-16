package com.sh3h.dataprovider.data.remote;

import android.content.Context;

import com.sh3h.dataprovider.data.entity.DUAcceptTimeLimit;
import com.sh3h.dataprovider.data.entity.DUAddress;
import com.sh3h.dataprovider.data.entity.DUBranchAccept;
import com.sh3h.dataprovider.data.entity.DUBudget;
import com.sh3h.dataprovider.data.entity.DUConstructionAccept;
import com.sh3h.dataprovider.data.entity.DUMaterial;
import com.sh3h.dataprovider.data.entity.DUMaterialApply;
import com.sh3h.dataprovider.data.entity.DUMaterialVerify;
import com.sh3h.dataprovider.data.entity.DUMediaResponse;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.entity.DUOperate;
import com.sh3h.dataprovider.data.entity.DUPatrol;
import com.sh3h.dataprovider.data.entity.DUPatrolSearchResult;
import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.dataprovider.data.entity.DUProjectAccept;
import com.sh3h.dataprovider.data.entity.DUViolation;
import com.sh3h.dataprovider.data.entity.DUWord;
import com.sh3h.dataprovider.data.entity.base.DUBaseResult;
import com.sh3h.dataprovider.data.entity.base.DUEntitiesResult;
import com.sh3h.dataprovider.data.entity.base.DUEntityResult;
import com.sh3h.dataprovider.data.entity.response.DUReplyResult;
import com.sh3h.dataprovider.data.entity.result.DUUserResult;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.data.local.config.SystemConfig;
import com.sh3h.dataprovider.injection.annotation.ApplicationContext;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

@Singleton
public class HttpHelper {

    private static final int UPLOAD_COUNT = 6;

    private final Context mContext;
    private final ConfigHelper mConfigHelper;
    private RestfulApiService restfulApiService;
    private boolean isUrlChanged = false;//url地址改变

    @Inject
    public HttpHelper(@ApplicationContext Context context, ConfigHelper configHelper) {
        mContext = context;
        mConfigHelper = configHelper;
        restfulApiService = null;
    }

    /**
     * 初始化RestfulApiService
     *
     * @return
     */
    private RestfulApiService initRestfulApi() {
        if (restfulApiService == null || isUrlChanged) {
            SystemConfig systemConfig = mConfigHelper.getSystemConfig();
            String baseUrl = systemConfig.getString(SystemConfig.PARAM_SERVER_BASE_URI);
            restfulApiService = RestfulApiService.Factory.newInstance(baseUrl);
        }
        return restfulApiService;
    }

    public void resetRestfulApiService() {
        isUrlChanged = true;
    }

    public Observable<DUEntityResult<DUUserResult>> getUserInfo(int userId) {
        return initRestfulApi().getUserInfo(userId);
    }

    public Observable<DUEntitiesResult<DUViolation>> loadViolationWords() {
        return initRestfulApi().loadViolationWords();
    }

    public Observable<DUEntitiesResult<DUWord>> loadWords() {
        return initRestfulApi().loadWords();
    }

    public Observable<DUEntitiesResult<DUMaterial>> loadMaterials() {
        return initRestfulApi().loadMaterialsWords();
    }

    public Observable<DUEntitiesResult<DUAddress>> loadAddresses(int userId) {
        return initRestfulApi().loadAddresses(userId);
    }

    public Observable<DUEntityResult<DUReplyResult>> uploadAddress(DUAddress duAddress, int userId) {
        return initRestfulApi().uploadAddress(duAddress, userId);
    }

    public Observable<DUEntityResult<DUReplyResult>> updateAddress(DUAddress address) {
        return initRestfulApi().updateAddress(address, address.getUserId().intValue(), address.getServerId().intValue());
    }

    public Observable<DUEntityResult<DUReplyResult>> deleteAddress(DUAddress address) {
        return initRestfulApi().deleteAddress(address, address.getUserId().intValue(), address.getServerId().intValue());
    }

    public Observable<DUEntitiesResult<DUProject>> loadProjects(int userId, String states) {
        return initRestfulApi().loadProjects(userId, states);
    }

    public Observable<DUEntitiesResult<DUProject>> loadProjects(int userId, String states, int index, int offset) {
        return initRestfulApi().loadProjects(userId, states, index, offset);
    }

    public Observable<DUEntityResult<DUProject>> loadProject(int userId, String projectNumber){
        return initRestfulApi().loadProject(userId, projectNumber);
    }

    public Observable<DUEntitiesResult<DUBudget>> loadProjectBudgets(long projectId) {
        return initRestfulApi().loadProjectBudgets(projectId);
    }

    public Observable<DUEntitiesResult<DUBudget>> loadProjectBudgets(long projectId, int state) {
        return initRestfulApi().loadProjectBudgets(projectId, state);
    }

    public Observable<DUEntitiesResult<DUReplyResult>> uploadOperates(List<DUOperate> duOperates, int userId) {
        return initRestfulApi().uploadOperates(duOperates, userId);
    }

    public Observable<DUEntitiesResult<DUReplyResult>> uploadPatrols(List<DUPatrol> duPatrols, int userId) {
        return initRestfulApi().uploadPatrols(duPatrols, userId);
    }

    public Observable<DUEntitiesResult<DUReplyResult>> uploadConstructionAccepts(List<DUConstructionAccept> duConstructionAccepts, int userId) {
        return initRestfulApi().constructionAcceptUpload(duConstructionAccepts, userId);
    }

    public Observable<DUEntitiesResult<DUReplyResult>> uploadBranchAccepts(List<DUBranchAccept> duBranchAccepts, int userId) {
        return initRestfulApi().branchAcceptUpload(duBranchAccepts, userId);
    }

    public Observable<DUEntitiesResult<DUReplyResult>> uploadProjectAccepts(List<DUProjectAccept> duProjectAccepts, int userId) {
        return initRestfulApi().projectAcceptUpload(duProjectAccepts, userId);
    }

    public Observable<DUEntitiesResult<DUPatrolSearchResult>> searchPatrols(long projectId, long startDate, long endDate, String type, String content, String situation) {
        return initRestfulApi().searchPatrols(projectId, startDate, endDate, type, content, situation);
    }

    public Observable<DUEntitiesResult<DUReplyResult>> uploadMaterialApply(List<DUMaterialApply> applies, int userId) {
        return initRestfulApi().uploadMaterialApply(applies, userId);
    }

    public Observable<DUEntitiesResult<DUReplyResult>> uploadMaterialConfirm(List<DUMaterialVerify> applies, int userId) {
        return initRestfulApi().uploadMaterialConfirm(applies, userId);
    }

    public Observable<DUEntitiesResult<DUMaterialApply>> searchApply(int userId, long startTime, long endTime) {
        return initRestfulApi().searchApply(userId, startTime, endTime);
    }

    public Observable<DUEntitiesResult<DUMaterialVerify>> searchConfirm(int userId, long startTime, long endTime) {
        return initRestfulApi().searchConfirm(userId, startTime, endTime);
    }

    public Observable<DUBaseResult> uploadFileInfos(List<DUMultiMedia> duMultiMedias) {
        return initRestfulApi().uploadFileInfos(duMultiMedias);
    }

    public Observable<List<DUMultiMedia>> uploadMultiMedias(final List<DUMultiMedia> duMediaList) {
        return Observable.create(new Observable.OnSubscribe<List<DUMultiMedia>>() {
            @Override
            public void call(Subscriber<? super List<DUMultiMedia>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    if (duMediaList == null) {
                        throw new NullPointerException("duMediaList is null");
                    }
                    Map<String, RequestBody> params = new HashMap<>();
                    for (DUMultiMedia duMultiMedia : duMediaList) {
                        File file = new File(duMultiMedia.getFilePath());
                        //可上传的多媒体文件 避免文件不存在时发生错误
                        if (file.exists()) {
                            // create RequestBody instance from file
                            params.put("file\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
                        }
                    }
                    if (params.size() > 0) {
                        // 执行请求
                        Call<DUEntitiesResult<DUMediaResponse>> call = initRestfulApi().uploadImages(params);
                        Response<DUEntitiesResult<DUMediaResponse>> response = call.execute();
                        DUEntitiesResult<DUMediaResponse> result = response.body();
                        if (result == null) {
                            throw new NullPointerException("result is null");
                        }
                        for (DUMultiMedia duMultiMedia : duMediaList) {
                            for (DUMediaResponse duMediaResponse : result.getData()) {
                                if (duMultiMedia.getFileName().equals(duMediaResponse.getOriginName())) {
                                    //文件上传成功 需要上传文件关联关系才上传成功
                                    duMultiMedia.setFileUrl(duMediaResponse.getUrl());
                                    duMultiMedia.setFileHash(duMediaResponse.getFileHash());
                                }
                            }
                        }
                    }
                    subscriber.onNext(duMediaList);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<DUEntityResult<DUAcceptTimeLimit>> getAcceptTimeLimit(){
        return initRestfulApi().getAcceptTimeLimit();
    }


}
