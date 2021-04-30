package com.example.emptyapplication

import com.example.localisationmaps.Constant
import com.example.localisationmaps.SessionBean
import com.example.localisationmaps.UserBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class WSUtils {
    companion object {
        val gson = Gson()

        fun updatePlace(user: UserBean) {
            val userInJson = gson.toJson(user)
             OkhttpUtils().sendPostOkHttpRequest("${Constant.URL}/updatePlace", userInJson)
        }


        fun getPlaces(): ArrayList<UserBean> {

            val userListJson =
                OkhttpUtils().sendGetOkHttpRequest("${Constant.URL}/places")

            val myType = object : TypeToken<List<UserBean>>() {}.type
            return gson.fromJson(userListJson, myType)
        }


        fun login(user: UserBean):SessionBean{
            val userInJson = gson.toJson(user)
            val sessionOk = OkhttpUtils().sendPostOkHttpRequest("${Constant.URL}/login", userInJson)
            val mySession = object : TypeToken<SessionBean>() {}.type
            return gson.fromJson(sessionOk,mySession)
        }

        fun subscribe(user: UserBean): SessionBean{
            val userInJson = gson.toJson(user)
            val sessionOk = OkhttpUtils().sendPostOkHttpRequest("${Constant.URL}/subscribe", userInJson)
            val mySession = object : TypeToken<SessionBean>() {}.type
            return gson.fromJson(sessionOk,mySession)
        }

    }

}