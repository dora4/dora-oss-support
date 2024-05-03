package dora.oss

import android.content.Context
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
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
        if (bucketName.equals("")) throw DoraOSSException(OSS_BUCKET_NAME, false)
        val request = PutObjectRequest(
            bucketName,
            objectKey, path
        )
        request.progressCallback = progressCallback
        OSSClient.getOSS().asyncPutObject(request, callback)
    }

    @JvmStatic
    @JvmOverloads
    fun downloadFile(
        context: Context,
        objectKey: String, callback: OSSCompletedCallback<GetObjectRequest, GetObjectResult>,
        progressCallback: OSSProgressCallback<GetObjectRequest>? = null
    ) {
        val bucketName = ManifestUtils.getApplicationMetadataValue(context, OSS_BUCKET_NAME)
        if (bucketName.equals("")) throw DoraOSSException(OSS_BUCKET_NAME, false)
        val get = GetObjectRequest(bucketName, objectKey)
        get.setProgressListener(progressCallback)
        OSSClient.getOSS().asyncGetObject(get, callback)
    }

    @JvmStatic
    fun getOSSUrl(context: Context, objectKey: String): String {
        return try {
            val bucketName = ManifestUtils.getApplicationMetadataValue(context, OSS_BUCKET_NAME)
            if (bucketName.equals("")) throw DoraOSSException(OSS_BUCKET_NAME, false)
            val urlRequest =
                GeneratePresignedUrlRequest(
                    bucketName, objectKey,
                    System.currentTimeMillis() + 10.0.pow(10.0).toLong()
                )
            OSSClient.getOSS().presignConstrainedObjectURL(urlRequest)
        } catch (e: ClientException) {
            ""
        }
    }
}