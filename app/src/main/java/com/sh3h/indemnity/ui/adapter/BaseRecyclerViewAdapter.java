package com.sh3h.indemnity.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.indemnity.ui.base.ParentPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengzhimin on 2017/3/13.
 */

public class BaseRecyclerViewAdapter<T, V extends ParentPresenter> extends RecyclerView.Adapter<BaseViewHolder> implements Filterable{

    private List<T> datas;
    private V presenter;
    private int layoutRes;
    private int dataVariableId;
    private int presenterVariableId;
    //记录初始data
    private List<T> originalDatas;
    private ProjectFilter filter;

    public BaseRecyclerViewAdapter(int layoutRes, List<T> datas, int dataVariableId){
        this.layoutRes = layoutRes;
        this.datas = datas;
        this.dataVariableId = dataVariableId;
    }

    public void setPresenter(V presenter, int presenterVariableId){
        this.presenter = presenter;
        this.presenterVariableId = presenterVariableId;
    }

    public void setDatas(List<T> datas){
        this.originalDatas = this.datas = datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<T> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.getBinding().setVariable(dataVariableId, datas.get(position));
        holder.getBinding().setVariable(presenterVariableId, presenter);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new ProjectFilter();
        }
        return filter;
    }

    //工程筛选
    class ProjectFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterStr = constraint.toString().toUpperCase();
            FilterResults results = new FilterResults();
            if(TextUtils.isEmpty(constraint)){
                results.values = originalDatas;
                results.count = originalDatas.size();
                return results;
            }
            List<T> filterDatas = new ArrayList<>();
            for(T data : originalDatas){
                if(data instanceof DUProject){
                    DUProject project = (DUProject) data;
                    if(project.getProjectNumber().contains(filterStr)){
                        filterDatas.add(data);
                    }
                }else{
                    filterDatas.add(data);
                }
            }
            results.values = filterDatas;
            results.count = filterDatas.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            datas = (List<T>) results.values;
            notifyDataSetChanged();
        }
    }
}

