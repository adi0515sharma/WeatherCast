package com.kft.learnkmp.model

import com.kft.learnkmp.Utils.ResponseHandler
import com.kft.learnkmp.model.LocationBasedResponse.LocationBasedResponse

data class WatchItemUI(val state : WatchEnum, val message : String?, val data : LocationBasedResponse?)

enum class WatchEnum{
    LOADING,
    ERROR,
    SUCCESS
}