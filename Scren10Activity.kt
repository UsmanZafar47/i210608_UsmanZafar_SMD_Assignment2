package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView

class Scren10Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen10)



        val revbtnn = findViewById<TextView>(R.id.rev_btn)

        revbtnn.setOnClickListener {
            val intent = Intent(this, Screen11Activity::class.java)
            startActivity(intent) // Start the next activity
        }

        val sesbtnn = findViewById<TextView>(R.id.book_ses_btn)

        sesbtnn.setOnClickListener {
            val intent = Intent(this, Screen13Activity::class.java)
            startActivity(intent) // Start the next activity
        }


    }
}