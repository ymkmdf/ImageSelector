package com.gy.imageselector;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gy.select.utils.SelectImageUtils;
import com.shop.qlt.mypermissions.MPermission;
import com.shop.qlt.mypermissions.annotation.OnMPermissionDenied;
import com.shop.qlt.mypermissions.annotation.OnMPermissionGranted;
import com.shop.qlt.mypermissions.annotation.OnMPermissionNeverAskAgain;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView mButton;
    ImageView mImageView;
    private static final int SELECT_IMAGE =23423;
    private static final int SELECT_CAMERA =23421;
    private File mFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.button);
        mImageView = findViewById(R.id.image);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelector(MainActivity.this,true,5,true);
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera();
            }
        });

        requestPermissions();
    }

    public void imageSelector(Context context, boolean showCamera, int imageCount, boolean single) {
        SelectImageUtils.select(context,showCamera,imageCount,single,SELECT_IMAGE);
    }

    public void camera(){
        mFile = SelectImageUtils.camera(this,SELECT_CAMERA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (resultCode == Activity.RESULT_OK){//结果是否OK
            if (requestCode == SELECT_IMAGE){  //判断是不是选择图片的 code
                //image path 集合
                List<String> path = SelectImageUtils.getData(intent);
                if (path !=null) {
                    for (String p : path) {
                        //设置图片
                    }
                }
                //设置图片
            }else if (requestCode == SELECT_CAMERA){ //判断是不是调用相机的 code
                if (mFile!=null){
                    String imagePath = mFile.getPath();
                    //设置图片
                }
            }
        }
    }

    private void requestPermissions(){
        MPermission.with(this).addRequestCode(23231).permissions(Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE).request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermission.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    @OnMPermissionDenied(23231)
    public void onMpermission(){
        Toast.makeText(this,"失败了",Toast.LENGTH_SHORT).show();
    }

    @OnMPermissionGranted(23231)
    public void onMPermissionGranted(){
        Toast.makeText(this,"成功了",Toast.LENGTH_SHORT).show();
    }

    @OnMPermissionNeverAskAgain(23231)
    public void onMPermissionNeverAskAgain(){
        Toast.makeText(this,"拒绝失败了",Toast.LENGTH_SHORT).show();
    }


}
