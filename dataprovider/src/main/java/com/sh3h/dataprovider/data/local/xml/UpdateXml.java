package com.sh3h.dataprovider.data.local.xml;


import android.util.Xml;

import com.sh3h.dataprovider.data.entity.DUUpdateXmlFile;
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
import java.util.List;

public class UpdateXml {
    private static final String TAG = "UpdateXml";
    private static final String XML_ENCODING = "UTF-8";
    private static final String PROJECT_TAG = "project";
    private static final String APPLICATION_TAG = "application";
    private static final String DATA_TAG = "data";
    private static final String ID_TAG = "id";
    private static final String APP_NAME_TAG = "appName";
    private static final String APP_ID_TAG = "appId";
    private static final String PACKAGE_NAME_TAG = "packageName";
    private static final String NAME_TAG = "name";
    private static final String VERSION_TAG = "version";
    private static final String VERSION_CODE_TAG = "versionCode";
    private static final String VERSION_NAME_TAG = "versionName";
    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_APK_VALUE = "apk";
    private static final String ATTRIBUTE_DATA_VALUE = "data";
    private static final String LINE_BREAK = "\n";
    private static final String LINE_SPACE = "    ";

    private final ConfigHelper mConfigHelper;
    private DUUpdateXmlFile mDuUpdateXmlFile;
    private boolean isRead;

    public UpdateXml(ConfigHelper configHelper) {
        this.mConfigHelper = configHelper;
        this.mDuUpdateXmlFile = null;
        this.isRead = false;
    }

