package com.sh3h.indemnity.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sh3h.indemnity.ui.base.ParentPresenter;

import java.util.List;

/**
 * Created by dengzhimin on 2017/3/9.
 */

public class BaseListAdapter<T, V extends ParentPresenter> extends BaseAdapter {

    private int layoutRes;
    private List<T> datas;
    private int dataVariableId;
    private V presenter;
    private int presenterVariableId;

    public BaseListAdapter(int layoutRes, List<T> datas, int dataVariableId){
        this.layoutRes = layoutRes;
        this.datas = datas;
        this.dataVariableId = dataVariableId;
    }
    public void setPresenter(V presenter, int presenterVariableId){
        this.presenter = presenter;
        this.presenterVariableId = presenterVariableId;
    }

    public void setDatas(List<T> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void addDatas(T data){
        this.datas.add(data);
        notifyDataSetChanged();
    }

    public void removeData(T data){
        this.datas.remove(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewDataBinding binding = null;
        if(convertView == null){
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutRes, parent, false);
        }else{
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setVariable(dataVariableId, datas.get(position));
        binding.setVariable(presenterVariableId, presenter);
        return binding.getRoot();
    }

}
