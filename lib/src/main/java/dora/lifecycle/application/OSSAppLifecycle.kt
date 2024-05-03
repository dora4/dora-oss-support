package dora.lifecycle.application

import android.app.Application
import android.content.Context
import dora.oss.DoraOSS

class OSSAppLifecycle : ApplicationLifecycleCallbacks {
    override fun attachBaseContext(base: Context) {
    }

    override fun onCreate(application: Application) {
        DoraOSS.create(application)
    }

    override fun onTerminate(application: Application) {
    }
}