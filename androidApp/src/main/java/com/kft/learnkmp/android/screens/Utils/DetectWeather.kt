package com.kft.learnkmp.android.screens.Utils

import com.kft.learnkmp.android.R

object DetectWeather {

    fun getWeather(icon : String?) : Int{
        if(icon == null){
            return R.drawable.clear_sky
        }
        return when (icon.substring(0,2)){

            "02" -> {
                R.drawable.few_clouds
            }
            "03" -> {
                R.drawable.scattered_clouds
            }
            "04" -> {
                R.drawable.broken_clouds
            }
            "09" -> {
                R.drawable.shower_rain
            }
            "10" -> {
                R.drawable.rain
            }
            "11" -> {
                R.drawable.thunder
            }
            "13" -> {
                R.drawable.snowy
            }
            "50" -> {
                R.drawable.mist
            }
            else -> {
                R.drawable.clear_sky
            }
        }
    }
}