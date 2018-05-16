package com.sh3h.indemnity.util;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dengzhimin on 2017/3/8.
 */

public class BindingUtils {

    @BindingConversion
    public static String parseDate(Date date){
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return f.format(date);
    }

    @BindingConversion
    public static String parseDate(long timeMillis){
        if(timeMillis == 0){
            return Constants.TEXT_EMPTY;
        }
        SimpleDateFormat f;
        if(timeMillis <= 1 * 60 * 60 * 1000){
            f = new SimpleDateFormat("mm:ss");
        }else{
            f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
        return f.format(timeMillis);
    }

    @BindingAdapter(value = {"image", "error"}, requireAll = false)
    public static void src(ImageView imageView, String imagePath, Drawable drawable){
        //显示缩略图
        if(imagePath.contains("http")){
            Picasso.with(imageView.getContext()).load(imagePath).fit().error(drawable).into(imageView);
        }else{
            Picasso.with(imageView.getContext()).load(new File(imagePath)).fit().error(drawable).into(imageView);
        }
    }
}
