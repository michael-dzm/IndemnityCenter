package com.sh3h.dataprovider.data;

import android.content.Context;

import com.sh3h.dataprovider.data.entity.DUAcceptTimeLimit;
import com.sh3h.dataprovider.data.entity.DUAddress;
import com.sh3h.dataprovider.data.entity.DUBranchAccept;
import com.sh3h.dataprovider.data.entity.DUBudget;
import com.sh3h.dataprovider.data.entity.DUConstructionAccept;
import com.sh3h.dataprovider.data.entity.DUMaterial;
import com.sh3h.dataprovider.data.entity.DUMaterialApply;
import com.sh3h.dataprovider.data.entity.DUMaterialVerify;
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
import com.sh3h.dataprovider.data.entity.enums.Upload;
import com.sh3h.dataprovider.data.entity.response.DUReplyResult;
import com.sh3h.dataprovider.data.entity.result.DUUserResult;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.data.local.db.DbHelper;
import com.sh3h.dataprovider.data.local.file.FileHelper;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.data.local.preference.UserSession;
import com.sh3h.dataprovider.data.local.xml.Parser;
import com.sh3h.dataprovider.data.local.xml.XmlHelper;
import com.sh3h.dataprovider.data.remote.Downloader;
import com.sh3h.dataprovider.data.remote.HttpHelper;
import com.sh3h.dataprovider.exception.DUException;
import com.sh3h.dataprovider.injection.annotation.ApplicationContext;
import com.sh3h.dataprovider.util.EventPosterHelper;
import com.sh3h.dataprovider.util.OtoO;
import com.sh3h.localprovider.greendaoEntity.Address;
import com.sh3h.localprovider.greendaoEntity.BranchAccept;
import com.sh3h.localprovider.greendaoEntity.Budget;
import com.sh3h.localprovider.greendaoEntity.ConstructionAccept;
import com.sh3h.localprovider.greendaoEntity.Material;
import com.sh3h.localprovider.greendaoEntity.MultiMedia;
import com.sh3h.localprovider.greendaoEntity.Operate;
import com.sh3h.localprovider.greendaoEntity.Patrol;
import com.sh3h.localprovider.greendaoEntity.Project;
import com.sh3h.localprovider.greendaoEntity.ProjectAccept;
import com.sh3h.localprovider.greendaoEntity.Violation;
import com.sh3h.localprovider.greendaoEntity.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.functions.FuncN;

@Singleton
public class DataManager {
    private static final String TAG = "DataManager";

    private static final int UPLOAD_COUNT = 6;

    private final Context mContext;
    private final HttpHelper mHttpHelper;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final ConfigHelper mConfigHelper;
    private final EventPosterHelper mEventPoster;
    private final Downloader mDownloader;
    private final Parser mParser;
    private final XmlHelper mXmlHelper;
    private final FileHelper mFileHelper;

    @Inject
    public DataManager(@ApplicationContext Context context,
                       HttpHelper httpHelper,
                       PreferencesHelper preferencesHelper,
                       DbHelper dbHelper,
                       ConfigHelper configHelper,
                       Downloader downloader,
                       EventPosterHelper eventPosterHelper,
                       Parser parser,
                       XmlHelper xmlHelper,
                       FileHelper fileHelper) {
        mContext = context;
        mHttpHelper = httpHelper;
        mPreferencesHelper = preferencesHelper;
        mDbHelper = dbHelper;
        mConfigHelper = configHelper;
        mDownloader = downloader;
        mEventPoster = eventPosterHelper;
        mParser = parser;
        mXmlHelper = xmlHelper;
        mFileHelper = fileHelper;
    }

    public void close() {
        mDbHelper.close();
    }

    public void clearData() {
        mDbHelper.clearData();
    }

    /* -----------------------------HttpHelper Operate ----------------------------------------------*/

    public Observable<Boolean> getUserInfo(int userId) {
        return mHttpHelper.getUserInfo(userId)
                .map(new Func1<DUEntityResult<DUUserResult>, Boolean>() {
                    @Override
                    public Boolean call(DUEntityResult<DUUserResult> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        DUUserResult duUserResult = result.getData();
                        if (duUserResult == null) {
                            throw new NullPointerException(DUException.RETURN_NULL.getName());
                        }
                        UserSession userSession = mPreferencesHelper.getUserSession();
                        userSession.setUserId(duUserResult.getUserId());
                        userSession.setUserName(duUserResult.getUserName());
                        userSession.setAccount(duUserResult.getAccount());
                        userSession.setSex(duUserResult.getSex());
                        userSession.setAddress(duUserResult.getAddress());
                        userSession.setMobile(duUserResult.getMobile());
                        userSession.setTel(duUserResult.getTel());
                        userSession.setConstructionTeam(duUserResult.getConstructionTeam());
                        userSession.setRoles(duUserResult.getRoles());
                        return userSession.save();
                    }
                });
    }

    public Observable<List<DUViolation>> loadViolationWords() {
        return mHttpHelper.loadViolationWords()
                .map(new Func1<DUEntitiesResult<DUViolation>, List<DUViolation>>() {
                    @Override
                    public List<DUViolation> call(DUEntitiesResult<DUViolation> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData();
                    }
                })
                .concatMap(new Func1<List<DUViolation>, Observable<? extends List<DUViolation>>>() {
                    @Override
                    public Observable<? extends List<DUViolation>> call(List<DUViolation> duViolations) {
                        return saveViolations(duViolations);
                    }
                });
    }

    public Observable<Boolean> loadWords() {
        return mHttpHelper.loadWords()
                .map(new Func1<DUEntitiesResult<DUWord>, List<DUWord>>() {
                    @Override
                    public List<DUWord> call(DUEntitiesResult<DUWord> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData();
                    }
                })
                .concatMap(new Func1<List<DUWord>, Observable<? extends Boolean>>() {
                    @Override
                    public Observable<? extends Boolean> call(List<DUWord> duWords) {
                        return saveWords(duWords);
                    }
                });
    }

    public Observable<Boolean> loadMaterials() {
        return mHttpHelper.loadMaterials()
                .map(new Func1<DUEntitiesResult<DUMaterial>, List<DUMaterial>>() {
                    @Override
                    public List<DUMaterial> call(DUEntitiesResult<DUMaterial> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData();
                    }
                })
                .concatMap(new Func1<List<DUMaterial>, Observable<? extends Boolean>>() {
                    @Override
                    public Observable<? extends Boolean> call(List<DUMaterial> duMaterials) {
                        return saveMaterials(duMaterials);
                    }
                });
    }

    public Observable<Boolean> uploadAddress(final DUAddress duAddress, int userId) {
        return mHttpHelper.uploadAddress(duAddress, userId)
                .map(new Func1<DUEntityResult<DUReplyResult>, Boolean>() {
                    @Override
                    public Boolean call(DUEntityResult<DUReplyResult> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData().isSuccess();
                    }
                });
    }

    public Observable<Boolean> updateAddress(DUAddress address) {
        return mHttpHelper.updateAddress(address)
                .map(new Func1<DUEntityResult<DUReplyResult>, Boolean>() {
                    @Override
                    public Boolean call(DUEntityResult<DUReplyResult> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData().isSuccess();
                    }
                });
    }

    public Observable<List<DUAddress>> loadAddresses(int userId) {
        return mHttpHelper.loadAddresses(userId)
                .map(new Func1<DUEntitiesResult<DUAddress>, List<DUAddress>>() {
                    @Override
                    public List<DUAddress> call(DUEntitiesResult<DUAddress> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData();
                    }
                })
                .concatMap(new Func1<List<DUAddress>, Observable<? extends List<DUAddress>>>() {
                    @Override
                    public Observable<? extends List<DUAddress>> call(List<DUAddress> duAddresses) {
                        if (duAddresses == null || duAddresses.size() == 0) {
                            return Observable.just(null);
                        }
                        return saveAddresses(duAddresses);
                    }
                });
    }

