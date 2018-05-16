package com.sh3h.indemnity.ui.patrol;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivityPatrolBinding;
import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.indemnity.ui.patrol.entry.PatrolEntryFragment;
import com.sh3h.indemnity.ui.project.detail.ProjectDetailFragment;
import com.sh3h.indemnity.util.Constants;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by dengzhimin on 2017/3/7.
 */

public class PatrolActivity extends ParentActivity implements PatrolMvpView {

    @Inject
    PatrolPresenter mPresenter;

    private DUProject mProject;

    private String mIntentFlag;

    private FragmentManager mFragmentManager;

    private ActivityPatrolBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_patrol);
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mBinding.setPresenter(mPresenter);
        Intent intent = getIntent();
        if(intent != null){
            mProject = intent.getParcelableExtra(Constants.INTENT_PARAM_PROJECT);
            mIntentFlag = intent.getStringExtra(Constants.INTENT_PARAM_FLAG);
        }
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        if(Constants.FLAG_DAILY_PATROL.equals(mIntentFlag)){
            mBinding.include.toolbar.setTitle(R.string.activity_daily_patrol);
        } else if (Constants.FLAG_INDEMNITYCENTER_PATROL.equals(mIntentFlag)){
            mBinding.include.toolbar.setTitle(R.string.activity_indemnity_canter_patrol);
        }
        setSupportActionBar(mBinding.include.toolbar);
        initFragment(savedInstanceState == null ? null : savedInstanceState.getString(Constants.OUTSTATE_FRAGMENT_NAME));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //当activity不正常退出时 记录当前fragment name、project number
        List<Fragment> fragments = mFragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(!fragment.isHidden()){
                outState.putString(Constants.OUTSTATE_FRAGMENT_NAME, fragment.getClass().getName());
            }
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onCheckedChanged(int checkedId) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragments(transaction);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.INTENT_PARAM_PROJECT, mProject);
        switch (checkedId){
            case R.id.rb_detail:
                ProjectDetailFragment detailFragment = (ProjectDetailFragment) mFragmentManager.findFragmentByTag(ProjectDetailFragment.class.getName());
                if(detailFragment == null){
                    detailFragment = new ProjectDetailFragment();
                    detailFragment.setArguments(bundle);
                    transaction.add(R.id.fragment_container, detailFragment, detailFragment.getClass().getName()).commit();
                }else{
                    transaction.show(detailFragment).commit();
                }
                break;
            case R.id.rb_entry:
                PatrolEntryFragment entryFragment = (PatrolEntryFragment) mFragmentManager.findFragmentByTag(PatrolEntryFragment.class.getName());
                if(entryFragment == null){
                    entryFragment = new PatrolEntryFragment();
                    bundle.putString(Constants.INTENT_PARAM_FLAG, mIntentFlag);
                    entryFragment.setArguments(bundle);
                    transaction.add(R.id.fragment_container, entryFragment, entryFragment.getClass().getName()).commit();
                }else{
                    transaction.show(entryFragment).commit();
                }
                break;
        }
    }

    /** init fragment */
    private void initFragment(String fragmentName) {
        if(mFragmentManager == null){
            mFragmentManager = getSupportFragmentManager();
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.INTENT_PARAM_PROJECT, mProject);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (fragmentName == null) {
            ProjectDetailFragment fragment = new ProjectDetailFragment();
            bundle.putString(Constants.INTENT_PARAM_FLAG, mIntentFlag);
            fragment.setArguments(bundle);
            transaction.add(R.id.fragment_container, fragment, fragment.getClass().getName()).commit();
            return;
        }
        if(fragmentName.equals(ProjectDetailFragment.class.getName())){
            ProjectDetailFragment fragment = new ProjectDetailFragment();
            fragment.setArguments(bundle);
            transaction.add(R.id.fragment_container, fragment, fragment.getClass().getName()).commit();
        }else if (fragmentName.equals(PatrolEntryFragment.class.getName())){
            PatrolEntryFragment fragment = new PatrolEntryFragment();
            bundle.putString(Constants.INTENT_PARAM_FLAG, mIntentFlag);
            fragment.setArguments(bundle);
            transaction.add(R.id.fragment_container, fragment, fragmentName.getClass().getName()).commit();
        }
    }

    /** hide all fragments */
    private void hideFragments(FragmentTransaction transaction){
        List<Fragment> fragments = mFragmentManager.getFragments();
        for(Fragment fragment : fragments){
            transaction.hide(fragment);
        }
    }
}
