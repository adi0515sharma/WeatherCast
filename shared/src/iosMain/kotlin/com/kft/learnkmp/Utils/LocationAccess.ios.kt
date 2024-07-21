package com.kft.learnkmp.Utils

import com.kft.learnkmp.Platform

class IosLocationPlatform : LocationAccess {
    override suspend fun getCurrentLocation(): Location? {
        return null
    }
}

actual fun getLocationAccess(): LocationAccess = IosLocationPlatform()