    public Observable<Boolean> deleteAddress(DUAddress duAddress) {
        return mHttpHelper.deleteAddress(duAddress)
                .map(new Func1<DUEntityResult<DUReplyResult>, Boolean>() {
                    @Override
                    public Boolean call(DUEntityResult<DUReplyResult> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData().isSuccess();
                    }
                });
    }

    public Observable<List<DUProject>> loadProjects(int userId, List<Integer> projectStates, int index, int offset) {
        StringBuffer buffer = new StringBuffer();
        for (int state : projectStates) {
            buffer.append(state).append(",");
        }
        return mHttpHelper.loadProjects(userId, buffer.substring(0, buffer.length() - 1), index, offset)
                .map(new Func1<DUEntitiesResult<DUProject>, List<DUProject>>() {
                    @Override
                    public List<DUProject> call(DUEntitiesResult<DUProject> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData();
                    }
                })
                .concatMap(new Func1<List<DUProject>, Observable<? extends List<DUProject>>>() {
                    @Override
                    public Observable<? extends List<DUProject>> call(List<DUProject> duProjects) {
                        if (duProjects == null || duProjects.size() == 0) {
                            return Observable.just(null);
                        }
                        return saveProjects(duProjects);
                    }
                });
    }

    public Observable<List<DUProject>> loadProjects(int userId, int projectState) {
        return mHttpHelper.loadProjects(userId, String.valueOf(projectState))
                .map(new Func1<DUEntitiesResult<DUProject>, List<DUProject>>() {
                    @Override
                    public List<DUProject> call(DUEntitiesResult<DUProject> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData();
                    }
                })
                .concatMap(new Func1<List<DUProject>, Observable<? extends List<DUProject>>>() {
                    @Override
                    public Observable<? extends List<DUProject>> call(List<DUProject> duProjects) {
                        if (duProjects == null || duProjects.size() == 0) {
                            return Observable.just(null);
                        }
                        return saveProjects(duProjects);
                    }
                });
    }

    public Observable<DUProject> loadProject(int userId, String projectNumber) {
        return mHttpHelper.loadProject(userId, projectNumber)
                .map(new Func1<DUEntityResult<DUProject>, DUProject>() {
                    @Override
                    public DUProject call(DUEntityResult<DUProject> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData();
                    }
                })
                .concatMap(new Func1<DUProject, Observable<? extends DUProject>>() {
                    @Override
                    public Observable<? extends DUProject> call(DUProject duProject) {
                        if (duProject == null) {
                            return Observable.just(null);
                        }
                        return saveProject(duProject);
                    }
                });
    }

    public Observable<DUProject> loadProjectBudgetCount(List<DUProject> duProjects) {
        return Observable.from(duProjects)
                .map(new Func1<DUProject, DUProject>() {
                    @Override
                    public DUProject call(DUProject project) {
                        long totalCount = mDbHelper.getBudgetTotalCount(project.getProjectId());
                        if (totalCount > 0) {
                            long constructionCount = mDbHelper.getBudgetCount(project.getProjectId(), DUBudget.State.CONSTRUCTION_QUALIFIED.getValue());
                            long strengthCount = mDbHelper.getBudgetCount(project.getProjectId(), DUBudget.State.STRENGTH_QUALIFIED.getValue());
                            long airtightCount = mDbHelper.getBudgetCount(project.getProjectId(), DUBudget.State.AIRTIGHT_QUALIFIED.getValue());
                            long projectCount = mDbHelper.getBudgetCount(project.getProjectId(), DUBudget.State.PROJECT_QUALIFIED.getValue());
                            project.setBudgetTotalCount((int) totalCount);
                            project.setConstructionAcceptCount((int) constructionCount);
                            project.setStrengthAcceptCount((int) strengthCount);
                            project.setAirtightAcceptCount((int) airtightCount);
                            project.setProjectAcceptCount((int) projectCount);
                        }
                        return project;
                    }
                });
    }

    public Observable<List<DUBudget>> loadProjectBudgets(long projectId) {
        return mHttpHelper.loadProjectBudgets(projectId)
                .map(new Func1<DUEntitiesResult<DUBudget>, List<DUBudget>>() {
                    @Override
                    public List<DUBudget> call(DUEntitiesResult<DUBudget> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData();
                    }
                })
                .concatMap(new Func1<List<DUBudget>, Observable<? extends List<DUBudget>>>() {
                    @Override
                    public Observable<? extends List<DUBudget>> call(List<DUBudget> duBudgets) {
                        if (duBudgets == null || duBudgets.size() == 0) {
                            return Observable.just(null);
                        }
                        return saveProjectBudgets(duBudgets);
                    }
                });
    }

    public Observable<List<DUBudget>> loadProjectBudgets(long projectId, int state) {
        return mHttpHelper.loadProjectBudgets(projectId, state)
                .map(new Func1<DUEntitiesResult<DUBudget>, List<DUBudget>>() {
                    @Override
                    public List<DUBudget> call(DUEntitiesResult<DUBudget> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData();
                    }
                })
                .concatMap(new Func1<List<DUBudget>, Observable<? extends List<DUBudget>>>() {
                    @Override
                    public Observable<? extends List<DUBudget>> call(List<DUBudget> duBudgets) {
                        if (duBudgets == null || duBudgets.size() == 0) {
                            return Observable.just(null);
                        }
                        return saveProjectBudgets(duBudgets);
                    }
                });
    }

    /**
     * 上传工程操作，更新工程操作，然后获取所有上传成功的工程操作
     * @param userName
     * @param upload
     * @param userId
     * @return
     */
    public Observable<List<Map<String, Long>>> uploadOperates(final String userName, int upload, final int userId) {
        return getOperates(userName, upload)
                .flatMap(new Func1<List<DUOperate>, Observable<? extends List<DUOperate>>>() {
                    @Override
                    public Observable<? extends List<DUOperate>> call(final List<DUOperate> duOperates) {
                        return uploadOperates(duOperates, userId);
                    }
                })
                .concatMap(new Func1<List<DUOperate>, Observable<? extends Iterable<Operate>>>() {
                    @Override
                    public Observable<? extends Iterable<Operate>> call(List<DUOperate> duOperates) {
                        return updateOperates(duOperates);
                    }
                }).concatMap(new Func1<Iterable<Operate>, Observable<? extends List<Map<String, Long>>>>() {
                    @Override
                    public Observable<? extends List<Map<String, Long>>> call(Iterable<Operate> operates) {
                        return getOperateIds(userName, Upload.UPLOADED.getValue());
                    }
                });
    }

    public Observable<List<DUOperate>> uploadOperates(final List<DUOperate> duOperates, int userId){
        return mHttpHelper.uploadOperates(duOperates, userId)
                .map(new Func1<DUEntitiesResult<DUReplyResult>, List<DUOperate>>() {
                    @Override
                    public List<DUOperate> call(DUEntitiesResult<DUReplyResult> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        List<DUReplyResult> duReplyResults = result.getData();
                        for (DUOperate duOperate : duOperates) {
                            for (DUReplyResult duReplyResult : duReplyResults) {
                                if (duOperate.getOperateId() == duReplyResult.getLocalId()) {
                                    if (duReplyResult.isSuccess()) {
                                        duOperate.setServerId(duReplyResult.getServerId());
                                        duOperate.setUpload(Upload.UPLOADED.getValue());
                                    }else{
                                        duOperate.setUpload(Upload.FAILED.getValue());
                                    }
                                }
                            }
                        }
                        return duOperates;
                    }
                });
    }

