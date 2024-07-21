package com.kft.learnkmp.model.Ktor

import io.ktor.client.HttpClient


class IosKtorClient : KtorClient {
    override val KtorClient: HttpClient = HttpClient()
}
actual fun getKtorClientInterface(): KtorClient = IosKtorClient()