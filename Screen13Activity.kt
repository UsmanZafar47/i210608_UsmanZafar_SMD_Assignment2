package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView

class Screen13Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_13)



        //call btnnn
        val callButton = findViewById<ImageButton>(R.id.call_btn_booking)

        callButton.setOnClickListener {
            val intent = Intent(this, CallActivity::class.java)
            startActivity(intent)
        }

        //video call btnnn
        val videocallButton = findViewById<ImageButton>(R.id.video_btn_booking)

        videocallButton.setOnClickListener {
            val intent = Intent(this, VideoCallActivity::class.java)
            startActivity(intent)
        }


        //back btnnn
        val backButton = findViewById<ImageButton>(R.id.back_btn13)

        backButton.setOnClickListener {
            val intent = Intent(this, Scren10Activity::class.java)
            startActivity(intent)
        }


        //chat btnnn
        val chatButton = findViewById<ImageButton>(R.id.msg_btn_booking)

        chatButton.setOnClickListener {
            val intent = Intent(this, Chat13Activity::class.java)
            startActivity(intent)
        }

    }
}