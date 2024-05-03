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
import dora.util.ManifestUtils
import java.lang.IllegalStateException

object OSSClient {

    private var oss: OSS? = null
    private var codec: OSSCodec? = null
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
    fun init(codec: OSSCodec = OSSCodec(), conf: ClientConfiguration = ClientConfiguration(),
             enableLog: Boolean = false) {
        this.codec = codec
        this.conf = conf
        this.enableLog = enableLog
    }

    fun create(context: Context) {
        if (codec == null || conf == null) {
            throw IllegalStateException("Please call init method first.")
        }
        if (enableLog) {
            // write local log file, path is SDCard_path/OSSLog/logs.csv
            OSSLog.enableLog()
        }
        val accessKey = ManifestUtils.getApplicationMetadataValue(context, OSS_ACCESS_KEY)
        if (accessKey.equals("")) throw DoraOSSException(OSSConfig.OSS_ACCESS_KEY, true)
        val secretKey = ManifestUtils.getApplicationMetadataValue(context, OSS_SECRET_KEY)
        if (secretKey.equals("")) throw DoraOSSException(OSSConfig.OSS_SECRET_KEY, true)
        val endPoint = ManifestUtils.getApplicationMetadataValue(context, OSS_ENDPOINT)
        if (endPoint.equals("")) throw DoraOSSException(OSSConfig.OSS_ENDPOINT, false)
        val credentialProvider: OSSCredentialProvider = OSSStsTokenCredentialProvider(
            codec!!.decryptOSSMetadataValue(accessKey),
            codec!!.decryptOSSMetadataValue(secretKey),
            ""
        )
        oss = OSSClient(context, endPoint, credentialProvider, conf)
    }
}