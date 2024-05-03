dora-oss-support
![Release](https://jitpack.io/v/dora4/dora-oss-support.svg)
--------------------------------

#### Gradle依赖配置

```kotlin
// 添加以下代码到项目根目录下的build.gradle.kts
allprojects {
    repositories {
        maven { setUrl("https://jitpack.io") }
    }
}
dependencies {
    // 扩展包必须在有主框架dora的情况下使用
    implementation("com.github.dora4:dora:1.2.1")
    implementation("com.github.dora4:dora-oss-support:1.1")
}
```

#### AndroidManifest配置

```xml
 <meta-data
            android:name="dora.lifecycle.config.OSSGlobalConfig"
            android:value="GlobalConfig"/>
 <meta-data
            android:name="OSS_ENDPOINT"
            android:value="未加密的OSS Endpoint"/>
 <meta-data
            android:name="OSS_BUCKET_NAME"
            android:value="为加密的OSS Bucket Name"/>
 <meta-data
            android:name="OSS_ACCESS_KEY"
            android:value="加密后的OSS Access Key"/>
 <meta-data
            android:name="OSS_SECRET_KEY"
            android:value="加密后的OSS Secret Key"/>
```

#### 开始使用
##### 初始化配置
在Application的attachBaseContext中调用OSSClient.init()方法进行初始化。
如果init()方法不传自定义的OSSCodec对象，会有安全隐患。

##### API调用
```kotlin
OSSHelper.uploadFile()
OSSHelper.downloadFile()
OSSHelper.getOSSUrl()
```
通过OSSClient.getOSS()获取com.alibaba.sdk.android.oss.OSS对象。

