package com.sh3h.dataprovider.data.entity.base;

/**
 * Created by dengzhimin on 2017/4/11.
 */
public class DUEntityResult<T> extends DUBaseResult{

    private T data;

    public DUEntityResult(int code, int statusCode, String message, T data) {
        super(code, statusCode, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
