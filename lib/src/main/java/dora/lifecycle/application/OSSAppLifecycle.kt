package dora.lifecycle.application

import android.app.Application
import android.content.Context
import dora.oss.OSSClient

class OSSAppLifecycle : ApplicationLifecycleCallbacks {
    override fun attachBaseContext(base: Context) {
    }

    override fun onCreate(application: Application) {
        OSSClient.init(application)
    }

    override fun onTerminate(application: Application) {
    }
}