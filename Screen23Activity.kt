package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Screen23Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_23)


        //back btnnn
        val backButton = findViewById<ImageButton>(R.id.back_btn21)

        backButton.setOnClickListener {
            val intent = Intent(this, Screen21Activity::class.java)
            startActivity(intent)
        }



    }
}