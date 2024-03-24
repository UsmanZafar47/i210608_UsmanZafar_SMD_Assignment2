package com.example.myapplication


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast



import android.widget.ArrayAdapter
import android.widget.Spinner


import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference


//class MainActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Assuming you have a button with id 'nextButton' in your layout
//        val nextButton = findViewById<Button>(R.id.start_btn)
//
//        // Set OnClickListener to the button
//        nextButton.setOnClickListener {
//            // Create Intent to switch to NextActivity
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent) // Start NextActivity
//        }
//    }
//
//}



import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Handler to post a delayed task
        Handler(Looper.getMainLooper()).postDelayed({
            // Create an Intent to start LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            // Optionally, call finish() if you don't want users to return to this screen
            finish()
        }, 5000) // Delay of 5 seconds (5000 milliseconds)
    }
}
