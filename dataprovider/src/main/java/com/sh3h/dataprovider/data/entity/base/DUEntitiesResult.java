package com.sh3h.dataprovider.data.entity.base;

import java.util.List;

/**
 * Created by dengzhimin on 2017/4/11.
 */
public class DUEntitiesResult<T> extends DUBaseResult{

    private List<T> data;

    public DUEntitiesResult(int code, int statusCode, String message, List<T> data) {
        super(code, statusCode, message);
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
