package com.sh3h.indemnity.ui.multimedia;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.util.FileUtil;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.FragmentMultimediaBinding;
import com.sh3h.indemnity.ui.adapter.BaseListAdapter;
import com.sh3h.indemnity.ui.base.BaseActivity;
import com.sh3h.indemnity.ui.base.ParentFragment;
import com.sh3h.indemnity.ui.multimedia.brower.MultimediaBrowerActivity;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

/**
 * 多媒体
 * 这里只处理图片拍照，压缩，显示，删除等图片操作；不处理任何与业务逻辑相关的操作
 * Created by dengzhimin on 2017/3/9.
 */
public class MultimediaFragment extends ParentFragment implements MultimediaMvpView {

    public static final String TAG = MultimediaFragment.class.getName();

    public static final int REQUEST_CODE_CAMERA = 0x0000001;
    public static final int REQUEST_CODE_ALBUM = 0x0000002;

    @Inject
    MultimediaPresenter mPresenter;

    @Inject
    ConfigHelper mConfigHelper;

    private String mImagePath;
    private String mImageName;

    private BaseListAdapter mAdapter;

    private FragmentMultimediaBinding mBinding;

    private CameraCallBack mCallBack;

    private List<DUMultiMedia> mMultiMedias = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_multimedia, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new BaseListAdapter(R.layout.item_multimedia, mMultiMedias, BR.multimedia);
        mAdapter.setPresenter(mPresenter, BR.presenter);
        mBinding.setAdapter(mAdapter);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (mImageName == null) {
                        LogUtil.e(TAG, "image name is null");
                        return;
                    }
                    if (mImagePath == null) {
                        LogUtil.e(TAG, "image path is null");
                        return;
                    }
                    File file = new File(mImagePath);
                    if (!file.exists()) {
                        LogUtil.e(TAG, String.format("image file %s is not exists", mImageName));
                        return;
                    }
                    //图片压缩保存
                    mPresenter.saveImage(mImageName, mImagePath);
                }
                break;
            case REQUEST_CODE_ALBUM:
                createImageFile();
                if (resultCode == RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    ContentResolver resolver = getContext().getContentResolver();
                    try {
                        FileUtil.saveFile(mImagePath, resolver.openInputStream(uri));
                        //图片压缩保存
                        mPresenter.saveImage(mImageName, mImagePath);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /**
     * 创建图片名称和地址
     */
    public void createImageFile() {
        File folder = mConfigHelper.getImageFolderFile();
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //图片名称以当前时间毫秒命名
        mImageName = String.format("%s.jpg", TextUtil.format(System.currentTimeMillis(), TextUtil.FORMAT_DATE_SECOND));
        mImagePath = new File(folder, mImageName).getPath();
    }

    @Override
    public void onError(int resId) {
        ApplicationsUtil.showMessage(getActivity(), R.string.text_toast_image_compress_save_failed);
    }

    @Override
    public void onSaveImage(boolean isSuccess) {
        if (!isSuccess) {
            onError(R.string.text_toast_image_compress_save_failed);
        }
        //reset gridview
        mAdapter.addDatas(new DUMultiMedia(mImageName, mImagePath));
        //save success to callback
        if (mCallBack != null) {
            mCallBack.onAddImage(mImageName, mImagePath);
        }
    }

    @Override
    public void onDeleteImage(final DUMultiMedia media) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.text_dialog_title_prompt));
        builder.setMessage(getString(R.string.text_dialog_content_sure_delete_image));
        builder.setNegativeButton(getString(R.string.text_dialog_button_cancel), null);
        builder.setPositiveButton(getString(R.string.text_dialog_button_delete),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.removeData(media);
                        new File(media.getFilePath()).delete();
                        //delete to callback
                        if (mCallBack != null) {
                            mCallBack.onDeleteImage(media.getFileName(), media.getFilePath());
                        }
                    }
                });
        builder.show();
    }

    @Override
    public void onItemClick(DUMultiMedia media) {
        Intent intent = new Intent(getContext(), MultimediaBrowerActivity.class);
        intent.putParcelableArrayListExtra(Constants.INTENT_PARAM_MULTIMEDIAS, (ArrayList<? extends Parcelable>) mMultiMedias);
        intent.putExtra(Constants.INTENT_PARAM_SELECTPOSITION, mMultiMedias.indexOf(media));
        startActivity(intent);
    }

    public void selectTakePhoto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.text_dialog_title_prompt));
        builder.setItems(R.array.take_photo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    takePhoto();
                } else if (i == 1) {
                    openAlbum();
                }
            }
        });
        builder.setNegativeButton(getString(R.string.text_dialog_button_cancel), null);
        builder.show();
    }

    /**
     * 拍照
     */
    public void takePhoto() {
        try {
            File folder = mConfigHelper.getImageFolderFile();
            if (!folder.exists()) {
                folder.mkdirs();
            }
            //图片名称以当前时间毫秒命名
            mImageName = String.format("%s.jpg", TextUtil.format(System.currentTimeMillis(), TextUtil.FORMAT_DATE_SECOND));
            File file = new File(folder, mImageName);
            mImagePath = file.getPath();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){//7.0以上
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }else{
                if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext(), "com.sh3h.indemnity.fileprovider", file));
                    startActivityForResult(intent, 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        }
    }

    /**
     * 打开图库
     */
    public void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_ALBUM);
    }

    /**
     * 清除图片
     */
    public void clearImage(){
        mMultiMedias.clear();
        if(mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }
    }

    public void notifyMultiMedia(List<DUMultiMedia> multiMedias){
        mMultiMedias.clear();
        mMultiMedias.addAll(multiMedias);
        if(mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }
    }

    public void addMultiMedia(DUMultiMedia multiMedia){
        mMultiMedias.add(multiMedia);
        if(mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setCallBack(CameraCallBack callBack) {
        mCallBack = callBack;
    }

    /**
     * 拍照回调接口
     */
    public interface CameraCallBack {
        void onAddImage(String imageName, String imagePath);

        void onDeleteImage(String imageName, String imagePath);
    }

}
