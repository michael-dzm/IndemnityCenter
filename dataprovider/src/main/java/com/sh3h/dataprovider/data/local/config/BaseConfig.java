package com.sh3h.dataprovider.data.local.config;

import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class BaseConfig {
    private Properties mProperties;
    private boolean isRead;

    public BaseConfig() {
        mProperties = null;
        isRead = false;
    }

    /**
     * read the properties
     * @param path
     */
    public void readProperties(String path) {
        InputStream fis = null;
        mProperties = null;
        isRead = false;
        try {
            fis = new FileInputStream(path);
            Properties properties = new Properties();
            properties.load(fis);
            fis.close();
            fis = null;
            mProperties = properties;
            isRead = true;
        } catch (Exception e) {
            LogUtil.e("CH", "load system setting faild.", e);
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * write the properties
     * @param filePath
     */
    public boolean writeProperties(String filePath) {
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            mProperties.store(fos, "");
            fos.flush();
            fos.close();
            fos = null;
            return true;
        } catch (Exception e) {
            LogUtil.e("Ch", "load system setting faild.", e);
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    /**
     *
     * @param name
     * @return
     */
    public Object get(String name) {
        return getString(name);
    }

    /**
     *
     * @param name
     * @return
     */
    public String getString(String name) {
        if (mProperties != null) {
            return mProperties.getProperty(name);
        } else {
            return null;
        }
    }

    /**
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public int getInteger(String name, int defaultValue) {
        try {
            if (mProperties != null) {
                return Integer.parseInt(getString(name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultValue;
    }

    /**
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public double getDouble(String name, double defaultValue) {
        try {
            if (mProperties != null) {
                return Double.parseDouble(getString(name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultValue;
    }

    /**
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public boolean getBoolean(String name, boolean defaultValue) {
        try {
            if (mProperties != null) {
                return Boolean.parseBoolean(getString(name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultValue;
    }

    public long getLong(String name, long defaultValue){
        try{
            if (mProperties != null) {
                return Long.parseLong(getString(name));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return defaultValue;

    }

    /**
     *
     * @param name
     * @param value
     */
    public void set(String name, Object value) {
        if (mProperties == null) {
            mProperties = new Properties();
        }

        mProperties.setProperty(name, value == null ? TextUtil.EMPTY : value.toString());
    }

    /**
     *
     * @param name
     * @param value
     */
    public void set(String name, double value) {
        if (mProperties == null) {
            mProperties = new Properties();
        }

        mProperties.setProperty(name, String.valueOf(value));
    }

    /**
     *
     * @param name
     * @param value
     */
    public void set(String name, boolean value) {
        if (mProperties == null) {
            mProperties = new Properties();
        }

        mProperties.setProperty(name, String.valueOf(value));
    }

    /**
     *
     * @param name
     * @param value
     */
    public void set(String name, int value) {
        if (mProperties == null) {
            mProperties = new Properties();
        }

        mProperties.setProperty(name, String.valueOf(value));
    }

    /**
     *
     * @param name
     * @param value
     */
    public void set(String name, long value){
        if (mProperties == null) {
            mProperties = new Properties();
        }

        mProperties.setProperty(name, String.valueOf(value));
    }

    /**
     *
     * @param name
     * @param value
     */
    public void set(String name, String value) {
        if (mProperties == null) {
            mProperties = new Properties();
        }

        mProperties.setProperty(name, value);
    }
}
