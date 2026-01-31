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
    implementation("com.github.dora4:dora:1.3.57")
    implementation("com.github.dora4:dora-oss-support:1.5")
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

#### 初始化配置
在Application的attachBaseContext中调用DoraOSS.init()方法进行初始化。
```kotlin
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        val conf = ClientConfiguration()
        conf.connectionTimeout = 15 * 1000 // connection time out default 15s
        conf.socketTimeout = 10 * 300 * 1000 // socket timeout，default 5m
        conf.maxConcurrentRequest = 5 // synchronous request number，default 5
        conf.maxErrorRetry = 2 // retry，default 2
        DoraOSS.init(OSS_ACCESS_KEY, OSS_SECRET_KEY, conf = conf, enableLog = true)
    }
```

#### 开始使用

```kotlin
OSSHelper.uploadFile()
OSSHelper.downloadFile()
OSSHelper.getOSSUrl()
```
通过DoraOSS.getOSS()获取默认的com.alibaba.sdk.android.oss.OSS对象，也可以通过OSSHelper.createClient()方法自行创建一个。

