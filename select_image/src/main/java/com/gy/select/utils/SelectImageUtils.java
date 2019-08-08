package com.gy.select.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.gy.select.MultiImageSelectorActivity;
import com.gy.select.R;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SelectImageUtils {

    /**
     * 调用图片选择器
     * @param context
     * @param showCamera 是否显示相机按钮
     * @param imageCount 选择图片数量
     * @param single     是否只选择一张
     * @param code      requestCode
     * @return
     */
    public static void select(Context context, boolean showCamera, int imageCount, boolean single, int code) {
        Intent intent = new Intent(context, MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, showCamera);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, imageCount);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, single ? MultiImageSelectorActivity.MODE_SINGLE : MultiImageSelectorActivity.MODE_MULTI);
        ((Activity)context).startActivityForResult(intent,code);
    }

    /**
     * 调用相机
     * @param context
     * @param requestCode
     */
    public static File camera(Context context, int requestCode) {
        File file = null;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            try {
                file = FileUtils.createTmpFile(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (file != null && file.exists()) {
                if (VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //通过FileProvider创建一个content类型的Uri
                    Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                } else {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                }
                ((Activity) context).startActivityForResult(intent, requestCode);
            } else {
                Toast.makeText(context, R.string.mis_error_image_not_exist, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, R.string.mis_msg_no_camera, Toast.LENGTH_SHORT).show();
        }
        return file;
    }

    /**
     * 解析intent 数据 返回已选择的图片集合
     * @param intent
     * @return
     */
    public static List<String> getData(Intent intent){
        if (intent ==null){
            return null;
        }
        return intent.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
    }
}
