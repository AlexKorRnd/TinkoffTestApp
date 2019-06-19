package com.example.core.utils

object ConstructPictureUrl {
    fun construct(prefixUrl: String?, screenSuffix: String?, imageName: String): String {
        return buildString {
            if (prefixUrl != null) {
                append(prefixUrl)
                append("/")
            }
            if (screenSuffix != null) {
                append(screenSuffix)
                append("/")
            }
            append(imageName)
        }
    }
}

