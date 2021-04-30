package com.example.localisationmaps

import android.content.Intent
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


    fun onBtnLoginClick(view: View) {
        println("********************************clic")
        val user = UserBean(null,null, null,  tvPseudo.text.toString(), tvPwd.text.toString())
        thread {
            val mySession = WSUtils.login(user)
            runOnUiThread { println("**************************"+mySession.session_id) }

            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

    }

    fun onBtnSubscribeClick(view: View) {
        println("********************************clic")
        val user = UserBean(null,null, null,  tvPseudo.text.toString(), tvPwd.text.toString())
        thread {
            val mySession = WSUtils.subscribe(user)
            runOnUiThread { println("**************************"+mySession.session_id) }

            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }
}