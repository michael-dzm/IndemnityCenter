package com.sh3h.dataprovider.util;

import com.sh3h.dataprovider.data.entity.DUAddress;
import com.sh3h.dataprovider.data.entity.DUBranchAccept;
import com.sh3h.dataprovider.data.entity.DUBudget;
import com.sh3h.dataprovider.data.entity.DUConstructionAccept;
import com.sh3h.dataprovider.data.entity.DUMaterial;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.entity.DUOperate;
import com.sh3h.dataprovider.data.entity.DUPatrol;
import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.dataprovider.data.entity.DUProjectAccept;
import com.sh3h.dataprovider.data.entity.DUViolation;
import com.sh3h.dataprovider.data.entity.DUWord;
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
import java.util.List;

/**
 * 数据库实体与通用实体相互转换
 * Created by dengzhimin on 2017/3/31.
 */

public class OtoO {

    /*------------------------------------------Project && DUProject-----------------------------------------*/

    public static DUProject toDUProject(Project project) {
        if (project == null) {
            return null;
        }
        DUProject duProject = new DUProject();
        duProject.setProjectId(project.getPROJECT_ID());
        duProject.setProjectNumber(project.getPROJECT_NUMBER());
        duProject.setProjectName(project.getPROJECT_NAME());
        duProject.setConstructionUnit(project.getCONSTRUCTION_UNIT());
        duProject.setConstructionTeam(project.getCONSTRUCTION_TEAM());
        duProject.setOffice(project.getOFFICE());
        duProject.setAddress(project.getADDRESS());
        duProject.setStartTime(project.getSTART_TIME());
        duProject.setEndTime(project.getEND_TIME());
        duProject.setState(project.getSTATE());
        duProject.setRemark(project.getREMARK());
        return duProject;
    }

    public static Project toProject(DUProject duProject) {
        if (duProject == null) {
            return null;
        }
        Project project = new Project();
        project.setPROJECT_ID(duProject.getProjectId());
        project.setPROJECT_NUMBER(duProject.getProjectNumber());
        project.setPROJECT_NAME(duProject.getProjectName());
        project.setCONSTRUCTION_UNIT(duProject.getConstructionUnit());
        project.setCONSTRUCTION_TEAM(duProject.getConstructionTeam());
        project.setOFFICE(duProject.getOffice());
        project.setADDRESS(duProject.getAddress());
        project.setSTART_TIME(duProject.getStartTime());
        project.setEND_TIME(duProject.getEndTime());
        project.setSTATE(duProject.getState());
        project.setREMARK(duProject.getRemark());
        return project;
    }

    public static List<Project> toProjects(List<DUProject> duProjects) {
        if (duProjects == null) {
            return null;
        }
        List<Project> projects = new ArrayList<>();
        for (DUProject duProject : duProjects) {
            projects.add(toProject(duProject));
        }
        return projects;
    }

    public static List<DUProject> toDUProjects(List<Project> projects) {
        if (projects == null) {
            return null;
        }
        List<DUProject> duProjects = new ArrayList<>();
        for (Project project : projects) {
            duProjects.add(toDUProject(project));
        }
        return duProjects;
    }

    /*------------------------------------------Operate && DUOperate-----------------------------------------*/

    public static Operate toOperate(DUOperate duOperate) {
        if (duOperate == null) {
            return null;
        }
        Operate operate = new Operate();
        operate.setOPERATE_ID(duOperate.getOperateId());
        operate.setSERVER_ID(duOperate.getServerId());
        operate.setPROJECT_ID(duOperate.getProjectId());
        operate.setOPERATE_TYPE(duOperate.getOperateType());
        operate.setSTART_TIME(duOperate.getStartTime());
        operate.setSTOP_TIME(duOperate.getStopTime());
        operate.setRESTART_TIME(duOperate.getRestartTime());
        operate.setEND_TIME(duOperate.getEndTime());
        operate.setOPERATOR(duOperate.getOperator());
        operate.setOPERATE_TIME(duOperate.getOperateTime());
        operate.setLONGITUDE(duOperate.getLongitude());
        operate.setLATITUDE(duOperate.getLatitude());
        operate.setREMARK(duOperate.getRemark());
        operate.setUPLOAD(duOperate.getUpload());
        return operate;
    }

