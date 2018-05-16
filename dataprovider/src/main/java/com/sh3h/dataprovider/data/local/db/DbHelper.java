package com.sh3h.dataprovider.data.local.db;

import android.content.Context;

import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.injection.annotation.ApplicationContext;
import com.sh3h.localprovider.DBManager;
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

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class DbHelper {
    private static final String TAG = "DbHelper";

    private final Context mContext;
    private final ConfigHelper mConfigHelper;
    private final String mDBPath;

    @Inject
    public DbHelper(@ApplicationContext Context context,
                    ConfigHelper configHelper) {
        mContext = context;
        mConfigHelper = configHelper;
        mDBPath = mConfigHelper.getDBFilePath().getPath();
    }

    /* -------------------------------------Project-----------------------------------------------*/

    public Observable<Project> insertProject(Project project){
        return DBManager.getDataProvider(mContext, mDBPath).insertProject(project);
    }

    public Observable<Iterable<Project>> insertProjects(List<Project> projects) {
        return DBManager.getDataProvider(mContext, mDBPath).insertProjects(projects);
    }

    public Observable<Project> queryProject(String constructionTeam, String projectNumber) {
        return DBManager.getDataProvider(mContext, mDBPath).queryProject(constructionTeam, projectNumber);
    }

    public Observable<List<Project>> queryProjects(String constructionTeam, List<Integer> projectState, int index, int offset) {
        return DBManager.getDataProvider(mContext, mDBPath).queryProjects(constructionTeam, projectState, index, offset);
    }

    /* -------------------------------------Operate-----------------------------------------------*/

    public Observable<Boolean> saveOperate(Project project, Operate operate, List<MultiMedia> medias) {
        return DBManager.getDataProvider(mContext, mDBPath).saveOperate(project, operate, medias);
    }

    public Observable<List<Operate>> queryOperatesByUpload(String userName, int upload) {
        return DBManager.getDataProvider(mContext, mDBPath).queryOperatesByUpload(userName, upload);
    }

    public Observable<Iterable<Operate>> updateOperates(List<Operate> operates) {
        return DBManager.getDataProvider(mContext, mDBPath).updateOperates(operates);
    }

    /* -------------------------------------Violation-----------------------------------------------*/

    public Observable<Iterable<Violation>> insertViolations(List<Violation> violations) {
        return DBManager.getDataProvider(mContext, mDBPath).insertViolations(violations);
    }

    public Observable<List<Violation>> queryAllViolationType() {
        return DBManager.getDataProvider(mContext, mDBPath).queryAllViolationType();
    }

    public Observable<List<Violation>> queryViolationByType(String violationType) {
        return DBManager.getDataProvider(mContext, mDBPath).queryViolationByType(violationType);
    }

    /* -------------------------------------Word-----------------------------------------------*/

    public Observable<Iterable<Word>> insertWords(List<Word> words) {
        return DBManager.getDataProvider(mContext, mDBPath).insertWords(words);
    }

    public Observable<Word> queryWordName(String group, String value) {
        return DBManager.getDataProvider(mContext, mDBPath).queryWord(group, value);
    }

    public Observable<List<Word>> queryWords(String group) {
        return DBManager.getDataProvider(mContext, mDBPath).queryWords(group);
    }

    /* -------------------------------------Material-----------------------------------------------*/

    public Observable<Iterable<Material>> insertMaterials(List<Material> materials) {
        return DBManager.getDataProvider(mContext, mDBPath).insertMaterials(materials);
    }

    public Material queryMaterial(long materialId){
        return DBManager.getDataProvider(mContext, mDBPath).queryMaterial(materialId);
    }

    public Observable<List<Material>> queryAllMaterialCategory() {
        return DBManager.getDataProvider(mContext, mDBPath).queryAllMaterialCategory();
    }

    public Observable<Address> getDefaultDUAddress(int userId) {
        return DBManager.getDataProvider(mContext, mDBPath).getDefaultDUAddress(userId);
    }

    public Observable<List<Address>> getAddresses(int userId) {
        return DBManager.getDataProvider(mContext, mDBPath).getAddresses(userId);
    }

    public Observable<List<Address>> getAddressesNoUpload(int userId) {
        return DBManager.getDataProvider(mContext, mDBPath).getAddressesNoUpload(userId);
    }

    public Observable<List<Material>> queryMaterialNameByCategory(String materialCategory) {
        return DBManager.getDataProvider(mContext, mDBPath).queryMaterialNameByCategory(materialCategory);
    }

    public Observable<List<Material>> queryMaterialFormatByName(String materialCategory, String materialName) {
        return DBManager.getDataProvider(mContext, mDBPath).queryMaterialFormatByName(materialCategory, materialName);
    }

    /* -------------------------------------Address-----------------------------------------------*/

    public Observable<Iterable<Address>> insertAddresses(List<Address> addresses) {
        return DBManager.getDataProvider(mContext, mDBPath).insertAddresses(addresses);
    }

    public Observable<Address> insertAddress(Address address){
        return DBManager.getDataProvider(mContext, mDBPath).insertAddress(address);
    }

     /* -------------------------------------Budget-----------------------------------------------*/

    public Observable<Iterable<Budget>> insertBudgets(List<Budget> budgets) {
        return DBManager.getDataProvider(mContext, mDBPath).insertBudgets(budgets);
    }

    public Observable<List<Budget>> queryProjectBudgets(long projectId) {
        return DBManager.getDataProvider(mContext, mDBPath).queryProjectBudgets(projectId);
    }

    public Observable<List<Budget>> queryProjectBudgets(long projectId, int state) {
        return DBManager.getDataProvider(mContext, mDBPath).queryProjectBudgets(projectId, state);
    }

    public Observable<Iterable<Budget>> updateBudgets(List<Budget> budgets) {
        return DBManager.getDataProvider(mContext, mDBPath).updateBudgets(budgets);
    }

    public long getBudgetCount(long projectId, int budgetState){
        return DBManager.getDataProvider(mContext, mDBPath).getBudgetCount(projectId, budgetState);
    }

    public long getBudgetTotalCount(long projectId){
        return DBManager.getDataProvider(mContext, mDBPath).getBudgetTotalCount(projectId);
    }

    /* -------------------------------------Patrol-----------------------------------------------*/

    public Observable<Boolean> savePatrol(Patrol patrol, List<MultiMedia> medias) {
        return DBManager.getDataProvider(mContext, mDBPath).savePatrol(patrol, medias);
    }

    public Observable<List<Patrol>> queryPatrolsByUpload(String userName, int type, int upload) {
        return DBManager.getDataProvider(mContext, mDBPath).queryPatrolsByUpload(userName, type, upload);
    }

    public Observable<Iterable<Patrol>> updatePatrols(List<Patrol> patrols) {
        return DBManager.getDataProvider(mContext, mDBPath).updatePatrols(patrols);
    }

    /* -------------------------------------ProjectAccept-----------------------------------------------*/

    public Observable<List<ProjectAccept>> queryProjectAcceptsByUpload(String userName, int upload) {
        return DBManager.getDataProvider(mContext, mDBPath).queryProjectAcceptsByUpload(userName, upload);
    }

    public Observable<Iterable<ProjectAccept>> updateProjectAccepts(List<ProjectAccept> projectAccepts) {
        return DBManager.getDataProvider(mContext, mDBPath).updateProjectAccepts(projectAccepts);
    }

    public Observable<Boolean> saveProjectAccept(Budget budget, ProjectAccept projectAccept,
                                                 List<MultiMedia> multiMedias) {
        return DBManager.getDataProvider(mContext, mDBPath).saveProjectAccept(budget, projectAccept, multiMedias);
    }

    public Observable<Boolean> saveConstructionAccept(Budget budget, ConstructionAccept constructionAccept,
                                                 List<MultiMedia> multiMedias) {
        return DBManager.getDataProvider(mContext, mDBPath).saveConstructionAccept(budget, constructionAccept, multiMedias);
    }

    /* -------------------------------------BranchAccept-----------------------------------------------*/

    public Observable<List<BranchAccept>> queryBranchAccepts(String userName, int upload) {
        return DBManager.getDataProvider(mContext, mDBPath).queryBranchAccepts(userName, upload);
    }

    public Observable<BranchAccept> queryBranchAccept(int upload, String userName, long project, long budgetId, int acceptType){
        return DBManager.getDataProvider(mContext, mDBPath).queryBranchAccept(upload, userName, project, budgetId, acceptType);
    }

    public Observable<Iterable<BranchAccept>> updateBranchAccepts(List<BranchAccept> branchAccepts) {
        return DBManager.getDataProvider(mContext, mDBPath).updateBranchAccepts(branchAccepts);
    }

    public Observable<Boolean> saveBranchAccept(Budget budget, BranchAccept branchAccept,
                                                List<MultiMedia> multiMedias) {
        return DBManager.getDataProvider(mContext, mDBPath).saveBranchAccept(budget, branchAccept, multiMedias);
    }

    public Observable<Boolean> saveBranchAccept(Budget budget, BranchAccept branchAccept) {
        return DBManager.getDataProvider(mContext, mDBPath).saveBranchAccept(budget, branchAccept);
    }

    public Observable<Map<String, Long>> saveBranchAccept(BranchAccept branchAccept, MultiMedia multiMedia) {
        return DBManager.getDataProvider(mContext, mDBPath).saveBranchAccept(branchAccept, multiMedia);
    }

    public Observable<Boolean> deleteBranchAccept(BranchAccept branchAccept, List<MultiMedia> multiMedias){
        return DBManager.getDataProvider(mContext, mDBPath).deleteBranchAccept(branchAccept, multiMedias);
    }

    /* -------------------------------------ConstructionAccept-----------------------------------------------*/

    public Observable<List<ConstructionAccept>> queryConstructionAccepts(String userName, int upload) {
        return DBManager.getDataProvider(mContext, mDBPath).queryConstructionAccepts(userName, upload);
    }

    public Observable<Iterable<ConstructionAccept>> updateConstructionAccepts(List<ConstructionAccept> constructionAccepts){
        return DBManager.getDataProvider(mContext, mDBPath).updateConstructionAccepts(constructionAccepts);
    }

    /* -------------------------------------MultiMedia-----------------------------------------------*/
    public Observable<List<MultiMedia>> queryMultiMedias(List<Map<String, Object>> idTypes, int upload) {
        return DBManager.getDataProvider(mContext, mDBPath).queryMultiMedias(idTypes, upload);
    }

    public Observable<List<MultiMedia>> queryMultiMedias(List<Map<String, Long>> ids, List<Integer> types, int upload) {
        return DBManager.getDataProvider(mContext, mDBPath).queryMultiMedias(ids, types, upload);
    }

    public Observable<List<MultiMedia>> queryMultiMedias(long relateId, int relateType){
        return DBManager.getDataProvider(mContext, mDBPath).queryMultiMedias(relateId, relateType);
    }

    public Observable<List<MultiMedia>> queryMultiMedias(int[] uploads){
        return DBManager.getDataProvider(mContext, mDBPath).queryMultiMedias(uploads);
    }

    public Observable<MultiMedia> updateMultiMedia(MultiMedia multiMedia) {
        return DBManager.getDataProvider(mContext, mDBPath).updateMultiMedia(multiMedia);
    }

    public Observable<Iterable<MultiMedia>> updateMultiMedias(List<MultiMedia> multiMedias) {
        return DBManager.getDataProvider(mContext, mDBPath).updateMultiMedias(multiMedias);
    }

    public Observable<Void> deleteMultiMedia(MultiMedia multiMedia){
        return DBManager.getDataProvider(mContext, mDBPath).deleteMultiMedia(multiMedia);
    }


    /**
     * destroy
     */
    public synchronized void close() {
        DBManager.getInstance(mContext, mDBPath).closeDB();
    }

    /**
     * clear all tables
     */
    public synchronized void clearData() {
        DBManager.getInstance(mContext, mDBPath).clearDB();
    }


}
