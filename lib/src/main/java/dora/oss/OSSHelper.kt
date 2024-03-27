package dora.oss

import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
import com.alibaba.sdk.android.oss.model.GeneratePresignedUrlRequest
import com.alibaba.sdk.android.oss.model.GetObjectRequest
import com.alibaba.sdk.android.oss.model.GetObjectResult
import com.alibaba.sdk.android.oss.model.PutObjectRequest
import com.alibaba.sdk.android.oss.model.PutObjectResult
import dora.oss.OSSConfig.Companion.OSS_BUCKET_NAME
import kotlin.math.pow

object OSSHelper {

    @JvmOverloads
    fun uploadFile(
        objectKey: String,
        path: String,
        callback: OSSCompletedCallback<PutObjectRequest, PutObjectResult>?,
        progressCallback: OSSProgressCallback<PutObjectRequest>? = null
    ) {
        val put = PutObjectRequest(
            OSS_BUCKET_NAME,
            objectKey, path
        )
        put.progressCallback = progressCallback
        OSSClient.getOSS().asyncPutObject(put, callback)
    }

    @JvmOverloads
    fun downloadFile(
        objectKey: String, callback: OSSCompletedCallback<GetObjectRequest, GetObjectResult>,
        progressCallback: OSSProgressCallback<GetObjectRequest>? = null
    ) {
        val get = GetObjectRequest(OSS_BUCKET_NAME, objectKey)
        get.setProgressListener(progressCallback)
        OSSClient.getOSS().asyncGetObject(get, callback)
    }

    @JvmStatic
    @Throws(ClientException::class)
    fun getOSSUrl(objectKey: String): String {
        val urlRequest =
            GeneratePresignedUrlRequest(
                OSS_BUCKET_NAME, objectKey,
                System.currentTimeMillis() + 10.0.pow(10.0).toLong()
            )
        return OSSClient.getOSS().presignConstrainedObjectURL(urlRequest)
    }
}