    public static DUOperate toDUOperate(Operate operate) {
        if (operate == null) {
            return null;
        }
        DUOperate duOperate = new DUOperate();
        duOperate.setOperateId(operate.getOPERATE_ID());
        duOperate.setServerId(operate.getSERVER_ID());
        duOperate.setProjectId(operate.getPROJECT_ID());
        duOperate.setOperateType(operate.getOPERATE_TYPE());
        duOperate.setStartTime(operate.getSTART_TIME());
        duOperate.setStopTime(operate.getSTOP_TIME());
        duOperate.setRestartTime(operate.getRESTART_TIME());
        duOperate.setEndTime(operate.getEND_TIME());
        duOperate.setOperator(operate.getOPERATOR());
        duOperate.setOperateTime(operate.getOPERATE_TIME());
        duOperate.setLongitude(operate.getLONGITUDE());
        duOperate.setLatitude(operate.getLATITUDE());
        duOperate.setRemark(operate.getREMARK());
        duOperate.setUpload(operate.getUPLOAD());
        return duOperate;
    }

    public static List<Operate> toOperates(List<DUOperate> duOperates) {
        if (duOperates == null) {
            return null;
        }
        List<Operate> operates = new ArrayList<>();
        for (DUOperate duOperate : duOperates) {
            operates.add(toOperate(duOperate));
        }
        return operates;
    }

    public static List<DUOperate> toDUOperates(List<Operate> operates) {
        if (operates == null) {
            return null;
        }
        List<DUOperate> duOperates = new ArrayList<>();
        for (Operate operate : operates) {
            duOperates.add(toDUOperate(operate));
        }
        return duOperates;
    }

    /*------------------------------------------MultiMedia && DUMultiMedia-----------------------------------------*/

    public static MultiMedia toMultiMedia(DUMultiMedia duMultiMedia) {
        if (duMultiMedia == null) {
            return null;
        }
        MultiMedia multiMedia = new MultiMedia();
        multiMedia.setMULTIMEDIA_ID(duMultiMedia.getMultimediaId());
        multiMedia.setSERVER_ID(duMultiMedia.getServerId());
        multiMedia.setRELATE_ID(duMultiMedia.getRelateId());
        multiMedia.setRELATE_TYPE(duMultiMedia.getRelateType());
        multiMedia.setFILE_TYPE(duMultiMedia.getFileType());
        multiMedia.setFILE_NAME(duMultiMedia.getFileName());
        multiMedia.setFILE_PATH(duMultiMedia.getFilePath());
        multiMedia.setFILE_HASH(duMultiMedia.getFileHash());
        multiMedia.setFILE_URL(duMultiMedia.getFileUrl());
        multiMedia.setFILE_TIME(duMultiMedia.getFileTime());
        multiMedia.setUPLOAD(duMultiMedia.getUpload());
        return multiMedia;
    }

    public static List<MultiMedia> toMultiMedias(List<DUMultiMedia> duMultiMedias) {
        if (duMultiMedias == null) {
            return null;
        }
        List<MultiMedia> multiMedias = new ArrayList<>();
        for (DUMultiMedia duMultiMedia : duMultiMedias) {
            multiMedias.add(toMultiMedia(duMultiMedia));
        }
        return multiMedias;
    }

