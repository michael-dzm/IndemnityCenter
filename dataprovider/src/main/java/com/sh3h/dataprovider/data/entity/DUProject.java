package com.sh3h.dataprovider.data.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.sh3h.dataprovider.BR;



/**
 * Created by dengzhimin on 2017/3/8.
 */

public class DUProject extends BaseObservable implements Parcelable{

    private Long projectId;//工程ID

    private String projectNumber;//工程编号

    private String projectName;//工程名称

    private String constructionTeam;//施工队

    private String constructionUnit;//施工单位

    private String office;//办事处

    private String address;//街道

    private long startTime;//开工日期

    private long endTime;//完工时间

    private int state;//工程状态 1新建工程 2相关计划已提交 3编制合同 4开工 5停工 6完工 7送审 8确认

    private String remark;//备注

    private int constructionAcceptCount;//施工方案验收合格数

    private int strengthAcceptCount; //强度试验合格数

    private int airtightAcceptCount; //气密试验合格数

    private int projectAcceptCount; //工程合格数

    private int budgetTotalCount; //预算总数



    public enum State{
        DEFAULT(3, "编制合同"),//编制合同
        START(4, "开工"),//开工
        STOP(5, "停工"),//停工
        RESTART(6, "复工"),//复工
        FINISH(7, "完工");//完工

        private int value;
        private String name;

        State(int value, String name){
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public DUProject(){}

    public DUProject(String projectNumber, String projectName, String constructionTeam){
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        this.constructionTeam = constructionTeam;
    }

    public DUProject(Long projectId, String projectNumber, String projectName, String constructionTeam, String constructionUnit, String office,
                     String address, long startTime, long endTime, int state, String remark) {
        this.projectId = projectId;
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        this.constructionTeam = constructionTeam;
        this.constructionUnit = constructionUnit;
        this.office = office;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
        this.remark = remark;
    }

    protected DUProject(Parcel in) {
        projectId = in.readLong();
        projectNumber = in.readString();
        projectName = in.readString();
        constructionTeam = in.readString();
        constructionUnit = in.readString();
        office = in.readString();
        address = in.readString();
        startTime = in.readLong();
        endTime = in.readLong();
        state = in.readInt();
        remark = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(projectId);
        dest.writeString(projectNumber);
        dest.writeString(projectName);
        dest.writeString(constructionTeam);
        dest.writeString(constructionUnit);
        dest.writeString(office);
        dest.writeString(address);
        dest.writeLong(startTime);
        dest.writeLong(endTime);
        dest.writeInt(state);
        dest.writeString(remark);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DUProject> CREATOR = new Creator<DUProject>() {
        @Override
        public DUProject createFromParcel(Parcel in) {
            return new DUProject(in);
        }

        @Override
        public DUProject[] newArray(int size) {
            return new DUProject[size];
        }
    };

    @Bindable
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
        notifyPropertyChanged(BR.projectId);
    }

    @Bindable
    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
        notifyPropertyChanged(BR.projectNumber);
    }

    @Bindable
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
        notifyPropertyChanged(BR.projectName);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public String getConstructionTeam() {
        return constructionTeam;
    }

    public void setConstructionTeam(String constructionTeam) {
        this.constructionTeam = constructionTeam;
        notifyPropertyChanged(BR.constructionTeam);
    }

    @Bindable
    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
        notifyPropertyChanged(BR.startTime);
    }

    @Bindable
    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
        notifyPropertyChanged(BR.endTime);
    }

    @Bindable
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyPropertyChanged(BR.state);
    }

    @Bindable
    public String getConstructionUnit() {
        return constructionUnit;
    }

    public void setConstructionUnit(String constructionUnit) {
        this.constructionUnit = constructionUnit;
        notifyPropertyChanged(BR.constructionUnit);
    }

    @Bindable
    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
        notifyPropertyChanged(BR.office);
    }

    @Bindable
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
        notifyPropertyChanged(BR.remark);
    }

    @Bindable
    public int getConstructionAcceptCount() {
        return constructionAcceptCount;
    }

    public void setConstructionAcceptCount(int constructionAcceptCount) {
        this.constructionAcceptCount = constructionAcceptCount;
        notifyPropertyChanged(BR.constructionAcceptCount);
    }

    @Bindable
    public int getStrengthAcceptCount() {
        return strengthAcceptCount;
    }

    public void setStrengthAcceptCount(int strengthAcceptCount) {
        this.strengthAcceptCount = strengthAcceptCount;
        notifyPropertyChanged(BR.strengthAcceptCount);
    }

    @Bindable
    public int getAirtightAcceptCount() {
        return airtightAcceptCount;
    }

    public void setAirtightAcceptCount(int airtightAcceptCount) {
        this.airtightAcceptCount = airtightAcceptCount;
        notifyPropertyChanged(BR.airtightAcceptCount);
    }

    @Bindable
    public int getProjectAcceptCount() {
        return projectAcceptCount;
    }

    public void setProjectAcceptCount(int projectAcceptCount) {
        this.projectAcceptCount = projectAcceptCount;
        notifyPropertyChanged(BR.projectAcceptCount);
    }

    @Bindable
    public int getBudgetTotalCount() {
        return budgetTotalCount;
    }

    public void setBudgetTotalCount(int budgetTotalCount) {
        this.budgetTotalCount = budgetTotalCount;
        notifyPropertyChanged(BR.budgetTotalCount);
    }
}
