package com.kft.learnkmp.model.Ktor

import io.ktor.client.HttpClient


interface KtorClient {
    val KtorClient:HttpClient
}

expect fun getKtorClientInterface(): KtorClient