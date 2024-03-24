package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class Chat13Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_14)

//Profile btnnn
        val pButton = findViewById<ImageView>(R.id.profile_btn14)

        pButton.setOnClickListener {
            val intent = Intent(this, Screen21Activity::class.java)
            startActivity(intent)
        }

        //msg btn
        val ms2Button = findViewById<LinearLayout>(R.id.msg_layout_btn)

        ms2Button.setOnClickListener {
            val intent = Intent(this, Screen15Activity::class.java)
            startActivity(intent)
        }


        //community btn
        val com2Button = findViewById<ImageView>(R.id.comm_layout_btn)

        com2Button.setOnClickListener {
            val intent = Intent(this, Screen16Activity::class.java)
            startActivity(intent)
        }


        //Search btnnn
        val sButton = findViewById<ImageView>(R.id.search_btnn14)

        sButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }



        //Add btnnn
        val adButton = findViewById<TextView>(R.id.add_btn14)

        adButton.setOnClickListener {
            val intent = Intent(this, AddMentorActivity::class.java)
            startActivity(intent)
        }


        //home btnnn
        val homeButton = findViewById<ImageView>(R.id.home_btn14)

        homeButton.setOnClickListener {
            val intent = Intent(this, Screen7Activity::class.java)
            startActivity(intent)
        }
    }
}