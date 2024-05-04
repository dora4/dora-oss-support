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
    implementation("com.github.dora4:dora-oss-support:1.3")
}
```

#### AndroidManifest配置

```xml
 <meta-data
            android:name="dora.lifecycle.config.OSSGlobalConfig"
            android:value="GlobalConfig"/>
 <meta-data
            android:name="OSS_ENDPOINT"
            android:value="你的OSS Endpoint"/>
 <meta-data
            android:name="OSS_BUCKET_NAME"
            android:value="你的OSS Bucket Name"/>
```

#### 开始使用
##### 初始化配置
在Application的attachBaseContext中调用DoraOSS.init()方法进行初始化。

##### API调用
```kotlin
OSSHelper.uploadFile()
OSSHelper.downloadFile()
OSSHelper.getOSSUrl()
```
通过DoraOSS.getOSS()获取com.alibaba.sdk.android.oss.OSS对象。