    public void read() {
        LogUtil.i(TAG, "---read---");

        mDuUpdateXmlFile = null;
        FileInputStream fis = null;
        try {
            File updateFolderPath = mConfigHelper.getUpdateFolderPathByUser();
            File apkUpdateXml = new File(updateFolderPath, ConfigHelper.FILE_UPDATE_XML);
            if (apkUpdateXml.exists()) {
                fis = new FileInputStream(apkUpdateXml);
                readUpdateXml(fis);
                fis.close();
                fis = null;
            } else {
                mDuUpdateXmlFile = new DUUpdateXmlFile();
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
            File updateFolderPath = mConfigHelper.getUpdateFolderPathByUser();
            if (!updateFolderPath.exists()) {
                updateFolderPath.mkdirs();
            }

            File apkUpdateXml = new File(updateFolderPath, ConfigHelper.FILE_UPDATE_XML);

            if ((mDuUpdateXmlFile != null)
                    && (mDuUpdateXmlFile.getApplicationList() != null)
                    && (mDuUpdateXmlFile.getApplicationList().size() > 0)) {
                fos = new FileOutputStream(apkUpdateXml);
                writeUpdateXML(fos);
                fos.close();
                fos = null;
                return true;
            } else {
                return !apkUpdateXml.exists() || apkUpdateXml.delete();
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

    public DUUpdateXmlFile getDuUpdateXmlFile() {
        if (!isRead) {
            read();
        }

        return mDuUpdateXmlFile;
    }

    public void setDuUpdateXmlFile(DUUpdateXmlFile duUpdateXmlFile) {
        mDuUpdateXmlFile = duUpdateXmlFile;
        isRead = true;
    }

    private void readUpdateXml(InputStream inputStream) throws Exception {
        mDuUpdateXmlFile = new DUUpdateXmlFile();
        List<DUUpdateXmlFile.Application> applicationList = new ArrayList<>();
        mDuUpdateXmlFile.setApplicationList(applicationList);

        DUUpdateXmlFile.Application application = null;
        DUUpdateXmlFile.Data data = null;
        String tag = null;

        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(inputStream, XML_ENCODING);
        int event = pullParser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if (APPLICATION_TAG.equals(pullParser.getName())) {
                        application = new DUUpdateXmlFile.Application();
                        tag = APPLICATION_TAG;
                    } else if (DATA_TAG.equals(pullParser.getName())) {
                        if ((tag != null) && (tag.equals(APPLICATION_TAG))) {
                            data = new DUUpdateXmlFile.Data();
                            tag = DATA_TAG;
                        } else {
                            tag = null;
                        }
                    } else if (ID_TAG.equals(pullParser.getName())) {
                        if (tag != null) {
                            if (tag.equals(APPLICATION_TAG)) {
                                application.setId(TextUtil.getInt(pullParser.nextText()));
                            } else if (tag.equals(DATA_TAG)) {
                                if (data != null) {
                                    data.setId(TextUtil.getInt(pullParser.nextText()));
                                }
                            }
                        }
                    } else if (APP_NAME_TAG.equals(pullParser.getName())) {
                        if (tag != null && tag.equals(APPLICATION_TAG)) {
                            application.setAppName(TextUtil.getString(pullParser.nextText()));
                        }
                    } else if (APP_ID_TAG.equals(pullParser.getName())) {
                        if (tag != null && tag.equals(APPLICATION_TAG)) {
                            application.setAppId(TextUtil.getString(pullParser.nextText()));
                        }
                    } else if (PACKAGE_NAME_TAG.equals(pullParser.getName())) {
                        if (tag != null && tag.equals(APPLICATION_TAG)) {
                            application.setPackageName(TextUtil.getString(pullParser.nextText()));
                        }
                    } else if (NAME_TAG.equals(pullParser.getName())) {
                        if (tag != null && tag.equals(DATA_TAG)) {
                            if (data != null) {
                                data.setName(pullParser.nextText());
                            }
                        }
                    } else if (VERSION_CODE_TAG.equals(pullParser.getName())) {
                        if (tag != null && tag.equals(APPLICATION_TAG)) {
                            application.setVersionCode(TextUtil.getInt(pullParser.nextText()));
                        }
                    } else if (VERSION_NAME_TAG.equals(pullParser.getName())) {
                        if (tag != null && tag.equals(APPLICATION_TAG)) {
                            application.setVersionName(TextUtil.getString(pullParser.nextText()));
                        }
                    } else if (VERSION_TAG.equals(pullParser.getName())) {
                        if (tag != null && tag.equals(DATA_TAG)) {
                            if (data != null) {
                                data.setVersion(TextUtil.getInt(pullParser.nextText()));
                            }
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (APPLICATION_TAG.equals(pullParser.getName())) {
                        if (application != null) {
                            application.setData(data);
                            applicationList.add(application);
                        }
                    }
                    break;
            }
            event = pullParser.next();
        }
    }

    private void writeUpdateXML(OutputStream out) throws Exception {
        XmlSerializer serializer = Xml.newSerializer();
        serializer.setOutput(out, XML_ENCODING);
        serializer.startDocument(XML_ENCODING, true);
        serializer.text(LINE_BREAK);
        serializer.startTag(null, PROJECT_TAG);
        serializer.text(LINE_BREAK);

        List<DUUpdateXmlFile.Application> applicationList = mDuUpdateXmlFile.getApplicationList();
        //DUUpdateXmlFile.Config config;
        DUUpdateXmlFile.Data data;
        for (DUUpdateXmlFile.Application application : applicationList) {
            // <application
            serializer.startTag(null, APPLICATION_TAG);

            // name="apk"
            serializer.attribute(null, ATTRIBUTE_NAME, ATTRIBUTE_APK_VALUE);
            serializer.text(LINE_BREAK);

            // <id>id</id>
            serializer.startTag(null, ID_TAG);
            serializer.text(String.valueOf(application.getId()));
            serializer.endTag(null, ID_TAG);
            serializer.text(LINE_BREAK);

            // <appName>app name</appName>
            serializer.startTag(null, APP_NAME_TAG);
            serializer.text(TextUtil.getString(application.getAppName()).trim());
            serializer.endTag(null, APP_NAME_TAG);
            serializer.text(LINE_BREAK);

            // <appId> app id</appId>
            serializer.startTag(null, APP_ID_TAG);
            serializer.text(TextUtil.getString(application.getAppId()).trim());
            serializer.endTag(null, APP_ID_TAG);
            serializer.text(LINE_BREAK);

            // <packageName> packageName </packageName>
            serializer.startTag(null, PACKAGE_NAME_TAG);
            serializer.text(TextUtil.getString(application.getPackageName()).trim());
            serializer.endTag(null, PACKAGE_NAME_TAG);
            serializer.text(LINE_BREAK);

            // <versionCode>versionCode</versionCode>
            serializer.startTag(null, VERSION_CODE_TAG);
            serializer.text(String.valueOf(application.getVersionCode()));
            serializer.endTag(null, VERSION_CODE_TAG);
            serializer.text(LINE_BREAK);

            // <versionName>versionName</versionName>
            serializer.startTag(null, VERSION_NAME_TAG);
            serializer.text(TextUtil.getString(application.getVersionName()).trim());
            serializer.endTag(null, VERSION_NAME_TAG);
            serializer.text(LINE_BREAK);

            // data
            data = application.getData();
            if (data != null) {
                // <data
                serializer.startTag(null, DATA_TAG);

                // name="data">
                serializer.attribute(null, ATTRIBUTE_NAME, ATTRIBUTE_DATA_VALUE);
                serializer.text(LINE_BREAK);

                // <id>id</id>
                serializer.startTag(null, ID_TAG);
                serializer.text(String.valueOf(data.getId()));
                serializer.endTag(null, ID_TAG);
                serializer.text(LINE_BREAK);

                // <name>name</name>
                serializer.startTag(null, NAME_TAG);
                serializer.text(TextUtil.getString(data.getName()).trim());
                serializer.endTag(null, NAME_TAG);
                serializer.text(LINE_BREAK);

                // <version>version</version>
                serializer.startTag(null, VERSION_TAG);
                serializer.text(String.valueOf(data.getVersion()));
                serializer.endTag(null, VERSION_TAG);
                serializer.text(LINE_BREAK);

                // </data>
                serializer.endTag(null, DATA_TAG);
                serializer.text(LINE_BREAK);
            }

            serializer.endTag(null, APPLICATION_TAG);
            serializer.text(LINE_BREAK);
        }
        serializer.endTag(null, PROJECT_TAG);
        serializer.endDocument();
        out.flush();
    }
}
