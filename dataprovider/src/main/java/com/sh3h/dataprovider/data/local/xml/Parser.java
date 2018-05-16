package com.sh3h.dataprovider.data.local.xml;


import com.sh3h.dataprovider.data.entity.DUFile;
import com.sh3h.dataprovider.data.entity.DUUpdateXmlFile;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.data.local.db.DbHelper;
import com.sh3h.dataprovider.util.FileUtil;
import com.sh3h.dataprovider.util.ZipUtil;
import com.sh3h.mobileutil.util.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Parser {
    private static final String TAG = "Parser";

    private final DbHelper mDbHelper;
    private final ConfigHelper mConfigHelper;
    private final XmlHelper mXmlHelper;

    @Inject
    public Parser(DbHelper dbHelper, ConfigHelper configHelper, XmlHelper xmlHelper) {
        mDbHelper = dbHelper;
        mConfigHelper = configHelper;
        mXmlHelper = xmlHelper;
    }

    /**
     * parser the file
     *
     * @param duFile
     * @return
     */
    public boolean parseFile(DUFile duFile) {
        LogUtil.i(TAG, "---parseFile---");

        try {
            File file = new File(duFile.getPath());
            if (!file.exists()) {
                return false;
            }

            File apksFolderPath = mConfigHelper.getApksFolderPath();
            if (!apksFolderPath.exists()) {
                apksFolderPath.mkdirs();
            }

            String outPathString = apksFolderPath.getPath();
            if (duFile.getFileType() == DUFile.FileType.DATA) {
                File updateFolderPath = mConfigHelper.getUpdateFolderPathByUser();
                if (!updateFolderPath.exists()) {
                    updateFolderPath.mkdirs();
                }

                outPathString = updateFolderPath.getPath();
            }
            ZipUtil.UnZipFolder(file.getPath(), outPathString);
            file.delete();

            switch (duFile.getFileType()) {
                case APK:
                    return save2UpdateXml(duFile);
                case DATA:
                    return save2UpdateXml(duFile);
                default:
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, "---parseFile---" + e.getMessage());
            return false;
        }
    }

    /**
     * @param duFile
     * @return
     */
    private boolean save2UpdateXml(DUFile duFile) {
        LogUtil.i(TAG, "---save2UpdateXml---");

        boolean found = false;
        DUUpdateXmlFile duUpdateXmlFileClone = mXmlHelper.getDuUpdateXmlFileClone();
        if ((duUpdateXmlFileClone != null) && (duUpdateXmlFileClone.getApplicationList() != null)) {
            List<DUUpdateXmlFile.Application> applicationList = duUpdateXmlFileClone.getApplicationList();
            for (DUUpdateXmlFile.Application application : applicationList) {
                if (application.getAppId().equals(duFile.getAppId())) {
                    switch (duFile.getFileType()) {
                        case APK:
                            if (application.getVersionCode() < duFile.getVersionCode()) {
                                application.setVersionCode(duFile.getVersionCode());
                                application.setVersionName(duFile.getVersionName());
                            }
                            break;
                        case DATA:
                            if (application.getData() != null) {
                                if (application.getData().getVersion() < duFile.getVersionCode()) {
                                    application.getData().setVersion(duFile.getVersionCode());
                                }
                            } else {
                                DUUpdateXmlFile.Data data = new DUUpdateXmlFile.Data(1,
                                        "Data", duFile.getVersionCode());
                                application.setData(data);
                            }
                            break;
                        default:
                            break;
                    }

                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            if (duFile.getFileType() == DUFile.FileType.APK) {
                if (duUpdateXmlFileClone == null) {
                    duUpdateXmlFileClone = new DUUpdateXmlFile();
                }

                int id = 1;
                List<DUUpdateXmlFile.Application> applicationList = duUpdateXmlFileClone.getApplicationList();
                if (applicationList == null) {
                    applicationList = new ArrayList<>();
                    duUpdateXmlFileClone.setApplicationList(applicationList);
                } else {
                    if (applicationList.size() > 0) {
                        id = applicationList.get(applicationList.size() - 1).getId() + 1;
                    }
                }

                applicationList.add(new DUUpdateXmlFile.Application(id, duFile.getAppName(),
                        duFile.getAppId(), duFile.getPackageName(), duFile.getVersionCode(),
                        duFile.getVersionName()));
            } else {
                return false;
            }
        }

        mXmlHelper.setDuUpdateXmlFile(duUpdateXmlFileClone);
        return mXmlHelper.saveDuUpdateXmlFile();
    }

    /**
     * @param zipFile
     * @param destFolder
     */
    private void deleteFiles(File zipFile, File destFolder) {
        try {
            FileUtil.deleteFile(zipFile);
            FileUtil.deleteFile(destFolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
