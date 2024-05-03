package dora.oss

class DoraOSSException(manifestMetadataName: String)
    : RuntimeException("Please check if " +
        "'$manifestMetadataName' is configured correctly in the AndroidManifest.xml file.")