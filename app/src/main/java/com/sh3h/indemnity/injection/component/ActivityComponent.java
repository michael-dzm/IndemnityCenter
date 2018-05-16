package com.sh3h.indemnity.injection.component;


import com.sh3h.indemnity.injection.annotation.PerActivity;
import com.sh3h.indemnity.injection.module.ActivityModule;
import com.sh3h.indemnity.ui.accept.ProjectAcceptListActivity;
import com.sh3h.indemnity.ui.accept.branch.AcceptBranchLRActivity;
import com.sh3h.indemnity.ui.accept.construction.AcceptConstructionLRActivity;
import com.sh3h.indemnity.ui.accept.project.AcceptProjectLRActivity;
import com.sh3h.indemnity.ui.accept.budget.BudgetAcceptListActivity;
import com.sh3h.indemnity.ui.base.BaseActivity;
import com.sh3h.indemnity.ui.main.MainActivity;
import com.sh3h.indemnity.ui.material.address.AddressManagerActivity;
import com.sh3h.indemnity.ui.material.apply.MaterialApplyActivity;
import com.sh3h.indemnity.ui.material.confirm.MaterialConfirmActivity;
import com.sh3h.indemnity.ui.material.search.apply.info.MaterialApplyInformationActivity;
import com.sh3h.indemnity.ui.material.search.apply.MaterialApplySearchFragment;
import com.sh3h.indemnity.ui.material.search.confirm.info.MaterialConfirmInformationActivity;
import com.sh3h.indemnity.ui.material.search.confirm.MaterialConfirmSearchFragment;
import com.sh3h.indemnity.ui.material.search.MaterialSearchActivity;
import com.sh3h.indemnity.ui.multimedia.MultimediaFragment;
import com.sh3h.indemnity.ui.patrol.PatrolActivity;
import com.sh3h.indemnity.ui.patrol.entry.PatrolEntryFragment;
import com.sh3h.indemnity.ui.project.detail.ProjectDetailFragment;
import com.sh3h.indemnity.ui.project.list.ProjectListActivity;
import com.sh3h.indemnity.ui.project.restart.RestartProjectActivity;
import com.sh3h.indemnity.ui.project.restart.entry.RestartProjectEntryFragment;
import com.sh3h.indemnity.ui.patrol.search.SearchPatrolActivity;
import com.sh3h.indemnity.ui.project.start.StartProjectActivity;
import com.sh3h.indemnity.ui.project.start.entry.StartProjectEntryFragment;
import com.sh3h.indemnity.ui.project.start.search.StartProjectSearchActivity;
import com.sh3h.indemnity.ui.project.stop.StopProjectActivity;
import com.sh3h.indemnity.ui.project.stop.entry.StopProjectEntryFragment;
import com.sh3h.indemnity.ui.setting.SettingActivity;
import com.sh3h.indemnity.ui.upload.UploadManagerActivity;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 * 生命周期跟Activity一样
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(BaseActivity activity);
    void inject(MainActivity activity);
    void inject(StartProjectSearchActivity activity);
    void inject(StartProjectActivity activity);
    void inject(StopProjectActivity activity);
    void inject(RestartProjectActivity activity);
    void inject(ProjectListActivity activity);
    void inject(PatrolActivity activity);
    void inject(AddressManagerActivity activity);
    void inject(MaterialApplyActivity activity);
    void inject(MaterialConfirmActivity activity);
    void inject(ProjectAcceptListActivity activity);
    void inject(AcceptBranchLRActivity activity);
    void inject(AcceptProjectLRActivity activity);
    void inject(BudgetAcceptListActivity activity);
    void inject(StartProjectEntryFragment fragment);
    void inject(StopProjectEntryFragment fragment);
    void inject(RestartProjectEntryFragment fragment);
    void inject(PatrolEntryFragment fragment);
    void inject(MultimediaFragment fragment);
    void inject(SearchPatrolActivity activity);
    void inject(MaterialSearchActivity activity);
    void inject(MaterialApplySearchFragment fragment);
    void inject(MaterialConfirmSearchFragment fragment);
    void inject(MaterialApplyInformationActivity activity);
    void inject(MaterialConfirmInformationActivity activity);
    void inject(ProjectDetailFragment fragment);
    void inject(SettingActivity activity);
    void inject(UploadManagerActivity activity);
    void inject(AcceptConstructionLRActivity activity);
}