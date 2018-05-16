package com.sh3h.dataprovider.data.local.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.ExifInterface;

import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.injection.annotation.ApplicationContext;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by zhangjing on 2016/9/30.
 */
@Singleton
public class FileHelper {
    private static final String TAG = FileHelper.class.getName();
    private static final int PHOTO_LOW_QUALITY_WITH = 210;
    private static final int PHOTO_LOW_QUALITY_HEIGHT = 300;
    private static final int PHOTO_MIDDLE_QUALITY_WITH = 420;
    private static final int PHOTO_MIDDLE_QUALITY_HEIGHT = 600;
    private static final int PHOTO_HIGH_QUALITY_WITH = 630;
    private static final int PHOTO_HIGH_QUALITY_HEIGHT = 900;

    private final Context mContext;
    private final ConfigHelper mConfigHelper;

    @Inject
    public FileHelper(@ApplicationContext Context context, ConfigHelper configHelper) {
        mContext = context;
        mConfigHelper = configHelper;
    }

    /**
     * compress a picture and add a stamp on it
     * @param fileName
     * @param filePath
     * @return
     */
    public Observable<Boolean> compressImageAndAddStamp(final String fileName, final String filePath) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    if (TextUtil.isNullOrEmpty(fileName) || (filePath == null)) {
                        throw new NullPointerException("compressImageAndAddStamp contains null parameter");
                    }
                    if (!new File(filePath).exists()) {
                        subscriber.onError(new Throwable("file is not exists"));
                    }
                    //缩放的比例，缩放是很难按准备的比例进行缩放的，其值表明缩放的倍数，SDK中建议其值是2的指数
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    BitmapFactory.decodeFile(filePath, options);
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = calculateInSampleSize(options,
                            PHOTO_MIDDLE_QUALITY_WITH, PHOTO_MIDDLE_QUALITY_HEIGHT);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
                    if (bitmap == null) {
                        subscriber.onError(new Throwable("failure to decode the picture"));
                        return;
                    }
                    // rotate the bitmap
                    int degree = getBitmapDegree(filePath);
                    if (degree != 0) {
                        bitmap = rotateBitmapByDegree(bitmap, degree);
                    }
                    // add the stamp
                    bitmap = addWatermark(bitmap);
                    // save image
                    subscriber.onNext(saveImage(bitmap, filePath));
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    e.printStackTrace();
                    subscriber.onError(e);
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * 删除文件
     * @param path
     * @return
     */
    public Observable<Boolean> deleteFile(final String path) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (TextUtil.isNullOrEmpty(path)) {
                        throw new NullPointerException("param is null");
                    }

                    File file = new File(path);
                    if (file.exists()) {
                        subscriber.onNext(file.delete());
                    } else {
                        subscriber.onNext(false);
                    }
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    e.printStackTrace();
                    subscriber.onError(e);
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * 读取图片旋转的角度
     *
     * @param path
     * @return
     */
    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */

    private Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }

        if (returnBm == null) {
            returnBm = bm;
        }

        if (bm != returnBm) {
            bm.recycle();
        }

        return returnBm;
    }

    /**
     * 添加水印
     *
     * @param bitmap
     * @return
     */

    private Bitmap addWatermark(Bitmap bitmap) {
        Bitmap watermarkBitmap = null;
        try {
            int width = bitmap.getWidth();
            int hight = bitmap.getHeight();
            watermarkBitmap = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(watermarkBitmap);
            Paint photoPaint = new Paint();
            photoPaint.setDither(true);
            photoPaint.setFilterBitmap(true);
            Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            Rect dst = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            canvas.drawBitmap(bitmap, src, dst, photoPaint);
            Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
            textPaint.setTextSize(15.0f);
            textPaint.setTypeface(Typeface.DEFAULT_BOLD);
            textPaint.setColor(Color.RED);
            textPaint.setAlpha(150);
            String t = TextUtil.format(System.currentTimeMillis(), TextUtil.FORMAT_FULL_DATETIME);
            canvas.drawText(t, (float) (width * 0.19), (float) (hight * 0.03), textPaint);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            e.printStackTrace();
            watermarkBitmap = bitmap;
        }
        return watermarkBitmap;
    }

    /**
     * save the image
     *
     * @param bitmap
     */
    private boolean saveImage(Bitmap bitmap, String filePath) {
        if ((bitmap == null) || (filePath == null)) {
            return false;
        }
        try {
            File file = new File(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 压缩图片
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                             int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

}