    public static DUMultiMedia toDUMultiMedia(MultiMedia multiMedia) {
        if (multiMedia == null) {
            return null;
        }
        DUMultiMedia duMultiMedia = new DUMultiMedia();
        duMultiMedia.setMultimediaId(multiMedia.getMULTIMEDIA_ID());
        duMultiMedia.setServerId(multiMedia.getSERVER_ID());
        duMultiMedia.setRelateId(multiMedia.getRELATE_ID());
        duMultiMedia.setRelateType(multiMedia.getRELATE_TYPE());
        duMultiMedia.setFileType(multiMedia.getFILE_TYPE());
        duMultiMedia.setFileName(multiMedia.getFILE_NAME());
        duMultiMedia.setFilePath(multiMedia.getFILE_PATH());
        duMultiMedia.setFileHash(multiMedia.getFILE_HASH());
        duMultiMedia.setFileUrl(multiMedia.getFILE_URL());
        duMultiMedia.setFileTime(multiMedia.getFILE_TIME());
        duMultiMedia.setUpload(multiMedia.getUPLOAD());
        return duMultiMedia;
    }

    public static List<DUMultiMedia> toDUMultiMedias(List<MultiMedia> multiMedias) {
        if (multiMedias == null) {
            return null;
        }
        List<DUMultiMedia> duMultiMedias = new ArrayList<>();
        for (MultiMedia multiMedia : multiMedias) {
            duMultiMedias.add(toDUMultiMedia(multiMedia));
        }
        return duMultiMedias;
    }

    /*---------------------------------------Violation && DUViolation-----------------------------------------*/

    public static DUViolation toDUViolation(Violation violation) {
        if (violation == null) {
            return null;
        }
        DUViolation duViolation = new DUViolation();
        duViolation.setViolationId(violation.getVIOLATION_ID());
        duViolation.setViolationNumber(violation.getVIOLATION_NUMBER());
        duViolation.setViolationType(violation.getVIOLATION_TYPE());
        duViolation.setViolationContent(violation.getVIOLATION_CONTENT());
        duViolation.setRemark(violation.getREMARK());
        return duViolation;
    }

    public static Violation toViolation(DUViolation duViolation) {
        if (duViolation == null) {
            return null;
        }
        Violation violation = new Violation();
        violation.setVIOLATION_ID(duViolation.getViolationId());
        violation.setVIOLATION_NUMBER(duViolation.getViolationNumber());
        violation.setVIOLATION_TYPE(duViolation.getViolationType());
        violation.setVIOLATION_CONTENT(duViolation.getViolationContent());
        violation.setREMARK(duViolation.getRemark());
        return violation;
    }

    public static List<DUViolation> toDUViolations(List<Violation> violations) {
        if (violations == null) {
            return null;
        }
        List<DUViolation> duViolations = new ArrayList<>();
        for (Violation violation : violations) {
            duViolations.add(toDUViolation(violation));
        }
        return duViolations;
    }

    public static List<Violation> toViolations(List<DUViolation> duViolations) {
        if (duViolations == null) {
            return null;
        }
        List<Violation> violations = new ArrayList<>();
        for (DUViolation duViolation : duViolations) {
            violations.add(toViolation(duViolation));
        }
        return violations;
    }

    /*---------------------------------------Word && DUWord-----------------------------------------*/

    public static Word toWord(DUWord duWord) {
        if (duWord == null) {
            return null;
        }
        Word word = new Word();
        word.setWORD_ID(duWord.getWordId());
        word.setPARENT_ID(duWord.getParentId());
        word.setGROUP(duWord.getGroup());
        word.setNAME(duWord.getName());
        word.setVALUE(duWord.getValue());
        word.setREMARK(duWord.getRemark());
        return word;
    }

    public static DUWord toDUWord(Word word) {
        if (word == null) {
            return null;
        }
        DUWord duWord = new DUWord();
        duWord.setWordId(word.getWORD_ID());
        duWord.setParentId(word.getPARENT_ID());
        duWord.setGroup(word.getGROUP());
        duWord.setName(word.getNAME());
        duWord.setValue(word.getVALUE());
        duWord.setRemark(word.getREMARK());
        return duWord;
    }

    public static List<Word> toWords(List<DUWord> duWords) {
        if (duWords == null) {
            return null;
        }
        List<Word> words = new ArrayList<>();
        for (DUWord duWord : duWords) {
            words.add(toWord(duWord));
        }
        return words;
    }