    public Observable<List<Map<String, Long>>> getOperateIds(String userName, int upload){
        return getOperates(userName, upload)
                .map(new Func1<List<DUOperate>, List<Map<String, Long>>>() {
                    @Override
                    public List<Map<String, Long>> call(List<DUOperate> duOperates) {
                        List<Map<String, Long>> maps = new ArrayList<>();
                        for (DUOperate duOperate : duOperates) {
                            Map<String, Long> map = new HashMap<String, Long>();
                            map.put("localId", duOperate.getOperateId());
                            map.put("serverId", duOperate.getServerId());
                            maps.add(map);
                        }
                        return maps;
                    }
                });
    }

    public Observable<List<Map<String, Object>>> uploadPatrols(final int type, int upload, final int userId, final String userName) {
        return getPatrols(userName, type, upload)
                .concatMap(new Func1<List<DUPatrol>, Observable<? extends List<DUPatrol>>>() {
                    @Override
                    public Observable<? extends List<DUPatrol>> call(final List<DUPatrol> duPatrols) {
                        return uploadPatrols(duPatrols, userId);
                    }
                })
                .concatMap(new Func1<List<DUPatrol>, Observable<? extends Iterable<Patrol>>>() {
                    @Override
                    public Observable<? extends Iterable<Patrol>> call(List<DUPatrol> duPatrols) {
                        return updatePatrols(duPatrols);
                    }
                })
                .concatMap(new Func1<Iterable<Patrol>, Observable<? extends List<Map<String, Object>>>>() {
                    @Override
                    public Observable<? extends List<Map<String, Object>>> call(Iterable<Patrol> patrols) {
                        return getPatrolIds(userName, type, Upload.UPLOADED.getValue());
                    }
                });

    }

    public Observable<List<DUPatrol>> uploadPatrols(final List<DUPatrol> duPatrols, int userId){
        return mHttpHelper.uploadPatrols(duPatrols, userId)
                .map(new Func1<DUEntitiesResult<DUReplyResult>, List<DUPatrol>>() {
                    @Override
                    public List<DUPatrol> call(DUEntitiesResult<DUReplyResult> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        List<DUReplyResult> duReplyResults = result.getData();
                        for (DUPatrol duPatrol : duPatrols) {
                            for (DUReplyResult duReplyResult : duReplyResults) {
                                if (duPatrol.getPatrolId() == duReplyResult.getLocalId()) {
                                    if (duReplyResult.isSuccess()) {
                                        duPatrol.setServerId(duReplyResult.getServerId());
                                        duPatrol.setUpload(Upload.UPLOADED.getValue());
                                    }else{
                                        duPatrol.setUpload(Upload.FAILED.getValue());
                                    }
                                }
                            }
                        }
                        return duPatrols;
                    }
                });
    }

