package com.sh3h.dataprovider.data.entity;


import java.util.ArrayList;
import java.util.List;

public class DUUpdateXmlFile implements Cloneable {
    public static class Application implements Cloneable {
        private int id;
        private String appName;
        private String appId;
        private String packageName;
        private int versionCode;
        private String versionName;
        private Data data;
        private boolean isInstalled;

        public Application() {

        }

        public Application(int id, String appName, String appId, String packageName,
                           int versionCode, String versionName) {
            this.id = id;
            this.appName = appName;
            this.appId = appId;
            this.packageName = packageName;
            this.versionCode = versionCode;
            this.versionName = versionName;
            this.isInstalled = false;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public synchronized Data getData() {
            return data;
        }

        public synchronized void setData(Data data) {
            this.data = data;
        }

        public boolean isInstalled() {
            return isInstalled;
        }

        public void setInstalled(boolean installed) {
            isInstalled = installed;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            Application application = (Application)super.clone();
            if (this.data != null) {
                application.data = (Data) this.data.clone();
            }
            return application;
        }
    }

//    public static class Config {
//        private List<Role> roles;
//
//        public List<Role> getRoles() {
//            return roles;
//        }
//
//        public void setRoles(List<Role> roles) {
//            this.roles = roles;
//        }
//    }

    public static class Role implements Cloneable {
        private int id;
        private String name;
        private int version;
        private String role;

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

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    public static class Data implements Cloneable {
        private int id;
        private String name;
        private int version;

        public Data() {

        }

        public Data(int id, String name, int version) {
            this.id = id;
            this.name = name;
            this.version = version;
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

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    private List<Application> applicationList;

    public DUUpdateXmlFile() {

    }

    public DUUpdateXmlFile(List<Application> applicationList) {
        this.applicationList = applicationList;
    }

    public synchronized List<Application> getApplicationList() {
        return applicationList;
    }

    public synchronized void setApplicationList(List<Application> applicationList) {
        this.applicationList = applicationList;
    }

    public DUUpdateXmlFile clone() throws CloneNotSupportedException {
        DUUpdateXmlFile duUpdateXmlFile = (DUUpdateXmlFile) super.clone();
        if (this.applicationList != null) {
            duUpdateXmlFile.applicationList = new ArrayList<>();
            for (Application application : this.applicationList) {
                duUpdateXmlFile.applicationList.add((Application)application.clone());
            }
        }

        return duUpdateXmlFile;
    }
}
