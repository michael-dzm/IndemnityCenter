package com.sh3h.dataprovider.data.local.config;


import android.content.Context;

import com.sh3h.dataprovider.data.local.config.BaseConfig;
import com.sh3h.dataprovider.injection.annotation.ApplicationContext;

import javax.inject.Inject;

public class MapConfig extends BaseConfig {
    /**
     * Map服务跟路径
     */
    public static final String PARAM_MAP_SERVER_URL = "map.server.url";

    /**
     * projection parameters
     */
    public static final String PARAM_COORDINATE_CONVERTING_EDTD = "coordinate.converting.edtd";
    public static final String PARAM_COORDINATE_CONVERTING_EDTM = "coordinate.converting.edtm";
    public static final String PARAM_COORDINATE_CONVERTING_EDTS = "coordinate.converting.edts";

    /**
     * offset parameters
     */
    public static final String PARAM_COORDINATE_CONVERTING_EDTDX = "coordinate.converting.edtdx";
    public static final String PARAM_COORDINATE_CONVERTING_EDTDY = "coordinate.converting.edtdy";
    public static final String PARAM_COORDINATE_CONVERTING_EDTDZ = "coordinate.converting.edtdz";

    /**
     * rotation parameters
     */
    public static final String PARAM_COORDINATE_CONVERTING_EDTRX = "coordinate.converting.edtrx";
    public static final String PARAM_COORDINATE_CONVERTING_EDTRY = "coordinate.converting.edtry";
    public static final String PARAM_COORDINATE_CONVERTING_EDTRZ = "coordinate.converting.edtrz";

    /**
     * scale factor
     */
    public static final String PARAM_COORDINATE_CONVERTING_EDTK = "coordinate.converting.edtk";

    public static final String PARAM_LONGITUDE_DEFAULT = "longitude.default";
    public static final String PARAM_LATITUDE_DEFAULT = "latitude.default";

    public MapConfig() {

    }


}