    public static List<DUWord> toDUWords(List<Word> words) {
        if (words == null) {
            return null;
        }
        List<DUWord> duWords = new ArrayList<>();
        for (Word word : words) {
            duWords.add(toDUWord(word));
        }
        return duWords;
    }

    /*---------------------------------------Material && DUMaterial-----------------------------------------*/

    public static Material toMaterial(DUMaterial duMaterial) {
        if (duMaterial == null) {
            return null;
        }
        Material material = new Material();
        material.setMATERIAL_ID(duMaterial.getMaterialId());
        material.setMATERIAL_CATEGORY(duMaterial.getMaterialCategory());
        material.setMATERIAL_NAME(duMaterial.getMaterialName());
        material.setMATERIAL_FORMAT(duMaterial.getMaterialFormat());
        material.setMATERIAL_UNIT(duMaterial.getMaterialUnit());
        material.setMATERIAL_PRICE(duMaterial.getMaterialPrice());
        material.setUPPER_LIMIT(duMaterial.getUpperLimit());
        material.setUPPER_LIMIT_TYPE(duMaterial.getUpperLimitType());
        material.setMULTIPLE(duMaterial.getMultiple());
        material.setREMARK(duMaterial.getRemark());
        return material;
    }

    public static DUMaterial toDUMaterial(Material material) {
        if (material == null) {
            return null;
        }
        DUMaterial duMaterial = new DUMaterial();
        duMaterial.setMaterialId(material.getMATERIAL_ID());
        duMaterial.setMaterialCategory(material.getMATERIAL_CATEGORY());
        duMaterial.setMaterialName(material.getMATERIAL_NAME());
        duMaterial.setMaterialFormat(material.getMATERIAL_FORMAT());
        duMaterial.setMaterialUnit(material.getMATERIAL_UNIT());
        duMaterial.setMaterialPrice(material.getMATERIAL_PRICE());
        duMaterial.setUpperLimit(material.getUPPER_LIMIT());
        duMaterial.setUpperLimitType(material.getUPPER_LIMIT_TYPE());
        duMaterial.setMultiple(material.getMULTIPLE());
        duMaterial.setRemark(material.getREMARK());
        return duMaterial;
    }

    public static List<Material> toMaterials(List<DUMaterial> duMaterials) {
        if (duMaterials == null) {
            return null;
        }
        List<Material> materials = new ArrayList<>();
        for (DUMaterial duMaterial : duMaterials) {
            materials.add(toMaterial(duMaterial));
        }
        return materials;
    }

    public static List<DUMaterial> toDUMaterials(List<Material> materials) {
        if (materials == null) {
            return null;
        }
        List<DUMaterial> duMaterials = new ArrayList<>();
        for (Material material : materials) {
            duMaterials.add(toDUMaterial(material));
        }
        return duMaterials;
    }

    /*---------------------------------------Address && DUAddress-----------------------------------------*/

    public static Address toAddress(DUAddress duAddress) {
        if (duAddress == null) {
            return null;
        }
        Address address = new Address();
        address.setADDRESS_ID(duAddress.getAddressId());
        address.setSERVER_ID(duAddress.getServerId());
        address.setUSER_ID(duAddress.getUserId());
        address.setADDRESS_CONTENT(duAddress.getAddressContent());
        address.setDEFAULT(duAddress.getIsDefault());
        address.setOPERATE_TIME(duAddress.getOperateTime());
        address.setUPLOAD(duAddress.getUpload());
        return address;
    }

    public static DUAddress toDUAddress(Address address) {
        if (address == null) {
            return null;
        }
        DUAddress duAddress = new DUAddress();
        duAddress.setAddressId(address.getADDRESS_ID());
        duAddress.setServerId(address.getSERVER_ID());
        duAddress.setUserId(address.getUSER_ID());
        duAddress.setAddressContent(address.getADDRESS_CONTENT());
        duAddress.setIsDefault(address.getDEFAULT());
        duAddress.setOperateTime(address.getOPERATE_TIME());
        duAddress.setUpload(address.getUPLOAD());
        return duAddress;
    }

