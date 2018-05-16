package com.sh3h.indemnity.ui.multimedia.brower;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.android.databinding.library.baseAdapters.BR;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivityMultimediaBrowerBinding;
import com.sh3h.indemnity.ui.adapter.BaseListAdapter;
import com.sh3h.indemnity.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengzhimin on 2017/6/14.
 */

public class MultimediaBrowerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private final static float FLING_MIN_DISTANCE = 100f;//滑动距离
    private final static float FLING_MIN_VELOCITY = 100f;//滑动速率

    private ActivityMultimediaBrowerBinding mBinding;

    private GestureDetector mGesture;

    private BaseListAdapter mAdapter;

    private int mSelectItemPosition;

    private List<DUMultiMedia> mMultiMedias;

    private List<String> mMultiMediaUrls;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_multimedia_brower);
        mGesture = new GestureDetector(this, new OnGestureListener());
        Intent intent = getIntent();
        if(intent != null){
            mSelectItemPosition = intent.getIntExtra(Constants.INTENT_PARAM_SELECTPOSITION, 0);
            mMultiMedias = intent.getParcelableArrayListExtra(Constants.INTENT_PARAM_MULTIMEDIAS);
            mMultiMediaUrls = intent.getStringArrayListExtra(Constants.INTENT_PARAM_MULTIMEDIA_URLS);
        }
        if(mMultiMedias != null && mMultiMedias.size() > 0){
            if(mMultiMediaUrls == null){
                mMultiMediaUrls = new ArrayList<>();
            }
            for(DUMultiMedia multiMedia : mMultiMedias){
                mMultiMediaUrls.add(multiMedia.getFilePath());
            }
        }
        if(mMultiMediaUrls == null || mMultiMediaUrls.size() == 0){
            finish();
            return;
        }
        mAdapter = new BaseListAdapter(R.layout.item_multimedia_brower, mMultiMediaUrls, BR.multimediaUrl);
        mBinding.setAdapter(mAdapter);
        mBinding.gallery.setOnItemClickListener(this);
        mBinding.setMultimediaUrl(mMultiMediaUrls.get(mSelectItemPosition));
        mBinding.gallery.setSelection(mSelectItemPosition);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGesture.onTouchEvent(event);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mBinding.setMultimediaUrl(mMultiMediaUrls.get(position));
    }

    private class OnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){//Fling right
                if(mSelectItemPosition > 0){//上一张图片
                    mSelectItemPosition --;
                    //选中上一张图片
                    mBinding.gallery.setSelection(mSelectItemPosition);
                    //主动触发OnItemClick事件
                    mBinding.gallery.performItemClick(mBinding.gallery.getChildAt(mSelectItemPosition), mSelectItemPosition,
                            mBinding.gallery.getItemIdAtPosition(mSelectItemPosition));
                }
            }else if(e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){//Fling left
                if(mSelectItemPosition < mBinding.gallery.getCount() - 1){//下一张图片
                    mSelectItemPosition ++;
                    //选中下一张图片
                    mBinding.gallery.setSelection(mSelectItemPosition);
                    //主动触发OnItemClick事件
                    mBinding.gallery.performItemClick(mBinding.gallery.getChildAt(mSelectItemPosition), mSelectItemPosition,
                            mBinding.gallery.getItemIdAtPosition(mSelectItemPosition));
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

}
