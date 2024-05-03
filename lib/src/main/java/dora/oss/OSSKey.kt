package dora.oss

abstract class OSSKey {

    abstract fun accessKey() : String
    abstract fun secretKey() : String
}