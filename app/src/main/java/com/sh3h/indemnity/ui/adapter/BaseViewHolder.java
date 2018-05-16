package com.sh3h.indemnity.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dengzhimin on 2017/3/10.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;

    public BaseViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public ViewDataBinding getBinding(){
        return binding;
    }
}