    public Observable<List<Map<String, Object>>> getPatrolIds(String userName, int type, int upload){
        return getPatrols(userName, type, upload)
                .map(new Func1<List<DUPatrol>, List<Map<String, Object>>>() {
                    @Override
                    public List<Map<String, Object>> call(List<DUPatrol> duPatrols) {
                        List<Map<String, Object>> maps = new ArrayList<>();
                        for (DUPatrol duPatrol : duPatrols) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("localId", duPatrol.getPatrolId());
                            map.put("serverId", duPatrol.getServerId());
                            map.put("relateType", duPatrol.getPatrolType());
                            if(duPatrol.getPatrolType() == DUPatrol.Type.DAILY_PATROL.getValue()){
                                map.put("relateType", DUMultiMedia.RelateType.DAILY_PATROL.getValue());
                            }else{
                                map.put("relateType", DUMultiMedia.RelateType.CENTER_PATROL.getValue());
                            }
                            maps.add(map);
                        }
                        return maps;
                    }
                });
    }

    public Observable<Object> uploadAccepts(int upload, int userId, String userName) {
        return Observable.zip(uploadConstructionAccepts(upload, userId, userName), uploadBranchAccepts(upload, userId, userName),
                uploadProjectAccepts(upload, userId, userName), new Func3<Object, Object, Object, Object>() {
                    @Override
                    public Object call(Object o, Object o2, Object o3) {
                        //此处可以将3个集合结果合成一个集合Iterable<ConstructionAccept> Iterable<BranchAccept> Iterable<ProjectAccept>
                        //暂无用到此返回数据 暂不处理
                        return null;
                    }
                });
    }

    public Observable<List<Map<String, Object>>> getAcceptIds(String userName, int upload){
        return Observable.zip(getConstructionAcceptIds(userName, upload), getBranchAcceptIds(userName, upload),
                getProjectAcceptIds(userName, upload), new Func3<List<Map<String, Object>>, List<Map<String, Object>>, List<Map<String, Object>>, List<Map<String, Object>>>() {
                    @Override
                    public List<Map<String, Object>> call(List<Map<String, Object>> maps, List<Map<String, Object>> maps2, List<Map<String, Object>> maps3) {
                        maps.addAll(maps2);
                        maps.addAll(maps3);
                        return maps;
                    }
                });
    }

    public Observable<Object> uploadConstructionAccepts(int upload, final int userId, final String userName){
        return getConstructionAccepts(userName, upload)
                .flatMap(new Func1<List<DUConstructionAccept>, Observable<? extends List<DUConstructionAccept>>>() {
                    @Override
                    public Observable<? extends List<DUConstructionAccept>> call(List<DUConstructionAccept> duConstructionAccepts) {
                        return uploadConstructionAccepts(duConstructionAccepts, userId);
                    }
                })
                .concatMap(new Func1<List<DUConstructionAccept>, Observable<?>>() {
                    @Override
                    public Observable<?> call(List<DUConstructionAccept> duConstructionAccepts) {
                        return updateConstructionAccepts(duConstructionAccepts);
                    }
                });

    }

    public Observable<List<DUConstructionAccept>> uploadConstructionAccepts(final List<DUConstructionAccept> duConstructionAccepts, int userId){
        return mHttpHelper.uploadConstructionAccepts(duConstructionAccepts, userId)
                .map(new Func1<DUEntitiesResult<DUReplyResult>, List<DUConstructionAccept>>() {
                    @Override
                    public List<DUConstructionAccept> call(DUEntitiesResult<DUReplyResult> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        List<DUReplyResult> duReplyResults = result.getData();
                        for (DUConstructionAccept duConstructionAccept : duConstructionAccepts) {
                            for (DUReplyResult duReplyResult : duReplyResults) {
                                if (duConstructionAccept.getAcceptId() == duReplyResult.getLocalId()) {
                                    //上传返回成功 设置serverId
                                    if (duReplyResult.isSuccess()) {
                                        duConstructionAccept.setServerId(duReplyResult.getServerId());
                                        duConstructionAccept.setUpload(Upload.UPLOADED.getValue());
                                    }else{
                                        //上传返回失败 将不再上传
                                        duConstructionAccept.setUpload(Upload.FAILED.getValue());
                                    }
                                }
                            }
                        }
                        return duConstructionAccepts;
                    }
                });
    }

    public Observable<List<Map<String, Object>>> getConstructionAcceptIds(String userName, int upload){
        return getConstructionAccepts(userName, upload)
                .map(new Func1<List<DUConstructionAccept>, List<Map<String, Object>>>() {
                    @Override
                    public List<Map<String, Object>> call(List<DUConstructionAccept> duConstructionAccepts) {
                        List<Map<String, Object>> branchIds = new ArrayList<>();
                        for (DUConstructionAccept duConstructionAccept : duConstructionAccepts) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("localId", duConstructionAccept.getAcceptId());
                            map.put("serverId", duConstructionAccept.getServerId());
                            map.put("relateType", DUMultiMedia.RelateType.CONSTRUCTION_ACCEPT.getValue());
                            branchIds.add(map);
                        }
                        return branchIds;
                    }
                });
    }

    public Observable<Object> uploadBranchAccepts(final int upload, final int userId, final String userName) {
        return getBranchAccepts(userName, upload)
                .flatMap(new Func1<List<DUBranchAccept>, Observable<? extends List<DUBranchAccept>>>() {
                    @Override
                    public Observable<? extends List<DUBranchAccept>> call(final List<DUBranchAccept> duBranchAccepts) {
                        return uploadBranchAccepts(duBranchAccepts, userId);
                    }
                })
                .concatMap(new Func1<List<DUBranchAccept>, Observable<?>>() {
                    @Override
                    public Observable<?> call(List<DUBranchAccept> duBranchAccepts) {
                        return updateBranchAccepts(duBranchAccepts);
                    }
                });

    }

    public Observable<List<DUBranchAccept>> uploadBranchAccepts(final List<DUBranchAccept> duBranchAccepts, int userId){
        return mHttpHelper.uploadBranchAccepts(duBranchAccepts, userId)
                .map(new Func1<DUEntitiesResult<DUReplyResult>, List<DUBranchAccept>>() {
                    @Override
                    public List<DUBranchAccept> call(DUEntitiesResult<DUReplyResult> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        List<DUReplyResult> duReplyResults = result.getData();
                        for (DUBranchAccept duBranchAccept : duBranchAccepts) {
                            for (DUReplyResult duReplyResult : duReplyResults) {
                                if (duBranchAccept.getAcceptId() == duReplyResult.getLocalId()) {
                                    //上传返回成功 设置serverId
                                    if (duReplyResult.isSuccess()) {
                                        duBranchAccept.setServerId(duReplyResult.getServerId());
                                        duBranchAccept.setUpload(Upload.UPLOADED.getValue());
                                    }else {
                                        //上传返回失败 将不再上传
                                        duBranchAccept.setUpload(Upload.FAILED.getValue());
                                    }
                                }
                            }
                        }
                        return duBranchAccepts;
                    }
                });
    }

    public Observable<List<Map<String, Object>>> getBranchAcceptIds(String userName, int upload){
        return getBranchAccepts(userName, upload)
                .map(new Func1<List<DUBranchAccept>, List<Map<String, Object>>>() {
                    @Override
                    public List<Map<String, Object>> call(List<DUBranchAccept> duBranchAccepts) {
                        List<Map<String, Object>> branchIds = new ArrayList<>();
                        for (DUBranchAccept duBranchAccept : duBranchAccepts) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("localId", duBranchAccept.getAcceptId());
                            map.put("serverId", duBranchAccept.getServerId());
                            if(duBranchAccept.getAcceptType() == DUBranchAccept.ACCEPT_TYPE_STRENGTH){
                                map.put("relateType", DUMultiMedia.RelateType.STRENGTH_ACCEPT.getValue());
                            }else{
                                map.put("relateType", DUMultiMedia.RelateType.AIRTIGHT_ACCEPT.getValue());
                            }
                            branchIds.add(map);
                        }
                        return branchIds;
                    }
                });
    }

    public Observable<Object> uploadProjectAccepts(int upload, final int userId, final String userName) {
        return getProjectAccepts(userName, upload)
                .flatMap(new Func1<List<DUProjectAccept>, Observable<? extends List<DUProjectAccept>>>() {
                    @Override
                    public Observable<? extends List<DUProjectAccept>> call(final List<DUProjectAccept> duProjectAccepts) {
                        return uploadProjectAccepts(duProjectAccepts, userId);
                    }
                })
                .concatMap(new Func1<List<DUProjectAccept>, Observable<?>>() {
                    @Override
                    public Observable<?> call(List<DUProjectAccept> duProjectAccepts) {
                        return updateProjectAccepts(duProjectAccepts);
                    }
                });

    }

    public Observable<List<DUProjectAccept>> uploadProjectAccepts(final List<DUProjectAccept> duProjectAccepts, int userId){
        return mHttpHelper.uploadProjectAccepts(duProjectAccepts, userId)
                .map(new Func1<DUEntitiesResult<DUReplyResult>, List<DUProjectAccept>>() {
                    @Override
                    public List<DUProjectAccept> call(DUEntitiesResult<DUReplyResult> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        List<DUReplyResult> duReplyResults = result.getData();
                        for (DUProjectAccept duProjectAccept : duProjectAccepts) {
                            for (DUReplyResult duReplyResult : duReplyResults) {
                                if (duProjectAccept.getAcceptId() == duReplyResult.getLocalId()) {
                                    if (duReplyResult.isSuccess()) {
                                        duProjectAccept.setServerId(duReplyResult.getServerId());
                                        duProjectAccept.setUpload(Upload.UPLOADED.getValue());
                                    }else {
                                        duProjectAccept.setUpload(Upload.FAILED.getValue());
                                    }
                                }
                            }
                        }
                        return duProjectAccepts;
                    }
                });
    }

    public Observable<List<Map<String, Object>>> getProjectAcceptIds(String userName, int upload){
        return getProjectAccepts(userName, upload)
                .map(new Func1<List<DUProjectAccept>, List<Map<String, Object>>>() {
                    @Override
                    public List<Map<String, Object>> call(List<DUProjectAccept> duProjectAccepts) {
                        List<Map<String, Object>> branchIds = new ArrayList<>();
                        for (DUProjectAccept duProjectAccept : duProjectAccepts) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("localId", duProjectAccept.getAcceptId());
                            map.put("serverId", duProjectAccept.getServerId());
                            map.put("relateType", DUMultiMedia.RelateType.PROJECT_ACCEPT.getValue());
                            branchIds.add(map);
                        }
                        return branchIds;
                    }
                });
    }

    /**
     * 获取工程操作已上传多媒体未上传的多媒体数量
     * @param userName
     * @param types 多媒体类型
     * @param operateUpload 工程操作上传状态
     * @param mediaUpload 多媒体上传状态
     * @return
     */
    public Observable<Integer> getOperateMultiMediaCount(String userName, final List<Integer> types, int operateUpload, final int mediaUpload){
        return getOperateIds(userName, operateUpload)
                .concatMap(new Func1<List<Map<String, Long>>, Observable<? extends Integer>>() {
                    @Override
                    public Observable<? extends Integer> call(List<Map<String, Long>> ids) {
                        return mDbHelper.queryMultiMedias(ids, types, mediaUpload)
                                .map(new Func1<List<MultiMedia>, Integer>() {
                                    @Override
                                    public Integer call(List<MultiMedia> multiMedias) {
                                        return multiMedias == null ? 0 : multiMedias.size();
                                    }
                                });
                    }
                });
    }

    /**
     * 获取巡视已上传多媒体未上传的多媒体数量
     * @param userName
     * @param type 巡视状态
     * @param patrolUpload 巡视上传状态
     * @param mediaUpload 多媒体上传状态
     * @return
     */
    public Observable<Integer> getPatrolMultiMediaCount(String userName, int type, int patrolUpload, final int mediaUpload){
        return getPatrolIds(userName, type, patrolUpload)
                .concatMap(new Func1<List<Map<String, Object>>, Observable<? extends Integer>>() {
                    @Override
                    public Observable<? extends Integer> call(List<Map<String, Object>> idTypes) {
                        return mDbHelper.queryMultiMedias(idTypes, mediaUpload)
                                .map(new Func1<List<MultiMedia>, Integer>() {
                                    @Override
                                    public Integer call(List<MultiMedia> multiMedias) {
                                        return multiMedias == null ? 0 : multiMedias.size();
                                    }
                                });
                    }
                });
    }

    /**
     * 获取验收已上传多媒体未上传的多媒体数量
     * @param userName
     * @param acceptUpload
     * @param mediaUpload
     * @return
     */
    public Observable<Integer> getAcceptMultiMediaCount(String userName, int acceptUpload, final int mediaUpload){
        return getAcceptIds(userName, acceptUpload)
                .concatMap(new Func1<List<Map<String, Object>>, Observable<? extends Integer>>() {
                    @Override
                    public Observable<? extends Integer> call(List<Map<String, Object>> idTypes) {
                        return mDbHelper.queryMultiMedias(idTypes, mediaUpload)
                                .map(new Func1<List<MultiMedia>, Integer>() {
                                    @Override
                                    public Integer call(List<MultiMedia> multiMedias) {
                                        return multiMedias == null ? 0 : multiMedias.size();
                                    }
                                });
                    }
                });
    }

    /**
     * 在线操作上传多媒体
     * @param duMediaList
     * @return
     */
    public Observable<Boolean> uploadOnlineMultiMedias(List<DUMultiMedia> duMediaList) {
        return mHttpHelper.uploadMultiMedias(duMediaList)
                .filter(new Func1<List<DUMultiMedia>, Boolean>() {
                    @Override
                    public Boolean call(List<DUMultiMedia> duMultiMedia) {
                        return (duMultiMedia != null) && (duMultiMedia.size() >= 0);
                    }
                })
                .concatMap(new Func1<List<DUMultiMedia>, Observable<? extends Boolean>>() {
                    @Override
                    public Observable<? extends Boolean> call(List<DUMultiMedia> duMultiMedias) {
                        return mHttpHelper.uploadFileInfos(duMultiMedias)
                                .map(new Func1<DUBaseResult, Boolean>() {
                                    @Override
                                    public Boolean call(DUBaseResult duBaseResult) {
                                        if (duBaseResult.getCode() != DUEntitiesResult.CODE_0
                                                || duBaseResult.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                                            throw new NullPointerException(String.format("code:%d statusCode:%d",
                                                    duBaseResult.getCode(), duBaseResult.getStatusCode()));
                                        }
                                        return true;
                                    }
                                });
                    }
                });
    }

    public Observable<HashMap<String, Integer>> uploadMultiMedias(List<Map<String, Object>> idTypes, int upload) {
        return mDbHelper.queryMultiMedias(idTypes, upload)
                .filter(new Func1<List<MultiMedia>, Boolean>() {
                    @Override
                    public Boolean call(List<MultiMedia> duMediaList) {
                        return duMediaList != null || duMediaList.size() > 0;
                    }
                }).concatMap(new Func1<List<MultiMedia>, Observable<HashMap<String, Integer>>>() {
                    @Override
                    public Observable<HashMap<String, Integer>> call(List<MultiMedia> multiMedias) {
                        return uploadOfflineMultiMedias(OtoO.toDUMultiMedias(multiMedias));
                    }
                });

    }

    public Observable<HashMap<String, Integer>> uploadMultiMedias(List<Map<String, Long>> ids, List<Integer> types, int upload) {
        return mDbHelper.queryMultiMedias(ids, types, upload)
                .filter(new Func1<List<MultiMedia>, Boolean>() {
                    @Override
                    public Boolean call(List<MultiMedia> duMediaList) {
                        return duMediaList != null || duMediaList.size() > 0;
                    }
                }).concatMap(new Func1<List<MultiMedia>, Observable<HashMap<String, Integer>>>() {
                    @Override
                    public Observable<HashMap<String, Integer>> call(List<MultiMedia> multiMedias) {
                        return uploadOfflineMultiMedias(OtoO.toDUMultiMedias(multiMedias));
                    }
                });

    }

    /**
     * 离线操作上传多媒体
     * @param duMediaList
     * @return
     */
    public Observable<HashMap<String, Integer>> uploadOfflineMultiMedias(final List<DUMultiMedia> duMediaList){
        List<Observable<Iterable<MultiMedia>>> observables = new ArrayList<>();
        int index = duMediaList.size()/UPLOAD_COUNT + (duMediaList.size()%UPLOAD_COUNT == 0?0:1);
        //分发上传多媒体文件 每次传6张图片
        for(int i=0; i<index; i++){
            observables.add(patchUploadOfflineMultiMedias(duMediaList.subList(UPLOAD_COUNT*i, UPLOAD_COUNT*(i+1)
                    <= duMediaList.size()?UPLOAD_COUNT*(i+1):duMediaList.size())));
        }
        return Observable.zip(observables, new FuncN<HashMap<String, Integer>>() {
            @Override
            public HashMap<String, Integer> call(Object... args) {
                //统计成功和失败数据数量
                HashMap<String, Integer> result = new HashMap<String, Integer>();
                int failedCount = 0;
                int successCount = 0;
                for(int i=0; i<args.length; i++){
                    Iterator<MultiMedia> iterator = (Iterator<MultiMedia>) args[i];
                    while (iterator.hasNext()) {
                        MultiMedia media = iterator.next();
                        if (media.getUPLOAD() == Upload.FAILED.getValue()) {
                            failedCount++;
                        } else {
                            successCount++;
                        }
                    }
                }
                result.put("failedCount", failedCount);
                result.put("successCount", successCount);
                return result;
            }
        });
    }

    /**
     * 分发离线操作上传多媒体
     * @param duMediaList
     * @return
     */
    public Observable<Iterable<MultiMedia>> patchUploadOfflineMultiMedias(final List<DUMultiMedia> duMediaList) {
        return mHttpHelper.uploadMultiMedias(duMediaList)
                .concatMap(new Func1<List<DUMultiMedia>, Observable<? extends List<DUMultiMedia>>>() {
                    @Override
                    public Observable<? extends List<DUMultiMedia>> call(final List<DUMultiMedia> duMultiMedias) {
                        return mHttpHelper.uploadFileInfos(duMultiMedias)
                                .map(new Func1<DUBaseResult, List<DUMultiMedia>>() {
                                    @Override
                                    public List<DUMultiMedia> call(DUBaseResult result) {
                                        if (result.getCode() != DUEntitiesResult.CODE_0 ||
                                                result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                                            throw new NullPointerException(String.format("code:%d statusCode:%d",
                                                    result.getCode(), result.getStatusCode()));
                                        }
                                        for (DUMultiMedia duMultiMedia : duMultiMedias) {
                                            if (duMultiMedia.getFileUrl() != null && duMultiMedia.getFileHash() != null) {
                                                duMultiMedia.setUpload(Upload.UPLOADED.getValue());
                                            } else {
                                                duMultiMedia.setUpload(Upload.FAILED.getValue());
                                            }
                                        }
                                        return duMultiMedias;
                                    }
                                });
                    }
                }).concatMap(new Func1<List<DUMultiMedia>, Observable<? extends Iterable<MultiMedia>>>() {
                    @Override
                    public Observable<? extends Iterable<MultiMedia>> call(List<DUMultiMedia> duMultiMedia) {
                        //更新本地数据
                        return mDbHelper.updateMultiMedias(OtoO.toMultiMedias(duMultiMedia));
                    }
                });

    }

    public Observable<Void> deleteMultiMedia(DUMultiMedia multiMedia) {
        return mDbHelper.deleteMultiMedia(OtoO.toMultiMedia(multiMedia));
    }

    public Observable<List<DUPatrolSearchResult>> searchPatrols(long projectId, long startDate, long endDate, String type, String content, String situation) {
        return mHttpHelper.searchPatrols(projectId, startDate, endDate, type, content, situation)
                .map(new Func1<DUEntitiesResult<DUPatrolSearchResult>, List<DUPatrolSearchResult>>() {
                    @Override
                    public List<DUPatrolSearchResult> call(DUEntitiesResult<DUPatrolSearchResult> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData();
                    }
                });
    }

    public Observable<DUReplyResult> uploadMaterialApply(List<DUMaterialApply> applies, int userId) {
        return mHttpHelper.uploadMaterialApply(applies, userId)
                .map(new Func1<DUEntitiesResult<DUReplyResult>, DUReplyResult>() {
                    @Override
                    public DUReplyResult call(DUEntitiesResult<DUReplyResult> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData().get(0);
                    }
                });
    }

    public Observable<DUReplyResult> uploadMaterialConfirm(List<DUMaterialVerify> verifies, int userId) {
        return mHttpHelper.uploadMaterialConfirm(verifies, userId)
                .map(new Func1<DUEntitiesResult<DUReplyResult>, DUReplyResult>() {
                    @Override
                    public DUReplyResult call(DUEntitiesResult<DUReplyResult> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData().get(0);
                    }
                });
    }

    public Observable<List<DUMaterialApply>> searchMaterialApply(int userId, long startTime, long endTime) {
        return mHttpHelper.searchApply(userId, startTime, endTime)
                .map(new Func1<DUEntitiesResult<DUMaterialApply>, List<DUMaterialApply>>() {
                    @Override
                    public List<DUMaterialApply> call(DUEntitiesResult<DUMaterialApply> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData();
                    }
                })
                .map(new Func1<List<DUMaterialApply>, List<DUMaterialApply>>() {
                    @Override
                    public List<DUMaterialApply> call(List<DUMaterialApply> duMaterialApplies) {
                        for(DUMaterialApply apply : duMaterialApplies){
                            apply.setDuMaterial(getMaterial(apply.getMaterialId()));
                        }
                        return duMaterialApplies;
                    }
                });
    }

    public Observable<List<DUMaterialVerify>> searchMaterialConfirm(int userId, long startTime, long endTime) {
        return mHttpHelper.searchConfirm(userId, startTime, endTime)
                .map(new Func1<DUEntitiesResult<DUMaterialVerify>, List<DUMaterialVerify>>() {
                    @Override
                    public List<DUMaterialVerify> call(DUEntitiesResult<DUMaterialVerify> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        return result.getData();
                    }
                })
                .map(new Func1<List<DUMaterialVerify>, List<DUMaterialVerify>>() {
                    @Override
                    public List<DUMaterialVerify> call(List<DUMaterialVerify> duMaterialVerifies) {
                        for (DUMaterialVerify verify : duMaterialVerifies){
                            verify.setDuMaterial(getMaterial(verify.getMaterialId()));
                        }
                        return duMaterialVerifies;
                    }
                });
    }

    public Observable<Boolean> getAcceptTimeLimit(){
        return mHttpHelper.getAcceptTimeLimit()
                .map(new Func1<DUEntityResult<DUAcceptTimeLimit>, Boolean>() {
                    @Override
                    public Boolean call(DUEntityResult<DUAcceptTimeLimit> result) {
                        if (result.getCode() != DUEntitiesResult.CODE_0 || result.getStatusCode() != DUEntitiesResult.STATUS_CODE_200) {
                            throw new NullPointerException(String.format("code:%d statusCode:%d", result.getCode(), result.getStatusCode()));
                        }
                        DUAcceptTimeLimit timeLimit = result.getData();
                        return mConfigHelper.saveAcceptTimeLimit(timeLimit.getStrengthStepTwo(), timeLimit.getStrengthStepTwoToThree(),
                                timeLimit.getStrengthStepThree(), timeLimit.getAirtightStepTwo(), timeLimit.getAirtightStepTwoToThree(), timeLimit.getAirtightStepThree());
                    }
                });
    }

    /* -----------------------------FileHelper Operate ----------------------------------------------*/

    /**
     * 压缩照片和添加水印
     *
     * @return
     */
    public Observable<Boolean> compressImageAndAddStamp(String fileName, String filePath) {
        return mFileHelper.compressImageAndAddStamp(fileName, filePath);
    }

    /* -----------------------------DbHelper Operate ----------------------------------------------*/


    public Observable<DUProject> queryProjectByNumber(String constructionTeam, String projectNumber) {
        return mDbHelper.queryProject(constructionTeam, projectNumber)
                .map(new Func1<Project, DUProject>() {
                    @Override
                    public DUProject call(Project project) {
                        return OtoO.toDUProject(project);
                    }
                });
    }

    public Observable<Boolean> saveOperate(DUProject project, DUOperate
            operate, List<DUMultiMedia> medias) {
        return mDbHelper.saveOperate(OtoO.toProject(project), OtoO.toOperate(operate), OtoO.toMultiMedias(medias));
    }

    public Observable<List<DUOperate>> getOperates(String userName, int upload){
        return mDbHelper.queryOperatesByUpload(userName, upload)
                .map(new Func1<List<Operate>, List<DUOperate>>() {
                    @Override
                    public List<DUOperate> call(List<Operate> operates) {
                        if (operates == null) {
                            throw new NullPointerException("query return operates is null");
                        }
                        return OtoO.toDUOperates(operates);
                    }
                });
    }

    public Observable<Iterable<Operate>> updateOperates(List<DUOperate> duOperates){
        return mDbHelper.updateOperates(OtoO.toOperates(duOperates));
    }

    public Observable<List<DUProject>> saveProjects(final List<DUProject> duProjects) {
        return mDbHelper.insertProjects(OtoO.toProjects(duProjects))
                .map(new Func1<Iterable<Project>, List<DUProject>>() {
                    @Override
                    public List<DUProject> call(Iterable<Project> projects) {
                        if (projects == null) {
                            throw new NullPointerException("insert return projects is null");
                        }
                        return duProjects;
                    }
                });
    }

    @Deprecated
    public Observable<List<DUProject>> saveProjects(final List<DUProject> duProjects, final List<Integer> projectStates) {
        return mDbHelper.insertProjects(OtoO.toProjects(duProjects))
                .map(new Func1<Iterable<Project>, List<DUProject>>() {
                    @Override
                    public List<DUProject> call(Iterable<Project> projects) {
                        if (projects == null) {
                            throw new NullPointerException("insert return projects is null");
                        }
                        //过滤
                        List<DUProject> resultProjects = new ArrayList<>();
                        for (DUProject duProject : duProjects) {
                            for (int projectState : projectStates)
                                if (duProject.getState() == projectState)
                                    resultProjects.add(duProject);
                        }
                        return resultProjects;
                    }
                });
    }

    public Observable<DUProject> saveProject(final DUProject duProject) {
        return mDbHelper.insertProject(OtoO.toProject(duProject))
                .map(new Func1<Project, DUProject>() {
                    @Override
                    public DUProject call(Project project) {
                        if (project == null) {
                            throw new NullPointerException("saveProject return project is null");
                        }
                        return duProject;
                    }
                });
    }

    public Observable<List<DUBudget>> saveProjectBudgets(final List<DUBudget> duBudgets) {
        return mDbHelper.insertBudgets(OtoO.toBudgets(duBudgets))
                .map(new Func1<Iterable<Budget>, List<DUBudget>>() {
                    @Override
                    public List<DUBudget> call(Iterable<Budget> budgets) {
                        if (budgets == null) {
                            throw new NullPointerException("insert return violations is null");
                        }
                        return duBudgets;
                    }
                });
    }

    public Observable<List<DUBudget>> getProjectBudgets(long projectId) {
        return mDbHelper.queryProjectBudgets(projectId)
                .map(new Func1<List<Budget>, List<DUBudget>>() {
                    @Override
                    public List<DUBudget> call(List<Budget> budgets) {
                        return OtoO.toDUBudgets(budgets);
                    }
                });
    }

    public Observable<List<DUBudget>> getProjectBudgets(long projectId, int state) {
        return mDbHelper.queryProjectBudgets(projectId, state)
                .map(new Func1<List<Budget>, List<DUBudget>>() {
                    @Override
                    public List<DUBudget> call(List<Budget> budgets) {
                        return OtoO.toDUBudgets(budgets);
                    }
                });
    }

    public Observable<List<DUProjectAccept>> getProjectAccepts(String userName, int upload) {
        return mDbHelper.queryProjectAcceptsByUpload(userName, upload)
                .map(new Func1<List<ProjectAccept>, List<DUProjectAccept>>() {
                    @Override
                    public List<DUProjectAccept> call(List<ProjectAccept> projectAccepts) {
                        if (projectAccepts == null) {
                            throw new NullPointerException("query return projectAccepts is null");
                        }
                        return OtoO.toDUProjectAccepts(projectAccepts);
                    }
                });
    }

    public Observable<Iterable<ProjectAccept>> updateProjectAccepts(List<DUProjectAccept> duProjectAccepts){
        return mDbHelper.updateProjectAccepts(OtoO.toProjectAccepts(duProjectAccepts));
    }

    public Observable<List<DUBranchAccept>> getBranchAccepts(String userName, int upload) {
        return mDbHelper.queryBranchAccepts(userName, upload)
                .map(new Func1<List<BranchAccept>, List<DUBranchAccept>>() {
                    @Override
                    public List<DUBranchAccept> call(List<BranchAccept> branchAccepts) {
                        if (branchAccepts == null) {
                            throw new NullPointerException("query return branchAccepts is null");
                        }
                        return OtoO.toDUBranchAccepts(branchAccepts);
                    }
                });
    }

    public Observable<DUBranchAccept> getBranchAccept(int upload, String userName, long projectId, long budgetId, int acceptType) {
        return mDbHelper.queryBranchAccept(upload, userName, projectId, budgetId, acceptType)
                .map(new Func1<BranchAccept, DUBranchAccept>() {
                    @Override
                    public DUBranchAccept call(BranchAccept branchAccept) {
                        return OtoO.toDUBranchAccept(branchAccept);
                    }
                });
    }

    public Observable<Iterable<BranchAccept>> updateBranchAccepts(List<DUBranchAccept> duBranchAccepts){
        return mDbHelper.updateBranchAccepts(OtoO.toBranchAccepts(duBranchAccepts));
    }

    public Observable<Boolean> saveBranchAccept(DUBudget duBudget, DUBranchAccept duBranchAccept,
                                                List<DUMultiMedia> duMultiMedias) {
        return mDbHelper.saveBranchAccept(OtoO.toBudget(duBudget), OtoO.toBranchAccept(duBranchAccept), OtoO.toMultiMedias(duMultiMedias));
    }

    public Observable<Boolean> saveBranchAccept(DUBudget duBudget, DUBranchAccept duBranchAccept) {
        return mDbHelper.saveBranchAccept(OtoO.toBudget(duBudget), OtoO.toBranchAccept(duBranchAccept));
    }

    public Observable<Map<String, Long>> saveBranchAccept(DUBranchAccept duBranchAccept, DUMultiMedia duMultiMedia) {
        return mDbHelper.saveBranchAccept(OtoO.toBranchAccept(duBranchAccept), OtoO.toMultiMedia(duMultiMedia));
    }

    public Observable<Boolean> saveProjectAccept(DUBudget duBudget, DUProjectAccept duProjectAccept,
                                                 List<DUMultiMedia> duMultiMedias) {
        return mDbHelper.saveProjectAccept(OtoO.toBudget(duBudget), OtoO.toProjectAccept(duProjectAccept), OtoO.toMultiMedias(duMultiMedias));
    }

    public Observable<Boolean> saveConstructionAccept(DUBudget duBudget, DUConstructionAccept duConstructionAccept,
                                                      List<DUMultiMedia> duMultiMedias) {
        return mDbHelper.saveConstructionAccept(OtoO.toBudget(duBudget), OtoO.toConstructionAccept(duConstructionAccept), OtoO.toMultiMedias(duMultiMedias));
    }

    public Observable<List<DUConstructionAccept>> getConstructionAccepts(String userName, int upload){
        return mDbHelper.queryConstructionAccepts(userName, upload)
                .map(new Func1<List<ConstructionAccept>, List<DUConstructionAccept>>() {
                    @Override
                    public List<DUConstructionAccept> call(List<ConstructionAccept> constructionAccepts) {
                        if(constructionAccepts == null){
                            throw new NullPointerException("query return constructionAccepts is null");
                        }
                        return OtoO.toDuConstructionAccepts(constructionAccepts);
                    }
                });
    }

    public Observable<Iterable<ConstructionAccept>> updateConstructionAccepts(List<DUConstructionAccept> duConstructionAccepts){
        return mDbHelper.updateConstructionAccepts(OtoO.toConstructionAccepts(duConstructionAccepts));
    }

    public Observable<Boolean> deleteBranchAccept(DUBranchAccept duBranchAccept, List<DUMultiMedia> duMultiMedias) {
        return mDbHelper.deleteBranchAccept(OtoO.toBranchAccept(duBranchAccept), OtoO.toMultiMedias(duMultiMedias));
    }

    public Observable<List<DUProject>> getProjects(String constructionTeam, List<Integer> projectState, int index, int offset) {
        return mDbHelper.queryProjects(constructionTeam, projectState, index, offset)
                .map(new Func1<List<Project>, List<DUProject>>() {
                    @Override
                    public List<DUProject> call(List<Project> projects) {
                        return OtoO.toDUProjects(projects);
                    }
                });
    }

    public Observable<List<DUViolation>> saveViolations(
            final List<DUViolation> duViolations) {
        return mDbHelper.insertViolations(OtoO.toViolations(duViolations))
                .map(new Func1<Iterable<Violation>, List<DUViolation>>() {
                    @Override
                    public List<DUViolation> call(Iterable<Violation> violations) {
                        if (violations == null) {
                            throw new NullPointerException("insert return violations is null");
                        }
                        return duViolations;
                    }
                });
    }

    public Observable<List<DUViolation>> getAllViolationTypes() {
        return mDbHelper.queryAllViolationType()
                .map(new Func1<List<Violation>, List<DUViolation>>() {
                    @Override
                    public List<DUViolation> call(List<Violation> violations) {
                        if (violations == null) {
                            throw new NullPointerException("query return violations is null");
                        }
                        return OtoO.toDUViolations(violations);
                    }
                });
    }

    public Observable<List<DUViolation>> getViolationByType(String violationType) {
        return mDbHelper.queryViolationByType(violationType)
                .map(new Func1<List<Violation>, List<DUViolation>>() {
                    @Override
                    public List<DUViolation> call(List<Violation> violations) {
                        if (violations == null) {
                            throw new NullPointerException("qurey return violations is null");
                        }
                        return OtoO.toDUViolations(violations);
                    }
                });
    }

    public Observable<List<DUMultiMedia>> getMultiMedias(long relateId, int relateType) {
        return mDbHelper.queryMultiMedias(relateId, relateType)
                .map(new Func1<List<MultiMedia>, List<DUMultiMedia>>() {
                    @Override
                    public List<DUMultiMedia> call(List<MultiMedia> multiMedias) {
                        return OtoO.toDUMultiMedias(multiMedias);
                    }
                });
    }

    public Observable<List<String>> getMultiMediaNames(int[] uploads) {
        return mDbHelper.queryMultiMedias(uploads)
                .map(new Func1<List<MultiMedia>, List<String>>() {
                    @Override
                    public List<String> call(List<MultiMedia> multiMedias) {
                        if(multiMedias == null || multiMedias.size() == 0){
                            return null;
                        }
                        List<String> mediaNames = new ArrayList<String>();
                        for(MultiMedia media : multiMedias){
                            mediaNames.add(media.getFILE_NAME());
                        }
                        return mediaNames;
                    }
                });
    }

    public Observable<Boolean> saveWords(List<DUWord> duWords) {
        return mDbHelper.insertWords(OtoO.toWords(duWords))
                .map(new Func1<Iterable<Word>, Boolean>() {
                    @Override
                    public Boolean call(Iterable<Word> words) {
                        if (words == null) {
                            throw new NullPointerException("insert return words is null");
                        }
                        return true;
                    }
                });
    }

    public Observable<Boolean> saveMaterials(List<DUMaterial> duMaterials) {
        return mDbHelper.insertMaterials(OtoO.toMaterials(duMaterials))
                .map(new Func1<Iterable<Material>, Boolean>() {
                    @Override
                    public Boolean call(Iterable<Material> materials) {
                        if (materials == null) {
                            throw new NullPointerException("insert return materials is null");
                        }
                        return true;
                    }
                });
    }

    public Observable<List<DUAddress>> saveAddresses(final List<DUAddress> duAddresses) {
        return mDbHelper.insertAddresses(OtoO.toAddresses(duAddresses))
                .map(new Func1<Iterable<Address>, List<DUAddress>>() {
                    @Override
                    public List<DUAddress> call(Iterable<Address> addresses) {
                        if (addresses == null) {
                            throw new NullPointerException("insert return addresses is null");
                        }
                        return duAddresses;
                    }
                });
    }

    public Observable<Boolean> saveAddress(DUAddress duAddress) {
        return mDbHelper.insertAddress(OtoO.toAddress(duAddress))
                .map(new Func1<Address, Boolean>() {
                    @Override
                    public Boolean call(Address address) {
                        if (address == null) {
                            throw new NullPointerException("insert return address is null");
                        }
                        return true;
                    }
                });
    }

    public Observable<List<DUMaterial>> getAllMaterialCategory() {
        return mDbHelper.queryAllMaterialCategory()
                .map(new Func1<List<Material>, List<DUMaterial>>() {
                    @Override
                    public List<DUMaterial> call(List<Material> materials) {
                        return OtoO.toDUMaterials(materials);
                    }
                });
    }

    public Observable<DUAddress> getDefaultDUAddress(int userId) {
        return mDbHelper.getDefaultDUAddress(userId)
                .map(new Func1<Address, DUAddress>() {
                    @Override
                    public DUAddress call(Address address) {
                        return OtoO.toDUAddress(address);
                    }
                });
    }

    public Observable<List<DUAddress>> getAddresses(int userId) {
        return mDbHelper.getAddresses(userId)
                .map(new Func1<List<Address>, List<DUAddress>>() {
                    @Override
                    public List<DUAddress> call(List<Address> addresses) {
                        return OtoO.toDUAddresses(addresses);
                    }
                });
    }

    public Observable<Integer> getAddressNoUploadCount(int userId) {
        return mDbHelper.getAddressesNoUpload(userId)
                .map(new Func1<List<Address>, Integer>() {
                    @Override
                    public Integer call(List<Address> addresses) {
                        return addresses.size();
                    }
                });
    }

    public DUMaterial getMaterial(long materialId){
        return OtoO.toDUMaterial(mDbHelper.queryMaterial(materialId));
    }

    public Observable<List<DUMaterial>> getMaterialNameByCategory(String materialCategory) {
        return mDbHelper.queryMaterialNameByCategory(materialCategory)
                .map(new Func1<List<Material>, List<DUMaterial>>() {
                    @Override
                    public List<DUMaterial> call(List<Material> materials) {
                        return OtoO.toDUMaterials(materials);
                    }
                });
    }

    public Observable<List<DUMaterial>> getMaterialFormatByName(String materialCategory, String materialName) {
        return mDbHelper.queryMaterialFormatByName(materialCategory, materialName)
                .map(new Func1<List<Material>, List<DUMaterial>>() {
                    @Override
                    public List<DUMaterial> call(List<Material> materials) {
                        return OtoO.toDUMaterials(materials);
                    }
                });
    }

    public Observable<String> getWordName(String group, String value) {
        return mDbHelper.queryWordName(group, value)
                .map(new Func1<Word, String>() {
                    @Override
                    public String call(Word word) {
                        return word.getNAME();
                    }
                });
    }

    public Observable<List<DUWord>> getWords(String group) {
        return mDbHelper.queryWords(group)
                .map(new Func1<List<Word>, List<DUWord>>() {
                    @Override
                    public List<DUWord> call(List<Word> words) {
                        return OtoO.toDUWords(words);
                    }
                });
    }

    public Observable<List<DUWord>> getWords(List<String> groups) {
        return Observable.from(groups)
                .concatMap(new Func1<String, Observable<List<Word>>>() {
                    @Override
                    public Observable<List<Word>> call(String group) {
                        return mDbHelper.queryWords(group);
                    }
                }).map(new Func1<List<Word>, List<DUWord>>() {
                    @Override
                    public List<DUWord> call(List<Word> words) {
                        return OtoO.toDUWords(words);
                    }
                });
    }

    public Observable<Boolean> savePatrol(DUPatrol duPatrol, List<DUMultiMedia> medias) {
        return mDbHelper.savePatrol(OtoO.toPatrol(duPatrol), OtoO.toMultiMedias(medias));
    }

    public Observable<List<DUPatrol>> getPatrols(String userName, int type, int upload){
        return mDbHelper.queryPatrolsByUpload(userName, type, upload)
                .map(new Func1<List<Patrol>, List<DUPatrol>>() {
                    @Override
                    public List<DUPatrol> call(List<Patrol> patrols) {
                        return OtoO.toDUPatrols(patrols);
                    }
                });
    }

    public Observable<Iterable<Patrol>> updatePatrols(List<DUPatrol> duPatrols){
        return mDbHelper.updatePatrols(OtoO.toPatrols(duPatrols));
    }

    public Observable<Integer> getOperateCount(String userName, int upload) {
        return mDbHelper.queryOperatesByUpload(userName, upload)
                .map(new Func1<List<Operate>, Integer>() {
                    @Override
                    public Integer call(List<Operate> operates) {
                        return operates.size();
                    }
                });
    }

    public Observable<Integer> getPatrolCount(String userName, int type, int upload) {
        return mDbHelper.queryPatrolsByUpload(userName, type, upload)
                .map(new Func1<List<Patrol>, Integer>() {
                    @Override
                    public Integer call(List<Patrol> patrols) {
                        return patrols.size();
                    }
                });
    }

    public Observable<Integer> getAcceptCount(String userName, int upload) {
        Observable<Integer> constructionAcceptObservable = getConstructionAccepts(userName, upload)
                .map(new Func1<List<DUConstructionAccept>, Integer>() {
                    @Override
                    public Integer call(List<DUConstructionAccept> duConstructionAccept) {
                        return duConstructionAccept.size();
                    }
                });
        Observable<Integer> branchAcceptObservable = getBranchAccepts(userName, upload)
                .map(new Func1<List<DUBranchAccept>, Integer>() {
                    @Override
                    public Integer call(List<DUBranchAccept> duBranchAccepts) {
                        return duBranchAccepts.size();
                    }
                });
        Observable<Integer> projectAcceptObservable = getProjectAccepts(userName, upload)
                .map(new Func1<List<DUProjectAccept>, Integer>() {
                    @Override
                    public Integer call(List<DUProjectAccept> duProjectAccepts) {
                        return duProjectAccepts.size();
                    }
                });
        return Observable.merge(constructionAcceptObservable, branchAcceptObservable, projectAcceptObservable)
                .reduce(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer + integer2;
                    }
                });
    }

    public void resetRestfulApiService() {
        mHttpHelper.resetRestfulApiService();
    }

}
