package com.sh3h.indemnity.ui.multimedia;

import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.indemnity.ui.base.MvpView;

/**
 * Created by dengzhimin on 2017/3/9.
 */
public interface MultimediaMvpView extends MvpView {

    void onError(int resId);

    void onSaveImage(boolean isSuccess);

    void onDeleteImage(DUMultiMedia media);

    void onItemClick(DUMultiMedia media);

}
