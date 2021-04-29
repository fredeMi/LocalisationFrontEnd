package com.example.localisationmaps


data class UserBean(val id: Long, val lat: Double, val lon: Double, val pseudo: String, val pwd: String?, val timestamp: Long)

data class ErrorBean(var message:String)