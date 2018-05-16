package com.sh3h.indemnity.ui.project.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.FragmentProjectDetailBinding;
import com.sh3h.indemnity.ui.base.BaseActivity;
import com.sh3h.indemnity.ui.base.ParentFragment;
import com.sh3h.indemnity.ui.project.start.StartProjectActivity;
import com.sh3h.indemnity.util.Constants;

import javax.inject.Inject;

/**
 * Created by dengzhimin on 2017/3/8.
 */

public class ProjectDetailFragment extends ParentFragment implements ProjectDetailMvpView {

    @Inject
    ProjectDetailPresenter mPresenter;

    private FragmentProjectDetailBinding mBinding;

    private DUProject mProject;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);
        Bundle bundle = getArguments();
        if(bundle != null){
            mProject = bundle.getParcelable(Constants.INTENT_PARAM_PROJECT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_detail, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.setProject(mProject);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected void lazyLoad() {

    }
}
