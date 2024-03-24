package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class SearchActivity9 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_results_screen9)

        //home btn
        val hButton9 = findViewById<ImageView>(R.id.home_scn9_btn)

        hButton9.setOnClickListener {
            val intent = Intent(this, Screen7Activity::class.java)
            startActivity(intent)
        }

        //Profile btnnn
        val pButton = findViewById<ImageView>(R.id.profile_btn9)

        pButton.setOnClickListener {
            val intent = Intent(this, Screen21Activity::class.java)
            startActivity(intent)
        }


        //back btnnn
        val backButton = findViewById<ImageButton>(R.id.back_btn9)

        backButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }



        //Add btnnn
        val adButton = findViewById<TextView>(R.id.add9_btn)

        adButton.setOnClickListener {
            val intent = Intent(this, AddMentorActivity::class.java)
            startActivity(intent)
        }
        //chat btnnn
        val chatButton = findViewById<ImageView>(R.id.chat_btn9)

        chatButton.setOnClickListener {
            val intent = Intent(this, Chat13Activity::class.java)
            startActivity(intent)
        }




    }
}