    public static List<Address> toAddresses(List<DUAddress> duAddresses) {
        if (duAddresses == null) {
            return null;
        }
        List<Address> addresses = new ArrayList<>();
        for (DUAddress duAddress : duAddresses) {
            addresses.add(toAddress(duAddress));
        }
        return addresses;
    }

    public static List<DUAddress> toDUAddresses(List<Address> addresses) {
        if (addresses == null) {
            return null;
        }
        List<DUAddress> duAddresses = new ArrayList<>();
        for (Address address : addresses) {
            duAddresses.add(toDUAddress(address));
        }
        return duAddresses;
    }

    /*---------------------------------------Patrol && DUPatrol-----------------------------------------*/

    public static Patrol toPatrol(DUPatrol duPatrol) {
        if (duPatrol == null) {
            return null;
        }
        Patrol patrol = new Patrol();
        patrol.setPATROL_ID(duPatrol.getPatrolId());
        patrol.setPROJECT_ID(duPatrol.getProjectId());
        patrol.setSERVER_ID(duPatrol.getServerId());
        patrol.setPATROL_TYPE(duPatrol.getPatrolType());
        patrol.setVIOLATION_NUMBER(duPatrol.getViolationNumber());
        patrol.setPATROL_SITUATION(duPatrol.getPatrolSituation());
        patrol.setOPERATOR(duPatrol.getOperator());
        patrol.setOPERATE_TIME(duPatrol.getOperateTime());
        patrol.setLATITUDE(duPatrol.getLatitude());
        patrol.setLONGITUDE(duPatrol.getLongitude());
        patrol.setREMARK(duPatrol.getRemark());
        patrol.setUPLOAD(duPatrol.getUpload());
        return patrol;
    }

    public static DUPatrol toDUPatrol(Patrol patrol) {
        if (patrol == null) {
            return null;
        }
        DUPatrol duPatrol = new DUPatrol();
        duPatrol.setPatrolId(patrol.getPATROL_ID());
        duPatrol.setProjectId(patrol.getPROJECT_ID());
        duPatrol.setServerId(patrol.getSERVER_ID());
        duPatrol.setPatrolType(patrol.getPATROL_TYPE());
        duPatrol.setViolationNumber(patrol.getVIOLATION_NUMBER());
        duPatrol.setPatrolSituation(patrol.getPATROL_SITUATION());
        duPatrol.setOperator(patrol.getOPERATOR());
        duPatrol.setOperateTime(patrol.getOPERATE_TIME());
        duPatrol.setLatitude(patrol.getLATITUDE());
        duPatrol.setLongitude(patrol.getLONGITUDE());
        duPatrol.setRemark(patrol.getREMARK());
        duPatrol.setUpload(patrol.getUPLOAD());
        return duPatrol;
    }

    public static List<Patrol> toPatrols(List<DUPatrol> duPatrols) {
        if (duPatrols == null) {
            return null;
        }
        List<Patrol> patrols = new ArrayList<>();
        for (DUPatrol duPatrol : duPatrols) {
            patrols.add(toPatrol(duPatrol));
        }
        return patrols;
    }

    public static List<DUPatrol> toDUPatrols(List<Patrol> patrols) {
        if (patrols == null) {
            return null;
        }
        List<DUPatrol> duPatrols = new ArrayList<>();
        for (Patrol patrol : patrols) {
            duPatrols.add(toDUPatrol(patrol));
        }
        return duPatrols;
    }

       /*---------------------------------------Budget && DUBudget-----------------------------------------*/

