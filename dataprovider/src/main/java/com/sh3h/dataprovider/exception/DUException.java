package com.sh3h.dataprovider.exception;


public enum DUException {
    PARAM_NULL(1, "param is null"),
    PARAM_ERROR(2, "param is error"),
    RETURN_NULL(3, "return is null"),
    DB_ERROR(4, "db is error");

    private int id;
    private String name;

    DUException(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DUException toDUException(String name) {
        switch (name) {
            case "param is null":
                return PARAM_NULL;
            case "param is error":
                return PARAM_ERROR;
            case "return is null":
                return RETURN_NULL;
            case "db is error":
                return DB_ERROR;
            default:
                return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
