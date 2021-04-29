package com.example.localisationmaps

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import com.example.emptyapplication.WSUtils
import kotlinx.android.synthetic.main.activity_simple_login.*
import java.text.DateFormat
import java.util.*
import kotlin.concurrent.thread

class SimpleLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_login)
    }


    fun OnBtnSubscribeClick(view: View) {


    }

    fun onBtnLoginClick(view: View) {
        println("********************************clic")
        var mydate = DateFormat.getDateInstance()
        val user = UserBean(0, null,0.0, 0.0,  tvPseudo.text.toString(), tvPwd.text.toString(), mydate)
        thread {
            val mySession = WSUtils.login(user)
            runOnUiThread { println("**************************"+mySession.session_id) }
        }
    }
}