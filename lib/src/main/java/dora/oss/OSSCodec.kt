package dora.oss

open class OSSCodec {

    /**
     * 解密AndroidManifest.xml文件中metadata配置的value。[original]为加密后待解密的值，通过这个方法进行解密。
     */
    open fun decryptOSSMetadataValue(original: String) : String {
        return original
    }
}