package com.kft.learnkmp.model

import com.kft.learnkmp.model.Ktor.getKtorClientInterface

class APIConstants {

    companion object{
        val client = getKtorClientInterface()
        val BASE_URL = "https://api.openweathermap.org/"
        val appId = "2fb9beb24296a8010fbf05f1d8d58391"
        val limitOfSearchLocation = 10
    }


}