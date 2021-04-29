package com.example.localisationmaps

import java.util.*


data class UserBean(val id: Long, val lat: Double, val lon: Double, val pseudo: String, val pwd: String?, val timestamp: Date?)

data class CoordBean(val lat: Double, val lon: Double)

data class ErrorBean(var message:String)