/**
 * @author qiweiwei
 */
package com.sh3h.localprovider;


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

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * IDataProvider
 */
public interface IDataProvider {

    /** --------------------------Project-------------------------- **/

    /** 插入单条工程 */
    Observable<Project> insertProject(Project project);

    /** 插入多条工程 */
    Observable<Iterable<Project>> insertProjects(List<Project> projects);

    /** 删除单条工程 **/
    Observable<Void> deleteProject(Project project);

    /** 删除多条工程 */
    Observable<Void> deleteProjects(List<Project> projects);

    /** 更新工程 **/
    Observable<Project> updateProject(Project project);

    /** 更新工程状态 **/
    Observable<Project> updateProject(long projectID, int state);

    /** 更新工程状态和时间 **/
    Observable<Project> updateProject(long projectID, long time, int state);

    /** 查询工程编号对应的工程 **/
    Observable<Project> queryProject(String constructionTeam, String projectNumber);

    /** 查询施工队所属工程 **/
    Observable<List<Project>> queryProjects(String constructionTeam);

    /** 查询施工队所属的工程状态对应的工程 **/
    Observable<List<Project>> queryProjects(String constructionTeam, List<Integer> projectState, int index, int offset);

    /** 查询所有工程 **/
    Observable<List<Project>> queryAllProject();

    /** --------------------------Operate-------------------------- **/

    /** 插入工程操作 **/
    Observable<Operate> insertOperate(Operate operate);

    /** 删除单条工程操作 **/
    Observable<Void> deleteOperate(Operate operate);

    /** 删除多条工程操作 **/
    Observable<Void> deleteOperates(List<Operate> operates);

    /** 更新工程操作serverID **/
    Observable<Operate> updateOperate(long operateID, long serverID);

    /** 更新工程操作 **/
    Observable<Iterable<Operate>> updateOperates(List<Operate> operates);

    Observable<List<Operate>> queryOperatesByUpload(String userName, int upload);

    /** --------------------------Patrol-------------------------- **/

    /** 插入工程巡视记录 **/
    Observable<Patrol> insertPatrol(Patrol patrol);

    /** 更新工程巡视serverID **/
    Observable<Patrol> updatePatrol(long patrolID, long serverID);

    /** 根据上传状态查询巡视信息 **/
    Observable<List<Patrol>> queryPatrolsByUpload(String userName, int type, int upload);

    /** 更新工程操作 **/
    Observable<Iterable<Patrol>> updatePatrols(List<Patrol> patrols);

    /** --------------------------Violation-------------------------- **/

    /** 插入多条违规配置 **/
    Observable<Iterable<Violation>> insertViolations(List<Violation> violations);

    /** 查询违规的所有类型 **/
    Observable<List<Violation>> queryAllViolationType();

    /** 查询类型为violationType的所有违规配置 **/
    Observable<List<Violation>> queryViolationByType(String violationType);

    /** --------------------------Material-------------------------- **/

    /** 插入多条材料配置 **/
    Observable<Iterable<Material>> insertMaterials(List<Material> materials);

    /** 通过材料ID查询材料 **/
    Material queryMaterial(long materialId);

    /** 查询材料类别 **/
    Observable<List<Material>> queryAllMaterialCategory();

    /** 查询默认地址 **/
    Observable<Address> getDefaultDUAddress(int userId);

    /** 查询所有地址 **/
    Observable<List<Address>> getAddresses(int userId);

    /** 查询所有未上传地址 **/
    Observable<List<Address>> getAddressesNoUpload(int userId);

    /** 通过材料类型查询材料名称 **/
    Observable<List<Material>> queryMaterialNameByCategory(String materialCategory);

    /** 通过材料名称查询材料规格 **/
    Observable<List<Material>> queryMaterialFormatByName(String materialCategory,String materialName);

    /** --------------------------MaterialApply-------------------------- **/

    /** 插入材料申领记录 **/
    Observable<MaterialApply> insertMaterialApply(MaterialApply materialApply);

    /** 更新材料申领serverID **/
    Observable<MaterialApply> updateMaterialApply(long applyID, long serverID);

    /** --------------------------MaterialVerify-------------------------- **/

    /** 插入材料确认记录 **/
    Observable<MaterialVerify> insertMaterialVerify(MaterialVerify materialVerify);

    /** 更新材料确认serverID **/
    Observable<MaterialVerify> updateMaterialVerify(long verifyID, long serverID);

    /** --------------------------Budget-------------------------- **/

    /** 插入多条预算信息 **/
    Observable<Iterable<Budget>> insertBudgets(List<Budget> budgets);

    /** 更新预算状态 **/
    Observable<Budget> updateBudget(long budgetID, int state);

    /** 更预算信息 **/
    Observable<Iterable<Budget>> updateBudgets(List<Budget> budgets);

    /** 查询该工程预算信息 **/
    Observable<List<Budget>> queryProjectBudgets(long projectId);

    /** 查询该工程预算信息通过工程Id和类型 **/
    Observable<List<Budget>> queryProjectBudgets(long projectId, int state);

    /**通过预算类型获取预算数量 **/
    long getBudgetCount(long projectId, int budgetState);

