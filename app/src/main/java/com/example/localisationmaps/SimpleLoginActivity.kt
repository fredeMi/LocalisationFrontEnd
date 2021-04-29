package com.example.localisationmaps

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import com.example.emptyapplication.WSUtils
import kotlinx.android.synthetic.main.activity_simple_login.*
import java.util.*

class SimpleLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_login)

    }

    fun OnBtnLoginClick(view: View) {
        var user = UserBean(0, 0.0, 0.0,  tvPseudo.text.toString(), tvPwd.text.toString(), Date())
        WSUtils.login(user)
    }

    fun OnBtnSubscribeClick(view: View) {


    }
}