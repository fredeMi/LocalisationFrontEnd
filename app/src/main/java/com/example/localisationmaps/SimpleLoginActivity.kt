package com.example.localisationmaps

import android.content.Intent
import android.graphics.Color
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
        val user = UserBean(null, null, null, tvPseudo.text.toString(), tvPwd.text.toString())

        val pseudo = tvPseudo.text.toString()
        val pwd = tvPwd.text.toString()

        thread {
            if (pseudo.isNotBlank() && pseudo.isNotEmpty() && pwd.isNotBlank() && pwd.isNotEmpty()) {
                try {
                    val mySession = WSUtils.login(user)
                    runOnUiThread {
//                println("**************************session:${mySession.sessionId} et success = ${mySession.success}")
                        if (mySession.success) {
                            val intent = Intent(this, MapsActivity::class.java)
                            intent.putExtra("sessionId", mySession.sessionId)
                            startActivity(intent)
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    setErrorOnUiThread("Nous rencontrons des problèmes sur le serveur, veuillez vous reconnecter plus tard, merci.")
                }
            } else {
                setErrorOnUiThread("L'utilisateur doit remplir tous les champs")
            }
        }
    }

    fun onBtnSubscribeClick(view: View) {
        println("********************************clic")
        val user = UserBean(null, null, null, tvPseudo.text.toString(), tvPwd.text.toString())

        val pseudo = tvPseudo.text.toString()
        val pwd = tvPwd.text.toString()

        thread {
            if (pseudo.isNotBlank() && pseudo.isNotEmpty() && pwd.isNotBlank() && pwd.isNotEmpty()) {
                try {
                    val mySession = WSUtils.subscribe(user)
                    runOnUiThread {
//                println("**************************session:${mySession.sessionId} et success = ${mySession.success}")
                        if (mySession.success) {
                            val intent = Intent(this, MapsActivity::class.java)
                            intent.putExtra("sessionId", mySession.sessionId)
                            startActivity(intent)
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    setErrorOnUiThread("Nous rencontrons des problèmes sur le serveur, veuillez vous reconnecter plus tard, merci.")
                }
            } else {
                setErrorOnUiThread("L'utilisateur doit remplir tous les champs")
            }
        }
    }

    /* -------------------------------- */
    // Méthode de mise à jour de l'ihm
    /* -------------------------------- */

    fun setErrorOnUiThread(text: String?) = runOnUiThread {
        if (text.isNullOrBlank()) {
            tvError.visibility = View.GONE
        } else {
            tvError.visibility = View.VISIBLE
        }
        tvError.text = text
    }

    fun showProgressBar(visible: Boolean) = runOnUiThread {
        progressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }
}