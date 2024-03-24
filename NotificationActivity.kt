package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_24)

        //back btnnn
        val backButton = findViewById<ImageButton>(R.id.back_btn24)

        backButton.setOnClickListener {
            val intent = Intent(this, Screen7Activity::class.java)
            startActivity(intent)
        }
    }
}