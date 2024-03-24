package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ForgotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_pass_layout)

        val fButton = findViewById<Button>(R.id.reset_forgot_btn)

        fButton.setOnClickListener {
            val intent = Intent(this, ResetActivity::class.java)
            startActivity(intent)
        }


        val loginView = findViewById<TextView>(R.id.LoginView3)

        loginView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}