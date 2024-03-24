package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView

class Screen16Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_16)

        //call btnnn
        val callButton = findViewById<ImageButton>(R.id.call_btn_com)

        callButton.setOnClickListener {
            val intent = Intent(this, CallActivity::class.java)
            startActivity(intent)
        }

        //Profile btnnn
        val pButton = findViewById<ImageView>(R.id.profile_btn16)

        pButton.setOnClickListener {
            val intent = Intent(this, Screen21Activity::class.java)
            startActivity(intent)
        }
        //video call btnnn
        val videocallButton = findViewById<ImageButton>(R.id.video_btn_com)

        videocallButton.setOnClickListener {
            val intent = Intent(this, VideoCallActivity::class.java)
            startActivity(intent)
        }




    }
}