package com.sh3h.indemnity.ui.project.stop;

import android.widget.CompoundButton;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.indemnity.ui.base.ParentPresenter;

import javax.inject.Inject;

/**
 * Created by dengzhimin on 2017/3/8.
 */

public class StopProjectPresenter extends ParentPresenter<StopProjectMvpView> {

    @Inject
    public StopProjectPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        if(isChecked){
            getMvpView().onCheckedChanged(buttonView.getId());
        }
    }
}
