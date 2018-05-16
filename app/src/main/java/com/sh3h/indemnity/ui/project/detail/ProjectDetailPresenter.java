package com.sh3h.indemnity.ui.project.detail;

import android.databinding.ObservableField;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.indemnity.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengzhimin on 2017/3/8.
 */

public class ProjectDetailPresenter extends ParentPresenter<ProjectDetailMvpView> {

    public static final String TAG = ProjectDetailPresenter.class.getSimpleName();

    @Inject
    public ProjectDetailPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public String getProjectStateName(int projectState){
        String projectStateName = null;
        for(DUProject.State state : DUProject.State.values()){
            if(state.getValue() == projectState){
                projectStateName =  state.getName();
                break;
            }
        }
        return projectStateName;
    }
}
