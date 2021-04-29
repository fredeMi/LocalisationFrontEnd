package com.example.localisationmaps

import java.util.*


data class UserBean(val id: Long, val session_id:Int?, val lat: Double, val lon: Double, val pseudo: String, val pwd: String?, val timestamp: Date)

data class SessionBean(val success:Boolean,val session_id: Int)

data class CoordBean(val lat: Double, val lon: Double)

data class ErrorBean(var message:String)