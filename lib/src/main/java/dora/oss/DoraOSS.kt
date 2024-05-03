package dora.oss

import android.content.Context
import com.alibaba.sdk.android.oss.ClientConfiguration
import com.alibaba.sdk.android.oss.OSS
import com.alibaba.sdk.android.oss.OSSClient
import com.alibaba.sdk.android.oss.common.OSSLog
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider
import dora.oss.OSSConfig.Companion.OSS_ENDPOINT
import dora.util.ManifestUtils
import java.lang.IllegalStateException

object DoraOSS {

    private var oss: OSS? = null
    private var key: OSSKey? = null
    private var conf: ClientConfiguration? = null
    private var enableLog: Boolean = false

    @JvmStatic
    fun getOSS() : OSS {
        if (oss == null) {
            throw IllegalStateException("GlobalConfig is required.")
        }
        return oss!!
    }

    /**
     * 在[android.app.Application.attachBaseContext]中调用。
     */
    @JvmStatic
    @JvmOverloads
    fun init(key: OSSKey, conf: ClientConfiguration = ClientConfiguration(),
             enableLog: Boolean = false) {
        this.key = key
        this.conf = conf
        this.enableLog = enableLog
    }

    fun create(context: Context) {
        if (key == null || conf == null) {
            throw IllegalStateException("Please call init method first.")
        }
        if (enableLog) {
            // write local log file, path is SDCard_path/OSSLog/logs.csv
            OSSLog.enableLog()
        }
        val endPoint = ManifestUtils.getApplicationMetadataValue(context, OSS_ENDPOINT)
        if (endPoint.equals("")) throw DoraOSSException(OSS_ENDPOINT)
        val credentialProvider: OSSCredentialProvider = OSSStsTokenCredentialProvider(
            key!!.accessKey(),
            key!!.secretKey(),
            ""
        )
        oss = OSSClient(context, endPoint, credentialProvider, conf)
    }
}