    public static Budget toBudget(DUBudget duBudget) {
        if (duBudget == null) {
            return null;
        }
        Budget budget = new Budget();
        budget.setBUDGET_ID(duBudget.getBudgetId());
        budget.setPROJECT_ID(duBudget.getProjectId());
        budget.setBUDGET_NUMBER(duBudget.getBudgetNumber());
        budget.setBUDGET_NAME(duBudget.getBudgetName());
        budget.setBUDGET_STATE(duBudget.getBudgetState());
        budget.setSTATION_PILE_COUNT(duBudget.getStationPileCount());
        budget.setUSER_COUNT(duBudget.getUserCount());
        return budget;
    }

    public static DUBudget toDUBudget(Budget budget) {
        if (budget == null) {
            return null;
        }
        DUBudget duBudget = new DUBudget();
        duBudget.setBudgetId(budget.getBUDGET_ID());
        duBudget.setProjectId(budget.getPROJECT_ID());
        duBudget.setBudgetNumber(budget.getBUDGET_NUMBER());
        duBudget.setBudgetName(budget.getBUDGET_NAME());
        duBudget.setBudgetState(budget.getBUDGET_STATE());
        duBudget.setStationPileCount(budget.getSTATION_PILE_COUNT());
        duBudget.setUserCount(budget.getUSER_COUNT());
        return duBudget;
    }

    public static List<Budget> toBudgets(List<DUBudget> duBudgets) {
        if (duBudgets == null) {
            return null;
        }
        List<Budget> budgets = new ArrayList<>();
        for (DUBudget duBudget : duBudgets) {
            budgets.add(toBudget(duBudget));
        }
        return budgets;
    }

    public static List<DUBudget> toDUBudgets(List<Budget> budgets) {
        if (budgets == null) {
            return null;
        }
        List<DUBudget> duBudgets = new ArrayList<>();
        for (Budget budget : budgets) {
            duBudgets.add(toDUBudget(budget));
        }
        return duBudgets;
    }

        /*---------------------------------------BranchAccept && DUBranchAccept-----------------------------------------*/

    public static BranchAccept toBranchAccept(DUBranchAccept duBranchAccept) {
        if (duBranchAccept == null) {
            return null;
        }
        BranchAccept branchAccept = new BranchAccept();
        branchAccept.setACCEPT_ID(duBranchAccept.getAcceptId());
        branchAccept.setSERVER_ID(duBranchAccept.getServerId());
        branchAccept.setPROJECT_ID(duBranchAccept.getProjectId());
        branchAccept.setBUDGET_ID(duBranchAccept.getBudgetId());
        branchAccept.setCHECK_SITUATION(duBranchAccept.getCheckSituation());
        branchAccept.setLEAK_SITUATION(duBranchAccept.getLeakSituation());
        branchAccept.setLEAK_REFORM(duBranchAccept.getLeakReform());
        branchAccept.setOPERATOR(duBranchAccept.getOperator());
        branchAccept.setOPERATE_TIME(duBranchAccept.getOperateTime());
        branchAccept.setLONGITUDE(duBranchAccept.getLongitude());
        branchAccept.setLATITUDE(duBranchAccept.getLatitude());
        branchAccept.setREMARK(duBranchAccept.getRemark());
        branchAccept.setUPLOAD(duBranchAccept.getUpload());
        branchAccept.setACCEPT_TYPE(duBranchAccept.getAcceptType());
        return branchAccept;
    }

    public static DUBranchAccept toDUBranchAccept(BranchAccept branchAccept) {
        if (branchAccept == null) {
            return null;
        }
        DUBranchAccept duBranchAccept = new DUBranchAccept();
        duBranchAccept.setAcceptId(branchAccept.getACCEPT_ID());
        duBranchAccept.setServerId(branchAccept.getSERVER_ID());
        duBranchAccept.setProjectId(branchAccept.getPROJECT_ID());
        duBranchAccept.setBudgetId(branchAccept.getBUDGET_ID());
        duBranchAccept.setCheckSituation(branchAccept.getCHECK_SITUATION());
        duBranchAccept.setLeakSituation(branchAccept.getLEAK_SITUATION());
        duBranchAccept.setLeakReform(branchAccept.getLEAK_REFORM());
        duBranchAccept.setOperator(branchAccept.getOPERATOR());
        duBranchAccept.setOperateTime(branchAccept.getOPERATE_TIME());
        duBranchAccept.setLongitude(branchAccept.getLONGITUDE());
        duBranchAccept.setLatitude(branchAccept.getLATITUDE());
        duBranchAccept.setRemark(branchAccept.getREMARK());
        duBranchAccept.setUpload(branchAccept.getUPLOAD());
        duBranchAccept.setAcceptType(branchAccept.getACCEPT_TYPE());
        return duBranchAccept;
    }

