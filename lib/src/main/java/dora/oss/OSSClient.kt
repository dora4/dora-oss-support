package dora.oss

import android.content.Context
import com.alibaba.sdk.android.oss.ClientConfiguration
import com.alibaba.sdk.android.oss.OSS
import com.alibaba.sdk.android.oss.OSSClient
import com.alibaba.sdk.android.oss.common.OSSLog
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider
import dora.oss.OSSConfig.Companion.OSS_ACCESS_KEY
import dora.oss.OSSConfig.Companion.OSS_ENDPOINT
import dora.oss.OSSConfig.Companion.OSS_SECRET_KEY
import java.lang.IllegalStateException

object OSSClient {

    var oss: OSS? = null
        private set

    fun getOSS() : OSS {
        if (oss == null) {
            throw IllegalStateException("please call init method first.")
        }
        return oss!!
    }

    fun init(context: Context) {
        val conf = ClientConfiguration()
        conf.connectionTimeout = 15 * 1000 // connction time out default 15s
        conf.socketTimeout = 10 * 300 * 1000 // socket timeout，default 5m
        conf.maxConcurrentRequest = 5 // synchronous request number，default 5
        conf.maxErrorRetry = 2 // retry，default 2
        OSSLog.enableLog() //write local log file ,path is SDCard_path\OSSLog\logs.csv
        val credentialProvider: OSSCredentialProvider = OSSStsTokenCredentialProvider(
            OSS_ACCESS_KEY,
            OSS_SECRET_KEY,
            ""
        )
        oss = OSSClient(context, OSS_ENDPOINT, credentialProvider, conf)
    }
}