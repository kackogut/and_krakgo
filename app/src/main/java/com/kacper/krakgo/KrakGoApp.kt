package com.kacper.krakgo

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kacper.krakgo.helpers.SharedPreferencesHelper
import com.kacper.krakgo.helpers.ToastMessageHelper
import com.kacper.krakgo.screens.home.HomeMainActivity
import com.kacper.krakgo.screens.sign_in.SignInActivity

class KrakGoApp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val handler = Handler()
        handler.postDelayed({
            var intent: Intent? = null
            if (SharedPreferencesHelper.getBoolean(SharedPreferencesHelper.REMEMBER_USER, baseContext)) {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    intent = Intent(applicationContext, HomeMainActivity::class.java)
                } else
                    ToastMessageHelper.showShortToast(baseContext ,R.string.auto_login_error)
            }
            if (intent == null) {
                intent = Intent(applicationContext, SignInActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 2000)

    }

    companion object {
        fun logout(context: Context) {
            FirebaseAuth.getInstance().signOut()
            SharedPreferencesHelper.saveToSharedPreferences(context,
                    SharedPreferencesHelper.REMEMBER_USER, false)
        }
    }
}
