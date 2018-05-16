package com.sh3h.dataprovider.data.local.config;


public class OtherConfig extends BaseConfig {
    public static final String READING_START_DATE = "reading.start.date";
    public static final String READING_END_DATE = "reading.end.date";
    public static final int READING_READING_START_DATE = 1;
    public static final int READING_READING_END_DATE = 31;

    public OtherConfig() {
        set(READING_START_DATE, READING_READING_START_DATE);
        set(READING_END_DATE, READING_READING_END_DATE);
    }
}
