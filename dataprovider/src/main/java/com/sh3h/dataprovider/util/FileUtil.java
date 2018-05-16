package com.sh3h.dataprovider.util;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

public class FileUtil {
    /**
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file != null) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                for (File f : childFile) {
                    deleteFile(f);
                }
            }
        }
    }

    public static void deleteFile(File file, List<String> notDeleteFileNames){
        if (file != null) {
            if (file.isFile()) {
                boolean isDelete = true;
                for(String fileName : notDeleteFileNames){
                    if(fileName.equals(file.getName())){
                        isDelete = false;
                        break;
                    }
                }
                if(isDelete){
                    file.delete();
                }
            } else if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                for (File f : childFile) {
                    deleteFile(f, notDeleteFileNames);
                }
            }
        }
    }

    public static Bitmap readBitmap(String path){
        File file = new File(path);
        Bitmap bitmap = null;
        if(file.exists()){
            bitmap = BitmapFactory.decodeFile(path);
        }
        return bitmap;
    }

    public static void saveFile(String filePath, InputStream is){
        try {
            FileOutputStream os = new FileOutputStream(filePath);
            byte[] b = new byte[1024];
            while (is.read(b) != -1){
                os.write(b);
                os.flush();
            }
            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long getFolderSize(String folderPath){
        if(TextUtils.isEmpty(folderPath)){
            return 0L;
        }
        File folder = new File(folderPath);
        if(!folder.exists()){
            return 0L;
        }
        return getFolderSize(folder);
    }

    /**
     *
     * @param folderPath
     * @param notCalculateFileNames
     * @return
     */
    public static long getFolderSize(String folderPath, List<String> notCalculateFileNames){
        if(TextUtils.isEmpty(folderPath)){
            return 0L;
        }
        File folder = new File(folderPath);
        if(!folder.exists()){
            return 0L;
        }
        return getFolderSize(folder, notCalculateFileNames);
    }

    /**
     * 计算目录文件大小
     * @param folder
     * @return
     */
    public static long getFolderSize(File folder){
        long size = 0L;
        try {
            File[] files = folder.listFiles();
            for(File file : files){
                if(file.isDirectory()){
                    size += getFolderSize(file);
                }else{
                    size += file.length();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 计算目录文件大小
     * @param folder
     * @param notCalculateFileNames 文件不计算
     * @return
     */
    public static long getFolderSize(File folder, List<String> notCalculateFileNames){
        long size = 0L;
        try {
            File[] files = folder.listFiles();
            for(File file : files){
                if(file.isDirectory()){
                    size += getFolderSize(file, notCalculateFileNames);
                }else{
                    boolean isCalculate = true;
                    for(String fileName : notCalculateFileNames){
                        if(fileName.equals(file.getName())){
                            isCalculate = false;
                            break;
                        }
                    }
                    if(isCalculate){
                        size += file.length();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 文件大小格式化
     * @param size
     * @return
     */
    public static String formatSize(long size){
        double kSize = size/1024;
        if(kSize < 102.4){
            return (int)kSize + "K";
        }
        double mSize = kSize/1024;
        if(mSize < 102.4){
            BigDecimal mDecimal = new BigDecimal(mSize);
            return mDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "M";
        }
        double gSize = mSize/1024;
        if(gSize < 102.4){
            BigDecimal mDecimal = new BigDecimal(gSize);
            return mDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "G";
        }
        double tSize = gSize/1024;
        if(tSize < 102.4){
            BigDecimal mDecimal = new BigDecimal(tSize);
            return mDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "T";
        }
        return "";
    }
}
