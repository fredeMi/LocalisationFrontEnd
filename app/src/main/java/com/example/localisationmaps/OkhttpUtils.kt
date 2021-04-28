package com.example.emptyapplication

import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request


class OkhttpUtils {
    val client = OkHttpClient()
    val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()


    fun sendGetOkHttpRequest(url: String): String {
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        return if (response.code !in 200..299) {
            throw Exception("Réponse du serveur incorrect : ${response.code}")
        } else {
            response.body?.string() ?: ""
        }
    }

    fun sendPostOkHttpRequest(url: String, paramJson: String): String {
        println("url : $url")
        //Corps de la requête
        val body = paramJson.toRequestBody(MEDIA_TYPE_JSON)
        //Création de la requete
        val request = Request.Builder().url(url).post(body).build()
        //Execution de la requête
        val response = client.newCall(request).execute()
        //Analyse du code retour
        return if (response.code !in 200..299) {
            throw Exception("Réponse du serveur incorrect : ${response.code}")
        }
        else {
        //Résultat de la requete
        //ATTENTION .string ne peut être appelée qu’une seule fois.
            response.body?.string() ?: ""
        }
    }


}