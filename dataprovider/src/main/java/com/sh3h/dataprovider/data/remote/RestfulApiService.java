package com.sh3h.dataprovider.data.remote;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface RestfulApiService {

    /**
     * 根据userId获取用户信息
     **/
    @GET("bzzx/v1/mobile/user/{userId}")
    Observable<DUEntityResult<DUUserResult>> getUserInfo(@Path("userId") int userId);

    /**
     *
     * @param userId 用户编号
     * @param
     * @return
     */
    @GET("bzzx/v1/mobile/projects/list")
    Observable<DUEntitiesResult<DUProject>> loadProjects(@Query("userId") int userId, @Query("states")String states);

    /**
     * 分页获取工程项目信息
     *
     * @param userId 用户编号
     * @param states 工程状态
     * @param index  起始位置
     * @param offset 偏移量
     * @return
     */
    @GET("bzzx/v1/mobile/projects/list")
    Observable<DUEntitiesResult<DUProject>> loadProjects(@Query("userId") int userId, @Query("states")String states, @Query("index") int index, @Query("offset") int offset);

    /**
     * 获取单条工程项目信息
     *
     * @param userId 用户编号
     * @param projectNumber 工程编号
     * @return
     */
    @GET("bzzx/v1/mobile/projects/one")
    Observable<DUEntityResult<DUProject>> loadProject(@Query("userId") int userId, @Query("projectNumber") String projectNumber);

    /**
     * 开工、停工、复工、完工信息上传
     *
     * @param requests 请求参数封装体
     * @param userId   用户id
     * @return
     */
    @POST("bzzx/v1/mobile/projects/operator/reply")
    Observable<DUEntitiesResult<DUReplyResult>> uploadOperates(@Body List<DUOperate> requests, @Query("userId") int userId);

    /**
     * 巡视信息上传
     *
     * @param requests 请求参数封装体
     * @param userId   用户id
     * @return
     */
    @POST("bzzx/v1/mobile/projects/patrol/reply")
    Observable<DUEntitiesResult<DUReplyResult>> uploadPatrols(@Body List<DUPatrol> requests, @Query("userId") int userId);

    /**
     * 违规配置信息下载
     *
     * @return
     */
    @GET("bzzx/v1/mobile/words/violations")
    Observable<DUEntitiesResult<DUViolation>> loadViolationWords();

    /**
     * 地址信息下载
     *
     * @param userId 用户编号
     * @return
     */
    @GET("bzzx/v1/mobile/material/address")
    Observable<DUEntitiesResult<DUAddress>> loadAddresses(@Query("userId") int userId);

    /**
     * 地址信息上传
     *
     * @param request 请求参数封装体
     * @param userId  用户id
     * @return
     */
    @POST("bzzx/v1/mobile/material/address/upload")
    Observable<DUEntityResult<DUReplyResult>> uploadAddress(@Body DUAddress request, @Query("userId") int userId);

    /**
     * 地址信息更新
     *
     * @param request  请求参数封装体
     * @param userId   用户id
     * @param serverId
     * @return
     */
    @POST("bzzx/v1/mobile/material/address/update")
    Observable<DUEntityResult<DUReplyResult>> updateAddress(@Body DUAddress request, @Query("userId") int userId, @Query("serverId") int serverId);

    /**
     * 地址信息删除
     *
     * @param request  请求参数封装体
     * @param userId   用户id
     * @param serverId
     * @return
     */
    @POST("bzzx/v1/mobile/material/address/delete")
    Observable<DUEntityResult<DUReplyResult>> deleteAddress(@Body DUAddress request, @Query("userId") int userId, @Query("serverId") int serverId);

    /**
     * 材料配置信息下载
     *
     * @return
     */
    @GET("bzzx/v1/mobile/words/materials")
    Observable<DUEntitiesResult<DUMaterial>> loadMaterialsWords();

    /**
     * 材料申请上传
     *
     * @param requests 请求参数封装体
     * @param userId   用户id
     * @return
     */
    @POST("bzzx/v1/mobile/material/apply")
    Observable<DUEntitiesResult<DUReplyResult>> uploadMaterialApply(@Body List<DUMaterialApply> requests, @Query("userId") int userId);

    /**
     * 材料确认上传
     *
     * @param requests 请求参数封装体
     * @param userId   用户id
     * @return
     */
    @POST("bzzx/v1/mobile/material/confirm")
    Observable<DUEntitiesResult<DUReplyResult>> uploadMaterialConfirm(@Body List<DUMaterialVerify> requests, @Query("userId") int userId);

    /**
     * 获取预算信息
     *
     * @param projectId 工程ID
     * @return
     */
    @GET("bzzx/v1/mobile/projects/budgets/list")
    Observable<DUEntitiesResult<DUBudget>> loadProjectBudgets(@Query("projectId") long projectId);

    /**
     * 获取预算信息
     *
     * @param projectId 工程ID
     * @param state 预算状态
     * @return
     */
    @GET("bzzx/v1/mobile/projects/budgets/list")
    Observable<DUEntitiesResult<DUBudget>> loadProjectBudgets(@Query("projectId") long projectId, @Query("state") int state);

    @GET("bzzx/v1/mobile/words/list?group=all")
    Observable<DUEntitiesResult<DUWord>> loadWords();

    /**
     * 施工方案验收上传
     *
     * @param requests 请求参数封装体
     * @param userId   用户id
     * @return
     */
    @POST("bzzx/v1/mobile/projects/accept/construction/reply")
    Observable<DUEntitiesResult<DUReplyResult>> constructionAcceptUpload(@Body List<DUConstructionAccept> requests, @Query("userId") int userId);

    /**
     * 支管验收上传
     *
     * @param requests 请求参数封装体
     * @param userId   用户id
     * @return
     */
    @POST("bzzx/v1/mobile/projects/accept/branch/reply")
    Observable<DUEntitiesResult<DUReplyResult>> branchAcceptUpload(@Body List<DUBranchAccept> requests, @Query("userId") int userId);

    /**
     * 工程验收上传
     *
     * @param requests 请求参数封装体
     * @param userId   用户id
     * @return
     */
    @POST("bzzx/v1/mobile/projects/accept/project/reply")
    Observable<DUEntitiesResult<DUReplyResult>> projectAcceptUpload(@Body List<DUProjectAccept> requests, @Query("userId") int userId);

    /**
     * 巡视信息查询
     *
     * @param startTime        起始时间
     * @param endTime          截止时间
     * @param violationType    施工类型
     * @param violationContent 施工内容
     * @param patrolSituation  施工结果
     * @return
     */
    @GET("bzzx/v1/mobile/projects/patrol/search")
    Observable<DUEntitiesResult<DUPatrolSearchResult>> searchPatrols(@Query("projectId") long projectId,
                                                                     @Query("startTime") long startTime,
                                                                     @Query("endTime") long endTime,
                                                                     @Query("violationType") String violationType,
                                                                     @Query("violationContent") String violationContent,
                                                                     @Query("patrolSituation") String patrolSituation);

    /**
     * 材料申领单查询
     *
     * @param userId    用户编号
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @return
     */
    @GET("bzzx/v1/mobile/material/apply/search")
    Observable<DUEntitiesResult<DUMaterialApply>> searchApply(@Query("userId") int userId,
                                                              @Query("startTime") long startTime,
                                                              @Query("endTime") long endTime);

    /**
     * 材料确认单查询
     *
     * @param userId    用户编号
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @return
     */
    @GET("bzzx/v1/mobile/material/confirm/search")
    Observable<DUEntitiesResult<DUMaterialVerify>> searchConfirm(@Query("userId") int userId,
                                                                 @Query("startTime") long startTime,
                                                                 @Query("endTime") long endTime);

    @Multipart
    @POST("bzzx/files/upload")
    Observable<DUEntitiesResult<DUMediaResponse>> uploadImage(@Part("description") RequestBody description,
                                                              @Part MultipartBody.Part file);

    @Multipart
    @POST("bzzx/files/upload")
    Observable<DUEntitiesResult<DUMediaResponse>> uploadImages(@Part("description") RequestBody description,
                                                               @Part MultipartBody.Part file1,
                                                               @Part MultipartBody.Part file2,
                                                               @Part MultipartBody.Part file3,
                                                               @Part MultipartBody.Part file4,
                                                               @Part MultipartBody.Part file5,
                                                               @Part MultipartBody.Part file6);

    /**
     * 上传文件关系
     *
     * @param requests 请求参数封装体
     * @return
     */
    @POST("bzzx/v1/mobile/projects/fileInfos/upload")
    Observable<DUBaseResult> uploadFileInfos(@Body List<DUMultiMedia> requests);

    @Multipart
    @POST("bzzx/v1/mobile/files/upload")
    Call<DUEntitiesResult<DUMediaResponse>> uploadImages(@PartMap Map<String, RequestBody> params);

    @GET("bzzx/v1/mobile/projects/accept/timelimit")
    Observable<DUEntityResult<DUAcceptTimeLimit>> getAcceptTimeLimit();

    /********
     * Helper class that sets up a new services
     *******/
    class Factory {
        public static RestfulApiService newInstance(String baseUrl) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(true ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE); // BuildConfig.DEBUG
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(new UnauthorisedInterceptor())
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(RestfulApiService.class);
        }
    }
}
