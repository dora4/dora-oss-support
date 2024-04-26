dora-oss-support
![Release](https://jitpack.io/v/dora4/dora-oss-support.svg)
--------------------------------

#### gradle依赖配置

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
    implementation("com.github.dora4:dora-oss-support:1.0")
}
```
