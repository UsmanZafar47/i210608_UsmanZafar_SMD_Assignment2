package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

class Screen7Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen7)



        val relativeLayout = findViewById<RelativeLayout>(R.id.Box1)

        relativeLayout.setOnClickListener {
          val intent = Intent(this, Scren10Activity::class.java)
          startActivity(intent) // Start the next activity
           }

        val relativeLayout2 = findViewById<RelativeLayout>(R.id.Box5)

        relativeLayout2.setOnClickListener {
            val intent = Intent(this, Scren10Activity::class.java)
            startActivity(intent) // Start the next activity
        }


        val notifbtn = findViewById<Button>(R.id.notify1)

        notifbtn.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent) // Start the next activity
        }


        //Search btnnn
        val sButton = findViewById<ImageView>(R.id.search_btnn)

        sButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        //Profile btnnn
        val pButton = findViewById<ImageView>(R.id.profile_btn7)

        pButton.setOnClickListener {
            val intent = Intent(this, Screen21Activity::class.java)
            startActivity(intent)
        }


        //Add btnnn
        val adButton = findViewById<TextView>(R.id.add7_btn)

        adButton.setOnClickListener {
            val intent = Intent(this, AddMentorActivity::class.java)
            startActivity(intent)
        }


        //chat btnnn
        val chatButton = findViewById<ImageView>(R.id.chat_btn7)

        chatButton.setOnClickListener {
            val intent = Intent(this, Chat13Activity::class.java)
            startActivity(intent)
        }


    }
}

