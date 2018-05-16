package com.sh3h.dataprovider.data.local.xml;

import android.util.Xml;

import com.sh3h.dataprovider.data.entity.DUConfigXmlFile;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2016/6/21.
 */
public class ConfigXml {
    private static final String TAG = "ConfigXml";
    private static final String XML_ENCODING = "UTF-8";
    private static final String PROJECT_TAG = "project";
    private static final String COMPONENT_TAG = "component";
    private static final String FUNCTION_KEY_TAG = "functionKey";
    private static final String NAME_TAG = "name";
    private static final String ICON_TAG = "icon";
    private static final String PACKAGE_NAME_TAG = "packageName";
    private static final String ACTIVITY_TAG = "activity";
    private static final String PARAM_TAG = "param";
    private static final String ORDER_TAG = "order";
    private static final String SHOWN_AS_TAG = "shownAs";
    private static final String COUNT_TAG = "count";
    private static final String ROLES_TAG = "roles";
    private static final String EXTENDED_INFO_TAG = "extendedInfo";
    private static final String LINE_BREAK = "\n";
    private static final String LINE_SPACE = "    ";

    private final ConfigHelper mConfigHelper;
    private DUConfigXmlFile mDUConfigXmlFile;
    private boolean isRead;
    private File file;

    public ConfigXml(ConfigHelper configHelper) {
        this.mConfigHelper = configHelper;
        this.mDUConfigXmlFile = null;
        this.isRead = false;
    }

    public ConfigXml(ConfigHelper configHelper, File file) {
        this.mConfigHelper = configHelper;
        this.mDUConfigXmlFile = null;
        this.isRead = false;
        this.file = file;
    }

