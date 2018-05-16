package com.sh3h.localprovider;

import com.sh3h.localprovider.greendaoDao.AddressDao;
import com.sh3h.localprovider.greendaoDao.BranchAcceptDao;
import com.sh3h.localprovider.greendaoDao.BudgetDao;
import com.sh3h.localprovider.greendaoDao.ConstructionAcceptDao;
import com.sh3h.localprovider.greendaoDao.DaoSession;
import com.sh3h.localprovider.greendaoDao.MaterialDao;
import com.sh3h.localprovider.greendaoDao.MultiMediaDao;
import com.sh3h.localprovider.greendaoDao.OperateDao;
import com.sh3h.localprovider.greendaoDao.PatrolDao;
import com.sh3h.localprovider.greendaoDao.ProjectAcceptDao;
import com.sh3h.localprovider.greendaoDao.ProjectDao;
import com.sh3h.localprovider.greendaoDao.ViolationDao;
import com.sh3h.localprovider.greendaoDao.WordDao;
import com.sh3h.localprovider.greendaoEntity.Address;
import com.sh3h.localprovider.greendaoEntity.BranchAccept;
import com.sh3h.localprovider.greendaoEntity.Budget;
import com.sh3h.localprovider.greendaoEntity.ConstructionAccept;
import com.sh3h.localprovider.greendaoEntity.Material;
import com.sh3h.localprovider.greendaoEntity.MaterialApply;
import com.sh3h.localprovider.greendaoEntity.MaterialVerify;
import com.sh3h.localprovider.greendaoEntity.MultiMedia;
import com.sh3h.localprovider.greendaoEntity.Operate;
import com.sh3h.localprovider.greendaoEntity.Patrol;
import com.sh3h.localprovider.greendaoEntity.Project;
import com.sh3h.localprovider.greendaoEntity.ProjectAccept;
import com.sh3h.localprovider.greendaoEntity.Violation;
import com.sh3h.localprovider.greendaoEntity.Word;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by dengzhimin on 2017/3/29.
 */
public class DataProviderImpl implements IDataProvider {

    private DaoSession daoSession;