    public static List<BranchAccept> toBranchAccepts(List<DUBranchAccept> duBranchAccepts) {
        if (duBranchAccepts == null) {
            return null;
        }
        List<BranchAccept> branchAccepts = new ArrayList<>();
        for (DUBranchAccept duBranchAccept : duBranchAccepts) {
            branchAccepts.add(toBranchAccept(duBranchAccept));
        }
        return branchAccepts;
    }

    public static List<DUBranchAccept> toDUBranchAccepts(List<BranchAccept> branchAccepts) {
        if (branchAccepts == null) {
            return null;
        }
        List<DUBranchAccept> duBranchAccepts = new ArrayList<>();
        for (BranchAccept branchAccept : branchAccepts) {
            duBranchAccepts.add(toDUBranchAccept(branchAccept));
        }
        return duBranchAccepts;
    }

    /*---------------------------------------ProjectAccept && DUProjectAccept-----------------------------------------*/

    public static ProjectAccept toProjectAccept(DUProjectAccept duProjectAccept) {
        if (duProjectAccept == null) {
            return null;
        }
        ProjectAccept projectAccept = new ProjectAccept();
        projectAccept.setACCEPT_ID(duProjectAccept.getAcceptId());
        projectAccept.setSERVER_ID(duProjectAccept.getServerId());
        projectAccept.setPROJECT_ID(duProjectAccept.getProjectId());
        projectAccept.setBUDGET_ID(duProjectAccept.getBudgetId());
        projectAccept.setACCEPT_RESULT(duProjectAccept.getAcceptResult());
        projectAccept.setOPERATOR(duProjectAccept.getOperator());
        projectAccept.setOPERATE_TIME(duProjectAccept.getOperateTime());
        projectAccept.setLONGITUDE(duProjectAccept.getLongitude());
        projectAccept.setLATITUDE(duProjectAccept.getLatitude());
        projectAccept.setREMARK(duProjectAccept.getRemark());
        projectAccept.setUPLOAD(duProjectAccept.getUpload());
        return projectAccept;
    }

    public static DUProjectAccept toDUProjectAccept(ProjectAccept projectAccept) {
        if (projectAccept == null) {
            return null;
        }
        DUProjectAccept duProjectAccept = new DUProjectAccept();
        duProjectAccept.setAcceptId(projectAccept.getACCEPT_ID());
        duProjectAccept.setServerId(projectAccept.getSERVER_ID());
        duProjectAccept.setProjectId(projectAccept.getPROJECT_ID());
        duProjectAccept.setBudgetId(projectAccept.getBUDGET_ID());
        duProjectAccept.setAcceptResult(projectAccept.getACCEPT_RESULT());
        duProjectAccept.setOperator(projectAccept.getOPERATOR());
        duProjectAccept.setOperateTime(projectAccept.getOPERATE_TIME());
        duProjectAccept.setLongitude(projectAccept.getLONGITUDE());
        duProjectAccept.setLatitude(projectAccept.getLATITUDE());
        duProjectAccept.setRemark(projectAccept.getREMARK());
        duProjectAccept.setUpload(projectAccept.getUPLOAD());
        return duProjectAccept;
    }

