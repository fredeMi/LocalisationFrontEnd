package com.example.emptyapplication

import com.example.localisationmaps.Constant
import com.example.localisationmaps.UserBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class WSUtils {
    companion object {
        val gson = Gson()

//        fun getPlaces(user: UserBean): ArrayList<UserBean> {
//
//            val userInJson = gson.toJson(user)
//
//            val userListJson =
//                OkhttpUtils().sendPostOkHttpRequest("${Constant.URL}/getPlaces", userInJson)
//
//            val myType = object : TypeToken<List<UserBean>>() {}.type
//            return gson.fromJson(userListJson, myType)
//        }


        fun getPlacesTest(): ArrayList<UserBean> {

            val userListJson =
                OkhttpUtils().sendGetOkHttpRequest("${Constant.URL}/getPlaces")

            val myType = object : TypeToken<List<UserBean>>() {}.type
            return gson.fromJson(userListJson, myType)
        }

    }

}