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

    /**
     * 默认的OSS。
     */
    private var oss: OSS? = null
    private var accessKey: String = ""
    private var secretKey: String = ""
    private var securityToken: String = ""
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
    fun init(accessKey: String, secretKey: String, securityToken: String = "", conf: ClientConfiguration = ClientConfiguration(),
             enableLog: Boolean = false) {
        this.accessKey = accessKey
        this.secretKey = secretKey
        this.securityToken = securityToken
        this.conf = conf
        this.enableLog = enableLog
    }

    fun create(context: Context) {
        if (accessKey == "" || secretKey == "" || conf == null) {
            throw IllegalStateException("Please call init method first.")
        }
        if (enableLog) {
            // write local log file, path is SDCard_path/OSSLog/logs.csv
            OSSLog.enableLog()
        }
        val endPoint = ManifestUtils.getApplicationMetadataValue(context, OSS_ENDPOINT)
        if (endPoint.equals("")) throw DoraOSSException(OSS_ENDPOINT)
        val credentialProvider: OSSCredentialProvider = OSSStsTokenCredentialProvider(
            accessKey,
            secretKey,
            securityToken
        )
        oss = OSSHelper.createClient(context, endPoint, credentialProvider, conf!!)
    }
}