    /** 获取所有预算数量**/
    long getBudgetTotalCount(long projectId);

    /** --------------------------BranchAccept-------------------------- **/

    /** 插入支管验收记录 **/
    Observable<BranchAccept> insertBranchAccept(BranchAccept branchAccept);

    /** 更新入支管验收serverID **/
    Observable<BranchAccept> updateBranchAccept(long acceptID, long serverID);

    Observable<List<BranchAccept>> queryBranchAccepts(String userName, int upload);

    Observable<BranchAccept> queryBranchAccept(int upload, String userName, long projectId, long budgetId, int acceptType);

    /** 更支管验收信息 **/
    Observable<Iterable<BranchAccept>> updateBranchAccepts(List<BranchAccept> branchAccepts);

    /** --------------------------ProjectAccept-------------------------- **/

    /** 插入支管验收记录 **/
    Observable<ProjectAccept> insertProjectAccept(ProjectAccept projectAccept);

    /** 更新入支管验收serverID **/
    Observable<ProjectAccept> updateProjectAccept(long acceptID, long serverID);

    /** 更支管验收信息 **/
    Observable<Iterable<ProjectAccept>> updateProjectAccepts(List<ProjectAccept> projectAccepts);

    Observable<List<ProjectAccept>> queryProjectAcceptsByUpload(String userName, int upload);

    /** -------------------------ConstructionAccept--------------------------**/

    Observable<List<ConstructionAccept>> queryConstructionAccepts(String userName, int upload);

    Observable<Iterable<ConstructionAccept>> updateConstructionAccepts(List<ConstructionAccept> constructionAccepts);

    /** --------------------------Address-------------------------- **/

    /** 插入单条地址 **/
    Observable<Address> insertAddress(Address address);

    /** 插入多条地址 **/
    Observable<Iterable<Address>> insertAddresses(List<Address> addresses);

    /** 查询单条地址 **/
    Address queryAddress(long serverId);

    /** 删除单条地址 **/
    Observable<Void> deleteAddress(Address address);

    /** 更新地址 **/
    Observable<Address> updateAddress(Address address);

    /** 查询所有地址 **/
    Observable<List<Address>> queryAddress();

    /** --------------------------MultiMedia-------------------------- **/

    /** 插入单条多媒体 **/
    Observable<MultiMedia> insertMultiMedia(MultiMedia multiMedia);

    /** 插入多条多媒体 **/
    Observable<Iterable<MultiMedia>> insertMultiMedias(List<MultiMedia> multiMedias);

    /** 查询多媒体 **/
    Observable<List<MultiMedia>> queryMultiMedias(List<Map<String, Object>> idTypes, int upload);

    Observable<List<MultiMedia>> queryMultiMedias(List<Map<String, Long>> ids, List<Integer> types, int upload);

    Observable<List<MultiMedia>> queryMultiMedias(long relateId, int relateType);

    Observable<List<MultiMedia>> queryMultiMedias(int[] uploads);

    /** 更新多媒体 **/
    Observable<MultiMedia> updateMultiMedia(MultiMedia multiMedia);

    /** 更新多媒体 **/
    Observable<Iterable<MultiMedia>> updateMultiMedias(List<MultiMedia> multiMedias);

    Observable<Void> deleteMultiMedia(MultiMedia multiMedia);

    /** 更新入支管验收serverID, fileHash, fileUrl **/
    Observable<MultiMedia> updateMultiMedia(long multiMediaID, long serverID, String fileHash, String fileUrl);

    /** --------------------------Word-------------------------- **/

    /** 插入多条词语信息 **/
    Observable<Iterable<Word>> insertWords(List<Word> words);

    Observable<List<Word>> queryWords(String group);

    /** 查询词语Name **/
    Observable<Word> queryWord(String group, String value);


    /** --------------------------其他-------------------------- **/

    /** 保存工程操作流程 更新工程&&插入工程操作&&插入多媒体 **/
    Observable<Boolean> saveOperate(Project project, Operate operate, List<MultiMedia> medias);

    /** 保存工程巡视 插入工程巡视信息&&插入多媒体 **/
    Observable<Boolean> savePatrol(Patrol patrol, List<MultiMedia> medias);

    /** 保存预算信息 插入支管验收信息&&插入多媒体 **/
    Observable<Boolean> saveBranchAccept(Budget budget, BranchAccept branchAccept, List<MultiMedia> multiMedias);

    Observable<Boolean> saveBranchAccept(Budget budget, BranchAccept branchAccept);

    Observable<Map<String, Long>> saveBranchAccept(BranchAccept branchAccept, MultiMedia multiMedia);

    Observable<Boolean> deleteBranchAccept(BranchAccept branchAccept, List<MultiMedia> multiMedias);

    /** 保存预算信息 插入工程验收信息&&插入多媒体 **/
    Observable<Boolean> saveProjectAccept(Budget budget, ProjectAccept projectAccept, List<MultiMedia> multiMedias);

    Observable<Boolean> saveConstructionAccept(Budget budget, ConstructionAccept constructionAccept, List<MultiMedia> multiMedias);

}
