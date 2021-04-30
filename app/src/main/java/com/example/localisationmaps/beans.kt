package com.example.localisationmaps

import java.util.*


data class UserBean(val sessionId:Int?, val lat: Double?, val lon: Double?, val pseudo: String?, val pwd: String?)

data class SessionBean(val success:Boolean,val sessionId: Int)

data class CoordBean(val lat: Double, val lon: Double)

data class ErrorBean(var message:String)