    public static List<ProjectAccept> toProjectAccepts(List<DUProjectAccept> duProjectAccepts) {
        if (duProjectAccepts == null) {
            return null;
        }
        List<ProjectAccept> projectAccepts = new ArrayList<>();
        for (DUProjectAccept duProjectAccept : duProjectAccepts) {
            projectAccepts.add(toProjectAccept(duProjectAccept));
        }
        return projectAccepts;
    }

    public static List<DUProjectAccept> toDUProjectAccepts(List<ProjectAccept> projectAccepts) {
        if (projectAccepts == null) {
            return null;
        }
        List<DUProjectAccept> duProjectAccepts = new ArrayList<>();
        for (ProjectAccept projectAccept : projectAccepts) {
            duProjectAccepts.add(toDUProjectAccept(projectAccept));
        }
        return duProjectAccepts;
    }

    public static ConstructionAccept toConstructionAccept(DUConstructionAccept duConstructionAccept){
        if(duConstructionAccept == null){
            return null;
        }
        ConstructionAccept accept = new ConstructionAccept();
        accept.setACCEPT_ID(duConstructionAccept.getAcceptId());
        accept.setSERVER_ID(duConstructionAccept.getServerId());
        accept.setBUDGET_ID(duConstructionAccept.getBudgetId());
        accept.setPROJECT_ID(duConstructionAccept.getProjectId());
        accept.setCONSTRUCTION_PROGRAM(duConstructionAccept.getConstructionProgram());
        accept.setCONSTRUCTION_REFORM(duConstructionAccept.getConstructionReform());
        accept.setLATITUDE(duConstructionAccept.getLatitude());
        accept.setLONGITUDE(duConstructionAccept.getLongitude());
        accept.setOPERATE_TIME(duConstructionAccept.getOperateTime());
        accept.setOPERATOR(duConstructionAccept.getOperator());
        accept.setREMARK(duConstructionAccept.getRemark());
        accept.setUPLOAD(duConstructionAccept.getUpload());
        return accept;
    }

    public static DUConstructionAccept toDUConstructionAccept(ConstructionAccept accept){
        if(accept == null){
            return null;
        }
        DUConstructionAccept duConstructionAccept = new DUConstructionAccept();
        duConstructionAccept.setAcceptId(accept.getACCEPT_ID());
        duConstructionAccept.setServerId(accept.getSERVER_ID());
        duConstructionAccept.setBudgetId(accept.getBUDGET_ID());
        duConstructionAccept.setProjectId(accept.getPROJECT_ID());
        duConstructionAccept.setConstructionProgram(accept.getCONSTRUCTION_PROGRAM());
        duConstructionAccept.setConstructionReform(accept.getCONSTRUCTION_REFORM());
        duConstructionAccept.setLatitude(accept.getLATITUDE());
        duConstructionAccept.setLongitude(accept.getLONGITUDE());
        duConstructionAccept.setOperateTime(accept.getOPERATE_TIME());
        duConstructionAccept.setOperator(accept.getOPERATOR());
        duConstructionAccept.setRemark(accept.getREMARK());
        duConstructionAccept.setUpload(accept.getUPLOAD());
        return duConstructionAccept;
    }

    public static List<DUConstructionAccept> toDuConstructionAccepts(List<ConstructionAccept> constructionAccepts){
        if(constructionAccepts == null){
            return null;
        }
        List<DUConstructionAccept> duConstructionAccepts = new ArrayList<>();
        for(ConstructionAccept constructionAccept : constructionAccepts){
            duConstructionAccepts.add(toDUConstructionAccept(constructionAccept));
        }
        return duConstructionAccepts;
    }

    public static List<ConstructionAccept> toConstructionAccepts(List<DUConstructionAccept> duConstructionAccepts){
        if(duConstructionAccepts == null){
            return null;
        }
        List<ConstructionAccept> constructionAccepts = new ArrayList<>();
        for(DUConstructionAccept duConstructionAccept : duConstructionAccepts){
            constructionAccepts.add(toConstructionAccept(duConstructionAccept));
        }
        return constructionAccepts;
    }

}
