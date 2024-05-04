package dora.oss

import android.content.Context
import com.alibaba.sdk.android.oss.ClientConfiguration
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.OSS
import com.alibaba.sdk.android.oss.OSSClient
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider
import com.alibaba.sdk.android.oss.model.GeneratePresignedUrlRequest
import com.alibaba.sdk.android.oss.model.GetObjectRequest
import com.alibaba.sdk.android.oss.model.GetObjectResult
import com.alibaba.sdk.android.oss.model.PutObjectRequest
import com.alibaba.sdk.android.oss.model.PutObjectResult
import dora.oss.OSSConfig.Companion.OSS_BUCKET_NAME
import dora.util.ManifestUtils
import kotlin.math.pow

object OSSHelper {

    @JvmStatic
    @JvmOverloads
    fun uploadFile(
        context: Context,
        objectKey: String,
        path: String,
        callback: OSSCompletedCallback<PutObjectRequest, PutObjectResult>?,
        progressCallback: OSSProgressCallback<PutObjectRequest>? = null
    ) {
        val bucketName = ManifestUtils.getApplicationMetadataValue(context, OSS_BUCKET_NAME)
        if (bucketName.equals("")) throw DoraOSSException(OSS_BUCKET_NAME)
        uploadFile(DoraOSS.getOSS(), bucketName, objectKey, path, callback, progressCallback)
    }

    @JvmStatic
    @JvmOverloads
    fun uploadFile(
        oss: OSS,
        bucketName: String,
        objectKey: String,
        path: String,
        callback: OSSCompletedCallback<PutObjectRequest, PutObjectResult>?,
        progressCallback: OSSProgressCallback<PutObjectRequest>? = null
    ) {
        val request = PutObjectRequest(
            bucketName,
            objectKey, path
        )
        request.progressCallback = progressCallback
        oss.asyncPutObject(request, callback)
    }

    @JvmStatic
    @JvmOverloads
    fun downloadFile(
        context: Context,
        objectKey: String, callback: OSSCompletedCallback<GetObjectRequest, GetObjectResult>,
        progressCallback: OSSProgressCallback<GetObjectRequest>? = null
    ) {
        val bucketName = ManifestUtils.getApplicationMetadataValue(context, OSS_BUCKET_NAME)
        if (bucketName.equals("")) throw DoraOSSException(OSS_BUCKET_NAME)
        downloadFile(DoraOSS.getOSS(), bucketName, objectKey, callback, progressCallback)
    }

    @JvmStatic
    @JvmOverloads
    fun downloadFile(
        oss: OSS,
        bucketName: String,
        objectKey: String, callback: OSSCompletedCallback<GetObjectRequest, GetObjectResult>,
        progressCallback: OSSProgressCallback<GetObjectRequest>? = null
    ) {
        val get = GetObjectRequest(bucketName, objectKey)
        get.setProgressListener(progressCallback)
        oss.asyncGetObject(get, callback)
    }

    @JvmStatic
    fun getOSSUrl(bucketName: String, objectKey: String): String {
        return getOSSUrl(DoraOSS.getOSS(), bucketName, objectKey)
    }

    @JvmStatic
    fun getOSSUrl(oss: OSS, bucketName: String, objectKey: String): String {
        return try {
            val urlRequest =
                GeneratePresignedUrlRequest(
                    bucketName, objectKey,
                    System.currentTimeMillis() + 10.0.pow(10.0).toLong()
                )
            oss.presignConstrainedObjectURL(urlRequest)
        } catch (e: ClientException) {
            ""
        }
    }

    @JvmStatic
    fun getOSSUrl(context: Context, objectKey: String): String {
        val bucketName = ManifestUtils.getApplicationMetadataValue(context, OSS_BUCKET_NAME)
        if (bucketName.equals("")) throw DoraOSSException(OSS_BUCKET_NAME)
        return getOSSUrl(bucketName, objectKey)
    }

    @JvmStatic
    internal fun createClient(
        context: Context,
        endPoint: String,
        credentialProvider: OSSCredentialProvider,
        config: ClientConfiguration
    ) : OSSClient {
        return OSSClient(context, endPoint, credentialProvider, config)
    }

    /**
     * 创建自定义的OSS客户端。
     */
    @JvmStatic
    fun createClient(
        context: Context,
        endPoint: String,
        accessKey: String,
        secretKey: String,
        securityToken: String = "",
        config: ClientConfiguration = ClientConfiguration()
    ) : OSSClient {
        return createClient(context, endPoint, OSSStsTokenCredentialProvider(
            accessKey,
            secretKey,
            securityToken
        ), config)
    }
}