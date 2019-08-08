
## Android 图片选择器

* v1.0.0 图片选择器 可以选择图片文件夹
    
    
![图片](https://github.com/ymkmdf/ImageSelector/blob/master/391565232273_.pic.jpg?raw=true '图片选择器')

![图片](https://github.com/ymkmdf/ImageSelector/blob/master/401565232274_.pic.jpg?raw=true '图片选择器')

**图片选择器使用方法**
* 添加依赖
~~~ JAVA
    allprojects {
        repositories {
            ...
            maven { url 'https://www.jitpack.io' }
        }
    }
    
    dependencies {
        implementation 'com.github.ymkmdf:ImageSelector:v1.0.0'
    }
~~~

* 添加权限

~~~ XML
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
~~~


[Android 6.0 需要动态获取权限](https://github.com/ymkmdf/SimplePermissions/blob/master/README.md)



* 调用图片选择器
~~~ JAVA
//调用图片选择器 参数：Context  ,是否展示相机按钮  选择图片的数量, 是否只选择一张图片  requestCode
SelectImageUtils.select(context,showCamera,imageCount,single,SELECT_IMAGE);
~~~

* 调用相机
~~~ JAVA
//调用相机 
File mFile;
//此方法 返回一个File 保存相机拍照图片路径   参数： Context，requestCode
mFile = SelectImageUtils.camera(context,SELECT_CAMERA);
~~~

* 获取返回的图片
~~~ JAVA
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
~~~

* 清声明provider

在清单文件中添加  包名 改成自己的包名 
~~~~ XML
    <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="包名.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/provider_paths" />
        </provider>
~~~~

* 添加xml 文件

    在res目录中添加xml文件夹，在xml文件夹中添加provider_paths.xml文件

    provider_paths.xml 中的内容
    
~~~ XML

<?xml version="1.0" encoding="utf-8"?>
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android">
    <external-path name="external_files" path=""/>
</PreferenceScreen>
~~~

(*￣︶￣) (*￣︶￣)

