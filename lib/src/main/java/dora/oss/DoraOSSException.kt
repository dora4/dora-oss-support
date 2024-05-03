package dora.oss

class DoraOSSException(manifestMetadataName: String, needEncrypt: Boolean)
    : RuntimeException(if (needEncrypt) "Please check whether the encrypted '$manifestMetadataName'" +
        " is configured correctly in the AndroidManifest.xml file." else "Please check if " +
        "'$manifestMetadataName' is configured correctly in the AndroidManifest.xml file.")