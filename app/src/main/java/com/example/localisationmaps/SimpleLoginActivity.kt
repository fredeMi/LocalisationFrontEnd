package com.example.localisationmaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.emptyapplication.WSUtils

class SimpleLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_login)
    }

    fun OnBtnLoginClick(view: View) {
//        WSUtils.login()
    }
}