    public DataProviderImpl(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Override
    public Observable<Project> insertProject(Project project) {
        if (project == null) {
            return Observable.error(new Throwable("insertProject : project is null"));
        }
        return daoSession.getProjectDao().rx().insertOrReplace(project);
    }

    @Override
    public Observable<Iterable<Project>> insertProjects(List<Project> projects) {
        if (projects == null || projects.size() == 0) {
            return Observable.error(new Throwable("insertProjects : projects is null"));
        }
        return daoSession.getProjectDao().rx().insertOrReplaceInTx(projects);
    }

    @Override
    public Observable<Void> deleteProject(Project project) {
        if (project == null) {
            return Observable.error(new Throwable("deleteProject : project is null"));
        }
        return daoSession.getProjectDao().rx().delete(project);
    }

    @Override
    public Observable<Void> deleteProjects(List<Project> projects) {
        if (projects == null || projects.size() == 0) {
            return Observable.error(new Throwable("deleteProjects : projects is null"));
        }
        return daoSession.getProjectDao().rx().deleteInTx(projects);
    }

    @Override
    public Observable<Project> updateProject(Project project) {
        if (project == null) {
            return Observable.error(new Throwable("updateProject : project is null"));
        }
        return daoSession.getProjectDao().rx().update(project);
    }

    @Override
    public Observable<Project> updateProject(long projectID, int state) {
        Project project = daoSession.getProjectDao().load(projectID);
        if (project == null) {
            return Observable.error(new Throwable("updateProject : not find project by key projectID"));
        }
        project.setSTATE(state);
        return daoSession.getProjectDao().rx().update(project);
    }

    @Override
    public Observable<Project> updateProject(long projectID, long time, int state) {
        Project project = daoSession.getProjectDao().load(projectID);
        if (project == null) {
            return Observable.error(new Throwable("updateProject : not find project by key projectID"));
        }
        project.setSTATE(state);
        if (state == 4) {//开工
            project.setSTART_TIME(time);
        } else if (state == 7) {//完工
            project.setEND_TIME(time);
        }
        return daoSession.getProjectDao().rx().update(project);
    }

    @Override
    public Observable<Project> queryProject(String constructionTeam, String projectNumber) {
        if (projectNumber == null || projectNumber.isEmpty()) {
            return Observable.error(new Throwable("queryProject : projectNumber is null or empty"));
        }
        if(constructionTeam == null || constructionTeam.isEmpty()){
            return daoSession.getProjectDao().queryBuilder()
                    .where(ProjectDao.Properties.PROJECT_NUMBER.eq(projectNumber))
                    .rx().unique();
        }
        return daoSession.getProjectDao().queryBuilder()
                .where(ProjectDao.Properties.PROJECT_NUMBER.eq(projectNumber), ProjectDao.Properties.CONSTRUCTION_TEAM.eq(constructionTeam))
                .rx().unique();
    }

    @Override
    public Observable<List<Project>> queryProjects(final String constructionTeam) {
        if (constructionTeam == null || constructionTeam.isEmpty()) {
            return Observable.error(new Throwable("queryProjects : constructionTeam is null or empty"));
        }
        return daoSession.getProjectDao().queryBuilder()
                .where(ProjectDao.Properties.CONSTRUCTION_TEAM.eq(constructionTeam)).rx().list();
    }

    @Override
    public Observable<List<Project>> queryProjects(String constructionTeam, List<Integer> projectState, int index, int offset) {
        if (constructionTeam == null || constructionTeam.isEmpty()) {//保障中心
            return daoSession.getProjectDao().queryBuilder()
                    .where(ProjectDao.Properties.STATE.in(projectState))
                    .limit(offset).offset(index).rx().list();
        }
        //施工队
        return daoSession.getProjectDao().queryBuilder()
                .where(ProjectDao.Properties.CONSTRUCTION_TEAM.eq(constructionTeam), ProjectDao.Properties.STATE.in(projectState))
                .limit(offset).offset(index).rx().list();
    }

    @Override
    public Observable<List<Project>> queryAllProject() {
        return daoSession.getProjectDao().queryBuilder().rx().list();
    }

    @Override
    public Observable<Operate> insertOperate(Operate operate) {
        if (operate == null) {
            return Observable.error(new Throwable("insertOperate : operate is null"));
        }
        return daoSession.getOperateDao().rx().insertOrReplace(operate);
    }

    @Override
    public Observable<Void> deleteOperate(Operate operate) {
        if (operate == null) {
            return Observable.error(new Throwable("deleteOperate : operate is null"));
        }
        return daoSession.getOperateDao().rx().delete(operate);
    }

    @Override
    public Observable<Void> deleteOperates(List<Operate> operates) {
        if (operates == null || operates.size() == 0) {
            return Observable.error(new Throwable("deleteOperates : operates is null"));
        }
        return daoSession.getOperateDao().rx().deleteInTx(operates);
    }

    @Override
    public Observable<Operate> updateOperate(long operateID, long serverID) {
        Operate operate = daoSession.getOperateDao().load(operateID);
        if (operate == null) {
            return Observable.error(new Throwable("updateOperate : not find operate by key operateID"));
        }
        operate.setSERVER_ID(serverID);
        return daoSession.getOperateDao().rx().update(operate);
    }

    @Override
    public Observable<Iterable<Operate>> updateOperates(List<Operate> operates) {
        return daoSession.getOperateDao().rx().updateInTx(operates);
    }

    @Override
    public Observable<List<Operate>> queryOperatesByUpload(String userName, int upload) {
        return daoSession.getOperateDao().queryBuilder().where(OperateDao.Properties.OPERATOR.eq(userName),
                OperateDao.Properties.UPLOAD.eq(upload)).rx().list();
    }

    @Override
    public Observable<Patrol> insertPatrol(Patrol patrol) {
        if (patrol == null) {
            Observable.error(new Throwable("insertPatrol : patrol is null"));
        }
        return daoSession.getPatrolDao().rx().insertOrReplace(patrol);
    }

    @Override
    public Observable<Patrol> updatePatrol(long patrolID, long serverID) {
        Patrol patrol = daoSession.getPatrolDao().load(patrolID);
        if (patrol == null) {
            return Observable.error(new Throwable("updatePatrol : not find patrol by key patrolID"));
        }
        patrol.setSERVER_ID(serverID);
        return daoSession.getPatrolDao().rx().update(patrol);
    }

    @Override
    public Observable<List<Patrol>> queryPatrolsByUpload(String userName, int type, int upload) {
        return daoSession.getPatrolDao().queryBuilder().where(PatrolDao.Properties.OPERATOR.eq(userName),
                PatrolDao.Properties.PATROL_TYPE.eq(type), PatrolDao.Properties.UPLOAD.eq(upload)).rx().list();
    }

    @Override
    public Observable<Iterable<Patrol>> updatePatrols(List<Patrol> patrols) {
        return daoSession.getPatrolDao().rx().updateInTx(patrols);
    }

    @Override
    public Observable<Iterable<Violation>> insertViolations(List<Violation> violations) {
        if (violations == null || violations.size() == 0) {
            return Observable.error(new Throwable("insertViolations : violations is null"));
        }
        return daoSession.getViolationDao().rx().insertOrReplaceInTx(violations);
    }

    @Override
    public Observable<List<Violation>> queryAllViolationType() {
        return daoSession.getViolationDao().queryBuilder()
                .where(new WhereCondition.StringCondition("1 group by " + ViolationDao.Properties.VIOLATION_TYPE.columnName)).rx().list();
    }

    @Override
    public Observable<List<Violation>> queryViolationByType(String violationType) {
        if (violationType == null || violationType.isEmpty()) {
            return Observable.error(new Throwable("queryViolationByType : violationType is null or empty"));
        }
        return daoSession.getViolationDao().queryBuilder()
                .where(ViolationDao.Properties.VIOLATION_TYPE.eq(violationType)).rx().list();
    }

    @Override
    public Observable<Iterable<Material>> insertMaterials(List<Material> materials) {
        if (materials == null || materials.size() == 0) {
            return Observable.error(new Throwable("insertMaterials : materials is null"));
        }
        return daoSession.getMaterialDao().rx().insertOrReplaceInTx(materials);
    }

    @Override
    public Material queryMaterial(long materialId) {
        return daoSession.getMaterialDao().load(materialId);
    }

    @Override
    public Observable<List<Material>> queryAllMaterialCategory() {
        return daoSession.getMaterialDao().queryBuilder()
                .where(new WhereCondition.StringCondition("1 group by " + MaterialDao.Properties.MATERIAL_CATEGORY.columnName)).rx().list();
    }

    @Override
    public Observable<Address> getDefaultDUAddress(int userId) {
        return daoSession.getAddressDao().queryBuilder().where(AddressDao.Properties.USER_ID.eq(userId),
                AddressDao.Properties.DEFAULT.eq(1)).rx().unique();
    }

    @Override
    public Observable<List<Address>> getAddresses(int userId) {
        return daoSession.getAddressDao().queryBuilder().where(AddressDao.Properties.USER_ID.eq(userId)).rx().list();
    }

    @Override
    public Observable<List<Address>> getAddressesNoUpload(int userId) {
        return daoSession.getAddressDao().queryBuilder()
                .where(AddressDao.Properties.USER_ID.eq(userId), AddressDao.Properties.SERVER_ID.isNull())
                .rx().list();
    }

    @Override
    public Observable<List<Material>> queryMaterialNameByCategory(String materialCategory) {
        return daoSession.getMaterialDao().queryBuilder().where(MaterialDao.Properties.MATERIAL_CATEGORY.eq(materialCategory))
                .where(new WhereCondition.StringCondition("1 group by " + MaterialDao.Properties.MATERIAL_NAME.columnName)).rx().list();
    }

    @Override
    public Observable<List<Material>> queryMaterialFormatByName(String materialCategory, String materialName) {
        return daoSession.getMaterialDao().queryBuilder().where(MaterialDao.Properties.MATERIAL_NAME.eq(materialName),
                MaterialDao.Properties.MATERIAL_CATEGORY.eq(materialCategory))
                .where(new WhereCondition.StringCondition("1 group by " + MaterialDao.Properties.MATERIAL_FORMAT.columnName)).rx().list();
    }

    @Override
    public Observable<MaterialApply> insertMaterialApply(MaterialApply materialApply) {
        if (materialApply == null) {
            return Observable.error(new Throwable("insertMaterialApply : materialApply is null"));
        }
        return daoSession.getMaterialApplyDao().rx().insertOrReplace(materialApply);
    }

    @Override
    public Observable<MaterialApply> updateMaterialApply(long applyID, long serverID) {
        MaterialApply materialApply = daoSession.getMaterialApplyDao().load(applyID);
        if (materialApply == null) {
            return Observable.error(new Throwable("updateMaterialApply : not find materialApply by key applyID"));
        }
        materialApply.setSERVER_ID(serverID);
        return daoSession.getMaterialApplyDao().rx().update(materialApply);
    }

    @Override
    public Observable<MaterialVerify> insertMaterialVerify(MaterialVerify materialVerify) {
        if (materialVerify == null) {
            return Observable.error(new Throwable("insertMaterialVerify : materialVerify is null"));
        }
        return daoSession.getMaterialVerifyDao().rx().insertOrReplace(materialVerify);
    }

    @Override
    public Observable<MaterialVerify> updateMaterialVerify(long verifyID, long serverID) {
        MaterialVerify materialVerify = daoSession.getMaterialVerifyDao().load(verifyID);
        if (materialVerify == null) {
            return Observable.error(new Throwable("updateMaterialVerify : not find materialVerify by key verifyID"));
        }
        materialVerify.setSERVER_ID(serverID);
        return daoSession.getMaterialVerifyDao().rx().update(materialVerify);
    }

    @Override
    public Observable<Iterable<Budget>> insertBudgets(List<Budget> budgets) {
        if (budgets == null || budgets.size() == 0) {
            return Observable.error(new Throwable("insertBudgets : budgets is null"));
        }
        return daoSession.getBudgetDao().rx().insertOrReplaceInTx(budgets);
    }

    @Override
    public Observable<Budget> updateBudget(long budgetID, int state) {
        Budget budget = daoSession.getBudgetDao().load(budgetID);
        if (budget == null) {
            return Observable.error(new Throwable("updateBudget : not find budget by key budgetID"));
        }
        budget.setBUDGET_STATE(state);
        return daoSession.getBudgetDao().rx().update(budget);
    }

    @Override
    public Observable<Iterable<Budget>> updateBudgets(List<Budget> budgets) {
        return daoSession.getBudgetDao().rx().updateInTx(budgets);
    }

    @Override
    public Observable<List<Budget>> queryProjectBudgets(long projectId) {
        return daoSession.getBudgetDao().queryBuilder().where(BudgetDao.Properties.PROJECT_ID.eq(projectId)).rx().list();
    }

    @Override
    public Observable<List<Budget>> queryProjectBudgets(long projectId, int state) {
        return daoSession.getBudgetDao().queryBuilder().where(BudgetDao.Properties.PROJECT_ID.eq(projectId)
                , BudgetDao.Properties.BUDGET_STATE.eq(state)).rx().list();
    }

    @Override
    public long getBudgetCount(long projectId, int budgetState) {
        return daoSession.getBudgetDao().queryBuilder()
                .where(BudgetDao.Properties.PROJECT_ID.eq(projectId), BudgetDao.Properties.BUDGET_STATE.ge(budgetState))
                .count();
    }

    @Override
    public long getBudgetTotalCount(long projectId) {
        return daoSession.getBudgetDao().queryBuilder().where(BudgetDao.Properties.PROJECT_ID.eq(projectId)).count();
    }

    @Override
    public Observable<BranchAccept> insertBranchAccept(BranchAccept branchAccept) {
        if (branchAccept == null) {
            return Observable.error(new Throwable("insertBranchAccept : branchAccept is null"));
        }
        return daoSession.getBranchAcceptDao().rx().insertOrReplace(branchAccept);
    }

    @Override
    public Observable<BranchAccept> updateBranchAccept(long acceptID, long serverID) {
        BranchAccept branchAccept = daoSession.getBranchAcceptDao().load(acceptID);
        if (branchAccept == null) {
            return Observable.error(new Throwable("updateBranchAccept : not find branchAccept by key acceptID"));
        }
        branchAccept.setSERVER_ID(serverID);
        return daoSession.getBranchAcceptDao().rx().update(branchAccept);
    }

    @Override
    public Observable<List<BranchAccept>> queryBranchAccepts(String userName, int upload) {
        return daoSession.getBranchAcceptDao().queryBuilder().where(BranchAcceptDao.Properties.OPERATOR.eq(userName),
                BranchAcceptDao.Properties.UPLOAD.eq(upload)).rx().list();
    }

    @Override
    public Observable<BranchAccept> queryBranchAccept(int upload, String userName, long projectId, long budgetId, int acceptType) {
        return daoSession.getBranchAcceptDao().queryBuilder()
                .where(BranchAcceptDao.Properties.UPLOAD.eq(upload))
                .where(BranchAcceptDao.Properties.OPERATOR.eq(userName), BranchAcceptDao.Properties.PROJECT_ID.eq(projectId))
                .where(BranchAcceptDao.Properties.BUDGET_ID.eq(budgetId), BranchAcceptDao.Properties.ACCEPT_TYPE.eq(acceptType))
                .orderDesc(BranchAcceptDao.Properties.OPERATE_TIME).rx().unique();
    }

    @Override
    public Observable<Iterable<BranchAccept>> updateBranchAccepts(List<BranchAccept> branchAccepts) {
        return daoSession.getBranchAcceptDao().rx().updateInTx(branchAccepts);
    }

    @Override
    public Observable<ProjectAccept> insertProjectAccept(ProjectAccept projectAccept) {
        if (projectAccept == null) {
            return Observable.error(new Throwable("insertProjectAccept : projectAccept is null"));
        }
        return daoSession.getProjectAcceptDao().rx().insertOrReplace(projectAccept);
    }

    @Override
    public Observable<ProjectAccept> updateProjectAccept(long acceptID, long serverID) {
        ProjectAccept projectAccept = daoSession.getProjectAcceptDao().load(acceptID);
        if (projectAccept == null) {
            return Observable.error(new Throwable("updateProjectAccept : not find projectAccept by key acceptID"));
        }
        projectAccept.setSERVER_ID(serverID);
        return daoSession.getProjectAcceptDao().rx().update(projectAccept);
    }

    @Override
    public Observable<Iterable<ProjectAccept>> updateProjectAccepts(List<ProjectAccept> projectAccepts) {
        return daoSession.getProjectAcceptDao().rx().updateInTx(projectAccepts);
    }

    @Override
    public Observable<List<ProjectAccept>> queryProjectAcceptsByUpload(String userName, int upload) {
        return daoSession.getProjectAcceptDao().queryBuilder().where(ProjectAcceptDao.Properties.OPERATOR.eq(userName),
                ProjectAcceptDao.Properties.UPLOAD.eq(upload)).rx().list();
    }

    @Override
    public Observable<List<ConstructionAccept>> queryConstructionAccepts(String userName, int upload) {
        return daoSession.getConstructionAcceptDao().queryBuilder().where(ConstructionAcceptDao.Properties.OPERATOR.eq(userName),
                ConstructionAcceptDao.Properties.UPLOAD.eq(upload)).rx().list();
    }

    @Override
    public Observable<Iterable<ConstructionAccept>> updateConstructionAccepts(List<ConstructionAccept> constructionAccepts) {
        return daoSession.getConstructionAcceptDao().rx().updateInTx(constructionAccepts);
    }

    @Override
    public Observable<Address> insertAddress(Address address) {
        if (address == null) {
            return Observable.error(new Throwable("insertAddress : address is null"));
        }
        return daoSession.getAddressDao().rx().insertOrReplace(address);
    }

    @Override
    public Observable<Iterable<Address>> insertAddresses(List<Address> addresses) {
        if (addresses == null || addresses.size() == 0) {
            return Observable.error(new Throwable("insertAddresses : addresses is null"));
        }

        daoSession.getAddressDao().queryBuilder().where(AddressDao.Properties.USER_ID.eq(addresses.get(0).getUSER_ID()))
                .buildDelete().executeDeleteWithoutDetachingEntities();

        for (Address address : addresses) {
            Address currentAddress = queryAddress(address.getSERVER_ID());
            if (currentAddress != null) {
                address.setADDRESS_ID(currentAddress.getADDRESS_ID());
            }
        }
        return daoSession.getAddressDao().rx().insertOrReplaceInTx(addresses);
    }

    @Override
    public Address queryAddress(long serverId) {
        List<Address> addresses = daoSession.getAddressDao().queryBuilder()
                .where(AddressDao.Properties.SERVER_ID.eq(serverId)).build().list();
        if (addresses == null || addresses.size() <= 0) {
            return null;
        }
        return addresses.get(0);
    }

    @Override
    public Observable<Void> deleteAddress(Address address) {
        if (address == null) {
            return Observable.error(new Throwable("deleteAddress : address is null"));
        }
        return daoSession.getAddressDao().rx().delete(address);
    }

    @Override
    public Observable<Address> updateAddress(Address address) {
        if (address == null) {
            return Observable.error(new Throwable("updateAddress : address is null"));
        }
        return daoSession.getAddressDao().rx().update(address);
    }

    @Override
    public Observable<List<Address>> queryAddress() {
        return daoSession.getAddressDao().rx().loadAll();
    }

    @Override
    public Observable<MultiMedia> insertMultiMedia(MultiMedia multiMedia) {
        if (multiMedia == null) {
            return Observable.error(new Throwable("insertMultiMedia : multiMedia is null"));
        }
        return daoSession.getMultiMediaDao().rx().insertOrReplace(multiMedia);
    }

    @Override
    public Observable<Iterable<MultiMedia>> insertMultiMedias(List<MultiMedia> multiMedias) {
        if (multiMedias == null || multiMedias.size() == 0) {
            return Observable.error(new Throwable("insertMultiMedias : multiMedias is null"));
        }
        return daoSession.getMultiMediaDao().rx().insertOrReplaceInTx(multiMedias);
    }

    @Override
    public Observable<List<MultiMedia>> queryMultiMedias(final List<Map<String, Object>> idTypes, final int upload) {
        return Observable.create(new Observable.OnSubscribe<List<MultiMedia>>() {
            @Override
            public void call(Subscriber<? super List<MultiMedia>> subscriber) {
                List<MultiMedia> multiMedias = new ArrayList<MultiMedia>();
                try{
                    for(Map<String, Object> idType : idTypes){
                        List<MultiMedia> medias= daoSession.getMultiMediaDao().queryBuilder()
                                .where(MultiMediaDao.Properties.RELATE_ID.in(idType.get("localId")))
                                .where(MultiMediaDao.Properties.RELATE_TYPE.in(idType.get("relateType")))
                                .where(MultiMediaDao.Properties.UPLOAD.eq(upload)).list();
                        for(MultiMedia media : medias){
                            media.setRELATE_ID((Long) idType.get("serverId"));
                        }
                        multiMedias.addAll(medias);
                    }
                    subscriber.onNext(multiMedias);
                }catch (Exception e){
                    e.printStackTrace();
                    subscriber.onError(e);
                }finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    @Override
    public Observable<List<MultiMedia>> queryMultiMedias(final List<Map<String, Long>> ids, final List<Integer> types, final int upload) {
        return Observable.create(new Observable.OnSubscribe<List<MultiMedia>>() {
            @Override
            public void call(Subscriber<? super List<MultiMedia>> subscriber) {
                try{
                    List<Long> localIds = new ArrayList<Long>();
                    List<Long> serverIds = new ArrayList<Long>();
                    for(Map<String, Long> map : ids){
                        localIds.add(map.get("localId"));
                        serverIds.add(map.get("serverId"));
                    }
                    List<MultiMedia> medias = daoSession.getMultiMediaDao().queryBuilder()
                            .where(MultiMediaDao.Properties.RELATE_ID.in(localIds))
                            .where(MultiMediaDao.Properties.RELATE_TYPE.in(types))
                            .where(MultiMediaDao.Properties.UPLOAD.eq(upload)).list();
                    for(MultiMedia media : medias){
                        for(Integer type : types){
                            if(media.getRELATE_TYPE() == type){
                                for(int i=0; i<localIds.size(); i++){
                                    if(media.getRELATE_ID() == localIds.get(i)){
                                        media.setRELATE_ID(serverIds.get(i));
                                    }
                                }
                            }
                        }
                    }
                    subscriber.onNext(medias);
                }catch (Exception e){
                    e.printStackTrace();
                    subscriber.onError(e);
                }finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    @Override
    public Observable<List<MultiMedia>> queryMultiMedias(long relateId, int relateType) {
        return daoSession.getMultiMediaDao().queryBuilder()
                .where(MultiMediaDao.Properties.RELATE_ID.eq(relateId), MultiMediaDao.Properties.RELATE_TYPE.eq(relateType)).rx().list();
    }

    @Override
    public Observable<List<MultiMedia>> queryMultiMedias(int[] uploads) {
        QueryBuilder<MultiMedia> qb = daoSession.getMultiMediaDao().queryBuilder();
        switch (uploads.length){
            case 1:
                qb.where(MultiMediaDao.Properties.UPLOAD.eq(uploads[0]));
                break;
            case 2:
                qb.whereOr(MultiMediaDao.Properties.UPLOAD.eq(uploads[0]), MultiMediaDao.Properties.UPLOAD.eq(uploads[1]));
                break;
            case 3:
                qb.whereOr(MultiMediaDao.Properties.UPLOAD.eq(uploads[0]), MultiMediaDao.Properties.UPLOAD.eq(uploads[1]),
                        MultiMediaDao.Properties.UPLOAD.eq(uploads[2]));
                break;
            case 4:
                qb.whereOr(MultiMediaDao.Properties.UPLOAD.eq(uploads[0]), MultiMediaDao.Properties.UPLOAD.eq(uploads[1]),
                        MultiMediaDao.Properties.UPLOAD.eq(uploads[2]), MultiMediaDao.Properties.UPLOAD.eq(uploads[3]));
                break;
        }
        return qb.rx().list();
    }

    @Override
    public Observable<MultiMedia> updateMultiMedia(MultiMedia multiMedia) {
        return daoSession.getMultiMediaDao().rx().update(multiMedia);
    }

    @Override
    public Observable<Iterable<MultiMedia>> updateMultiMedias(List<MultiMedia> multiMedias) {
        return daoSession.getMultiMediaDao().rx().updateInTx(multiMedias);
    }

    @Override
    public Observable<Void> deleteMultiMedia(MultiMedia multiMedia) {
        if(multiMedia == null){
            return Observable.error(new Throwable("deleteMultiMedia : param multiMedia is null"));
        }
        if(multiMedia.getMULTIMEDIA_ID() == null){
            MultiMedia media = daoSession.getMultiMediaDao().queryBuilder()
                    .where(MultiMediaDao.Properties.RELATE_TYPE.eq(multiMedia.getRELATE_TYPE()))
                    .where(MultiMediaDao.Properties.FILE_NAME.eq(multiMedia.getFILE_NAME()))
                    .unique();
            if(media == null){
                return Observable.error(new Throwable("deleteMultiMedia : table BZ_MULTIMEDIA is not exist multiMedia data"));
            }
            return daoSession.getMultiMediaDao().rx().delete(media);
        }
        return daoSession.getMultiMediaDao().rx().delete(multiMedia);
    }

    @Override
    public Observable<MultiMedia> updateMultiMedia(long multiMediaID, long serverID, String fileHash, String fileUrl) {
        MultiMedia multiMedia = daoSession.getMultiMediaDao().load(multiMediaID);
        if (multiMedia == null) {
            return Observable.error(new Throwable("updateMultiMedia : not find multiMedia by key multiMediaID"));
        }
        multiMedia.setSERVER_ID(serverID);
        multiMedia.setFILE_HASH(fileHash);
        multiMedia.setFILE_URL(fileUrl);
        return daoSession.getMultiMediaDao().rx().update(multiMedia);
    }

    @Override
    public Observable<Iterable<Word>> insertWords(List<Word> words) {
        if (words == null || words.size() == 0) {
            return Observable.error(new Throwable("insertWords : words is null"));
        }
        return daoSession.getWordDao().rx().insertOrReplaceInTx(words);
    }

    @Override
    public Observable<List<Word>> queryWords(String group) {
        if (group == null) {
            return Observable.error(new Throwable("queryWords : group is null"));
        }
        return daoSession.getWordDao().queryBuilder().where(WordDao.Properties.GROUP.eq(group)).rx().list();
    }

    @Override
    public Observable<Word> queryWord(String group, String value) {
        if (group == null || value == null) {
            return Observable.error(new Throwable("queryWord : group or value is null"));
        }
        return daoSession.getWordDao().queryBuilder().where(WordDao.Properties.GROUP.eq(group), WordDao.Properties.VALUE.eq(value)).rx().unique();
    }

    @Override
    public Observable<Boolean> saveOperate(final Project project, final Operate operate, final List<MultiMedia> medias) {
        if (project == null) {
            return Observable.error(new Throwable("saveOperate : project is null"));
        }
        if (operate == null) {
            return Observable.error(new Throwable("saveOperate : operate is null"));
        }
        return daoSession.rxTx().call(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                daoSession.getProjectDao().update(project);
                long result = daoSession.getOperateDao().insertOrReplace(operate);
                if (result == -1) {
                    return false;
                }
                Long id = daoSession.getOperateDao().getKey(operate);
                if (medias != null && medias.size() > 0) {
                    for(MultiMedia media : medias){
                        media.setRELATE_ID(id);
                    }
                    daoSession.getMultiMediaDao().insertOrReplaceInTx(medias);
                }
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> savePatrol(final Patrol patrol, final List<MultiMedia> medias) {
        if (patrol == null) {
            return Observable.error(new Throwable("savePatrol : patrol is null"));
        }
        return daoSession.rxTx().call(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                long result = daoSession.getPatrolDao().insertOrReplace(patrol);
                if (result == -1) {
                    return false;
                }
                Long id = daoSession.getPatrolDao().getKey(patrol);
                if (medias != null && medias.size() > 0) {
                    for(MultiMedia media : medias){
                        media.setRELATE_ID(id);
                    }
                    daoSession.getMultiMediaDao().insertOrReplaceInTx(medias);
                }
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveBranchAccept(final Budget budget, final BranchAccept branchAccept, final List<MultiMedia> multiMedias) {
        if (budget == null) {
            return Observable.error(new Throwable("saveBranchAccept : budget is null"));
        }
        if (branchAccept == null) {
            return Observable.error(new Throwable("saveBranchAccept : branchAccept is null"));
        }
        return daoSession.rxTx().call(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                daoSession.getBudgetDao().update(budget);
                long result = daoSession.getBranchAcceptDao().insertOrReplace(branchAccept);
                if (result == -1) {
                    return false;
                }
                Long id = daoSession.getBranchAcceptDao().getKey(branchAccept);
                if (multiMedias != null && multiMedias.size() > 0) {
                    for(MultiMedia media : multiMedias){
                        media.setRELATE_ID(id);
                    }
                    daoSession.getMultiMediaDao().insertOrReplaceInTx(multiMedias);
                }
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveBranchAccept(final Budget budget, final BranchAccept branchAccept) {
        if (budget == null) {
            return Observable.error(new Throwable("saveBranchAccept : budget is null"));
        }
        if (branchAccept == null) {
            return Observable.error(new Throwable("saveBranchAccept : branchAccept is null"));
        }
        return daoSession.rxTx().call(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                daoSession.getBudgetDao().update(budget);
                long result = daoSession.getBranchAcceptDao().insertOrReplace(branchAccept);
                if (result == -1) {
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public Observable<Map<String, Long>> saveBranchAccept(final BranchAccept branchAccept, final MultiMedia multiMedia) {
        if (branchAccept == null) {
            return Observable.error(new Throwable("saveBranchAccept : branchAccept is null"));
        }
        if(multiMedia == null){
            return Observable.error(new Throwable("saveBranchAccept : multiMedia is null"));
        }
        return daoSession.rxTx().call(new Callable<Map<String, Long>>() {
            @Override
            public Map<String, Long> call() throws Exception {
                if (daoSession.getBranchAcceptDao().insertOrReplace(branchAccept) == -1) {
                    return null;
                }
                multiMedia.setRELATE_ID(branchAccept.getACCEPT_ID());
                if(daoSession.getMultiMediaDao().insertOrReplace(multiMedia) == -1){
                    return null;
                }
                Map<String, Long> result = new HashMap<String, Long>();
                result.put("acceptId", branchAccept.getACCEPT_ID());
                result.put("multimediaId", multiMedia.getMULTIMEDIA_ID());
                return result;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteBranchAccept(final BranchAccept branchAccept, final List<MultiMedia> multiMedias) {
        if (branchAccept == null) {
            return Observable.error(new Throwable("deleteBranchAccept : branchAccept is null"));
        }
        if(multiMedias == null || multiMedias.size() == 0){
            return Observable.error(new Throwable("deleteBranchAccept : multiMedias is null or is empty"));
        }
        return daoSession.rxTx().call(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                daoSession.getBranchAcceptDao().delete(branchAccept);
                daoSession.getMultiMediaDao().deleteInTx(multiMedias);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveProjectAccept(final Budget budget, final ProjectAccept projectAccept, final List<MultiMedia> multiMedias) {
        if (budget == null) {
            return Observable.error(new Throwable("saveProjectAccept : budget is null"));
        }
        if (projectAccept == null) {
            return Observable.error(new Throwable("saveProjectAccept : branchAccept is null"));
        }
        return daoSession.rxTx().call(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                daoSession.getBudgetDao().update(budget);
                long result = daoSession.getProjectAcceptDao().insertOrReplace(projectAccept);
                if (result == -1) {
                    return false;
                }
                if (multiMedias != null && multiMedias.size() > 0) {
                    for(MultiMedia media : multiMedias){
                        media.setRELATE_ID(projectAccept.getACCEPT_ID());
                    }
                    daoSession.getMultiMediaDao().insertOrReplaceInTx(multiMedias);
                }
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveConstructionAccept(final Budget budget, final ConstructionAccept constructionAccept, final List<MultiMedia> multiMedias) {
        if (budget == null) {
            return Observable.error(new Throwable("saveConstructionAccept : budget is null"));
        }
        if (constructionAccept == null) {
            return Observable.error(new Throwable("saveConstructionAccept : constructionAccept is null"));
        }
        return daoSession.rxTx().call(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                daoSession.getBudgetDao().update(budget);
                long result = daoSession.getConstructionAcceptDao().insertOrReplace(constructionAccept);
                if (result == -1) {
                    return false;
                }
                if (multiMedias != null && multiMedias.size() > 0) {
                    for(MultiMedia media : multiMedias){
                        media.setRELATE_ID(constructionAccept.getACCEPT_ID());
                    }
                    daoSession.getMultiMediaDao().insertOrReplaceInTx(multiMedias);
                }
                return true;
            }
        });
    }
}
