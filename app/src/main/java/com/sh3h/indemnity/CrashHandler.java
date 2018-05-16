package com.sh3h.indemnity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.sh3h.mobileutil.util.TextUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrashHandler implements UncaughtExceptionHandler {
    public static final String TAG = CrashHandler.class.getCanonicalName();

    // 系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    // 程序的Context对象
    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");

    /**
     * 保证只有一个实例
     */
    private CrashHandler() {
    }

    /**
     * 获取实例 ，单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null)
        {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        }
        else
        {
            try
            {
                Thread.sleep(3000);
            }
            catch (InterruptedException e)
            {
                Log.e(TAG, "error : ", e);
            }

            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null)
        {
            return false;
        }

        // 使用Toast来显示异常信息
        new Thread()
        {
            @Override
            public void run()
            {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉，程序出现异常。", Toast.LENGTH_LONG)
                        .show();
                Looper.loop();
            }
        }.start();

        // copy database
        backupDatabase(true);

        // 收集设备参数信息
        collectDeviceInfo(mContext);

        // 保存日志文件
        String str = saveCrashInfo2File(ex);
        Log.e(TAG, str);

        return false;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try
        {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null)
            {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }

        }
        catch (NameNotFoundException e)
        {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields)
        {
            try
            {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            }
            catch (Exception e)
            {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称,便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append("[" + key + ", " + value + "]\n");
        }

        sb.append("\n" + getStackTraceString(ex));

        try
        {
            String time = formatter.format(new Date());

            TelephonyManager mTelephonyMgr = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String imei = mTelephonyMgr.getDeviceId();
            if (TextUtils.isEmpty(imei))
            {
                imei = "unknownimei";
            }

            String fileName = "Log_" + time + "_" + imei + ".txt";

            File sdDir = null;

            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED))
                sdDir = Environment.getExternalStorageDirectory();

            File cacheDir = new File(sdDir + File.separator + "sh3h");
            if (!cacheDir.exists())
                cacheDir.mkdir();

            File filePath = new File(cacheDir + File.separator + fileName);

            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(sb.toString().getBytes());
            fos.close();

            return fileName;
        }
        catch (Exception e)
        {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }

    /**Z
     * 获取捕捉到的异常的字符串
     */
    public static String getStackTraceString(Throwable tr) {
        if (tr == null)
        {
            return "";
        }

        Throwable t = tr;
        while (t != null)
        {
            if (t instanceof UnknownHostException)
            {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        return sw.toString();
    }

    public static void backupDatabase(boolean isException) {
        File sdDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sdDir = Environment.getExternalStorageDirectory();
        }

        if (sdDir == null) {
            return;
        }

        File oldFile, newFile;
        if (isException) {
            oldFile = new File(sdDir, "sh3h/meterreading/data/main.cbj");
            newFile = new File(sdDir, "sh3h/main." + TextUtil.format(new Date(), "yyyyMMddHHmmss") + ".cbj");
        }
        else {
            oldFile = new File(sdDir, "sh3h/meterreading/data/main.cbj");
            newFile = new File(sdDir, "sh3h/meterreading/data/main_bk.cbj");
        }
        copyFile(oldFile.getPath(), newFile.getPath());
    }

    ///////////////////////复制文件//////////////////////////////
    /**
     * 复制单个文件
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath) {
        boolean isok = true;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    //System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();
            }
            else
            {
                isok = false;
            }
        }
        catch (Exception e) {
            // System.out.println("复制单个文件操作出错");
            // e.printStackTrace();
            isok = false;
        }

        return isok;
    }
}
