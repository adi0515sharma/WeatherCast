package com.kft.learnkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform