package com.sh3h.dataprovider.data.entity;


import java.util.List;

public class DUConfigXmlFile {
    public static final String SHOWN_AS_MAIN = "main";
    public static final String SHOWN_AS_SETTING = "setting";

    public static class Component implements Cloneable {
        private String functionKey;
        private String name;
        private String icon;
        private String packageName;
        private String activity;
        private String param;
        private int order;
        private String showAs;
        private int count;
        private String roles;
        private String extendedInfo;
        private boolean isValid;

        public Component() {

        }

        public Component(String functionKey,
                         String name,
                         String icon,
                         String packageName,
                         String activity,
                         String param,
                         int order,
                         String showAs,
                         int count,
                         String roles,
                         String extendedInfo,
                         boolean isValid) {
            this.functionKey = functionKey;
            this.name = name;
            this.icon = icon;
            this.packageName = packageName;
            this.activity = activity;
            this.param = param;
            this.order = order;
            this.showAs = showAs;
            this.count = count;
            this.roles = roles;
            this.extendedInfo = extendedInfo;
            this.isValid = isValid;
        }

        public String getFunctionKey() {
            return functionKey;
        }

        public void setFunctionKey(String functionKey) {
            this.functionKey = functionKey;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getShowAs() {
            return showAs;
        }

        public void setShownAs(String shownAs) {
            this.showAs = shownAs;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
        }

        public String getExtendInfo() {
            return extendedInfo;
        }

        public void setExtendInfo(String extendInfo) {
            this.extendedInfo = extendInfo;
        }

        public boolean isValid() {
            return isValid;
        }

        public void setValid(boolean valid) {
            isValid = valid;
        }

        @Override
        public String toString() {
            return "Component{" +
                    "functionKey=" + functionKey +
                    ", name='" + name + '\'' +
                    ", icon='" + icon + '\'' +
                    ", packageName='" + packageName + '\'' +
                    ", activity='" + activity + '\'' +
                    ", param='" + param + '\'' +
                    ", order=" + order +
                    ", showAs='" + showAs + '\'' +
                    ", count='" + count + '\'' +
                    ", roles='" + roles + '\'' +
                    ", extendedInfo='" + extendedInfo + '\'' +
                    ", isValid=" + isValid +
                    '}';
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        public Component getClone() {
            try {
                return (Component)clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return new Component(this.functionKey, this.name, this.icon, this.packageName,
                    this.activity, this.param, this.order, this.showAs, this.count,
                    this.roles, this.extendedInfo, this.isValid);
        }
    }

    private List<Component> componentList;

    public synchronized List<Component> getComponentList() {
        return componentList;
    }

    public synchronized void setComponentList(List<Component> componentList) {
        this.componentList = componentList;
    }
}