    public void read() {
        LogUtil.i(TAG, "---read---");

        mDUConfigXmlFile = null;
        FileInputStream fis = null;
        try {
            if ((file != null) && file.exists()) {
                fis = new FileInputStream(file);
                readConfigXml(fis);
                fis.close();
                fis = null;
            } else {
                File apksFolderPath = mConfigHelper.getApksFolderPath();
                File apkConfigXml = new File(apksFolderPath, ConfigHelper.FILE_CONFIG_XML);
                if (apkConfigXml.exists()) {
                    fis = new FileInputStream(apkConfigXml);
                    readConfigXml(fis);
                    fis.close();
                    fis = null;
                } else {
                    mDUConfigXmlFile = new DUConfigXmlFile();
                }
            }

            isRead = true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, "---read---" + e.getMessage());
            isRead = false;

            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean write() {
        LogUtil.i(TAG, "---write---");
        FileOutputStream fos = null;

        try {
            if (file != null) {
                return false;
            }

            if ((mDUConfigXmlFile != null)
                    && (mDUConfigXmlFile.getComponentList() != null)
                    && (mDUConfigXmlFile.getComponentList().size() > 0)) {
                File apksFolderPath = mConfigHelper.getApksFolderPath();
                if (!apksFolderPath.exists()) {
                    apksFolderPath.mkdirs();
                }
                File apkConfigXml = new File(apksFolderPath, ConfigHelper.FILE_CONFIG_XML);
                fos = new FileOutputStream(apkConfigXml);
                sortComponentList(mDUConfigXmlFile);
                writeConfigXML(fos);
                fos.close();
                fos = null;
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, "---write---" + e.getMessage());

            try {
                if (fos != null) {
                    fos.close();
                    fos = null;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }

    public DUConfigXmlFile getDuConfigXmlFile() {
        if (!isRead) {
            read();
        }

        return mDUConfigXmlFile;
    }

    public void setDuConfigXmlFile(DUConfigXmlFile duConfigXmlFile) {
        mDUConfigXmlFile = duConfigXmlFile;
        isRead = true;
    }

    private void readConfigXml(InputStream inputStream) throws Exception {
        mDUConfigXmlFile = new DUConfigXmlFile();
        List<DUConfigXmlFile.Component> componentList = new ArrayList<>();
        mDUConfigXmlFile.setComponentList(componentList);

        DUConfigXmlFile.Component component = null;
        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(inputStream, XML_ENCODING);
        int event = pullParser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if (COMPONENT_TAG.equals(pullParser.getName())) {
                        component = new DUConfigXmlFile.Component();
                    } else if (FUNCTION_KEY_TAG.equals(pullParser.getName())) {
                        if (component != null) {
                            component.setFunctionKey(pullParser.nextText());
                        }
                    } else if (NAME_TAG.equals(pullParser.getName())) {
                        if (component != null) {
                            component.setName(pullParser.nextText());
                        }
                    } else if (ICON_TAG.equals(pullParser.getName())) {
                        if (component != null) {
                            component.setIcon(pullParser.nextText());
                        }
                    } else if (PACKAGE_NAME_TAG.equals(pullParser.getName())) {
                        if (component != null) {
                            component.setPackageName(pullParser.nextText());
                        }
                    } else if (ACTIVITY_TAG.equals(pullParser.getName())) {
                        if (component != null) {
                            component.setActivity(pullParser.nextText());
                        }
                    } else if (PARAM_TAG.equals(pullParser.getName())) {
                        if (component != null) {
                            component.setParam(pullParser.nextText());
                        }
                    } else if (ORDER_TAG.equals(pullParser.getName())) {
                        if (component != null) {
                            component.setOrder(TextUtil.getInt(pullParser.nextText()));
                        }
                    } else if (SHOWN_AS_TAG.equals(pullParser.getName())) {
                        if (component != null) {
                            component.setShownAs(pullParser.nextText());
                        }
                    } else if (COUNT_TAG.equals(pullParser.getName())) {
                        if (component != null) {
                            component.setCount(TextUtil.getInt(pullParser.nextText()));
                        }
                    } else if (ROLES_TAG.equals(pullParser.getName())) {
                        if (component != null) {
                            component.setRoles(pullParser.nextText());
                        }
                    } else if (EXTENDED_INFO_TAG.equals(pullParser.getName())) {
                        if (component != null) {
                            component.setExtendInfo(pullParser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (COMPONENT_TAG.equals(pullParser.getName())) {
                        if (component != null) {
                            componentList.add(component);
                            component = null;
                        }
                    }
                    break;
            }
            event = pullParser.next();
        }
    }

    private void writeConfigXML(OutputStream out) throws Exception {
        XmlSerializer serializer = Xml.newSerializer();
        serializer.setOutput(out, XML_ENCODING);
        serializer.startDocument(XML_ENCODING, true);
        serializer.text(LINE_BREAK);
        serializer.startTag(null, PROJECT_TAG);
        serializer.text(LINE_BREAK);

        List<DUConfigXmlFile.Component> componentList = mDUConfigXmlFile.getComponentList();
        for (DUConfigXmlFile.Component component : componentList) {
            // <component>
            serializer.startTag(null, COMPONENT_TAG);
            serializer.text(LINE_BREAK);

            // <functionKey>functionKey</functionKey>
            serializer.startTag(null, FUNCTION_KEY_TAG);
            serializer.text(component.getFunctionKey());
            serializer.endTag(null, FUNCTION_KEY_TAG);
            serializer.text(LINE_BREAK);

            // <name>name</name>
            serializer.startTag(null, NAME_TAG);
            serializer.text(component.getName());
            serializer.endTag(null, NAME_TAG);
            serializer.text(LINE_BREAK);

            // <icon>icon</icon>
            serializer.startTag(null, ICON_TAG);
            serializer.text(component.getIcon());
            serializer.endTag(null, ICON_TAG);
            serializer.text(LINE_BREAK);

            // <packageName>packageName</packageName>
            serializer.startTag(null, PACKAGE_NAME_TAG);
            serializer.text(component.getPackageName());
            serializer.endTag(null, PACKAGE_NAME_TAG);
            serializer.text(LINE_BREAK);

            // <activity>activity</activity>
            serializer.startTag(null, ACTIVITY_TAG);
            serializer.text(component.getActivity());
            serializer.endTag(null, ACTIVITY_TAG);
            serializer.text(LINE_BREAK);

            // <param>param</param>
            serializer.startTag(null, PARAM_TAG);
            serializer.text(component.getParam());
            serializer.endTag(null, PARAM_TAG);
            serializer.text(LINE_BREAK);

            // <order>level</order>
            serializer.startTag(null, ORDER_TAG);
            serializer.text(String.valueOf(component.getOrder()));
            serializer.endTag(null, ORDER_TAG);
            serializer.text(LINE_BREAK);

            // <shownAs>shownAs</shownAs>
            serializer.startTag(null, SHOWN_AS_TAG);
            serializer.text(component.getShowAs());
            serializer.endTag(null, SHOWN_AS_TAG);
            serializer.text(LINE_BREAK);

            // <count>count</count>
            serializer.startTag(null, COUNT_TAG);
            serializer.text(String.valueOf(component.getCount()));
            serializer.endTag(null, COUNT_TAG);
            serializer.text(LINE_BREAK);

            // <roles>roles</roles>
            serializer.startTag(null, ROLES_TAG);
            serializer.text(component.getRoles());
            serializer.endTag(null, ROLES_TAG);
            serializer.text(LINE_BREAK);

            // <extendedInfo>extendedInfo</extendedInfo>
            serializer.startTag(null, EXTENDED_INFO_TAG);
            serializer.text(component.getExtendInfo());
            serializer.endTag(null, EXTENDED_INFO_TAG);
            serializer.text(LINE_BREAK);

            // </component>
            serializer.endTag(null, COMPONENT_TAG);
            serializer.text(LINE_BREAK);
        }
        serializer.endTag(null, PROJECT_TAG);
        serializer.endDocument();
        out.flush();
    }

    private void sortComponentList(DUConfigXmlFile duConfigXmlFile) {
        List<DUConfigXmlFile.Component> componentList = duConfigXmlFile.getComponentList();
        Collections.sort(componentList, new Comparator<DUConfigXmlFile.Component>() {
            @Override
            public int compare(DUConfigXmlFile.Component lhs, DUConfigXmlFile.Component rhs) {
                return lhs.getOrder() - rhs.getOrder();
            }
        });
    }
}
