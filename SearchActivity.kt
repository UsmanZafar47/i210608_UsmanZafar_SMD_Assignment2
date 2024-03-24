package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.search_screen8)
        val sButton8 = findViewById<ImageView>(R.id.search_scn8_btn)

        sButton8.setOnClickListener {
            val intent = Intent(this, SearchActivity9::class.java)
            startActivity(intent)
        }


        //home btn
        val hButton8 = findViewById<ImageView>(R.id.home_scn8_btn)

        hButton8.setOnClickListener {
            val intent = Intent(this, Screen7Activity::class.java)
            startActivity(intent)
        }


//Profile btnnn
        val pButton = findViewById<ImageView>(R.id.profile_btn8)

        pButton.setOnClickListener {
            val intent = Intent(this, Screen21Activity::class.java)
            startActivity(intent)
        }
        //Add btnnn
        val adButton = findViewById<TextView>(R.id.add8_btn)

        adButton.setOnClickListener {
            val intent = Intent(this, AddMentorActivity::class.java)
            startActivity(intent)
        }
        //chat btnnn
        val chatButton = findViewById<ImageView>(R.id.chat_btn8)

        chatButton.setOnClickListener {
            val intent = Intent(this, Chat13Activity::class.java)
            startActivity(intent)
        }



    }
}