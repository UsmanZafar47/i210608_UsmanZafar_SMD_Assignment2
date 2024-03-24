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
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_layout)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


        val nameEditText: EditText = findViewById(R.id.nameEditText) // Assume you have an EditText for name
        val emailEditText: EditText = findViewById(R.id.emailEditText) // Your email EditText
        val passwordEditText: EditText = findViewById(R.id.passwordEditText) // Your password EditText
        val countrySpinner: Spinner = findViewById(R.id.countryMenu)
        val citySpinner: Spinner = findViewById(R.id.cityMenu)
        val signupButton = findViewById<Button>(R.id.signup_btn2)
        val ContactEditText: EditText = findViewById(R.id.contact_number)

        setupSpinners()

        val loginView = findViewById<TextView>(R.id.LoginView)



        signupButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val name = nameEditText.text.toString().trim()
            val country = countrySpinner.selectedItem.toString()
            val city = citySpinner.selectedItem.toString()
            val contact = ContactEditText.text.toString().trim()

            if (validateForm(email, password, name, country, city,contact)) {
                createUserWithEmail(email, password, name, country, city,contact)
            }
        }

        loginView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }


    }
    private fun setupSpinners() {
        val countries = arrayOf("Select Country", "Country 1", "Country 2") // Add actual country names
        val countryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countries).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        findViewById<Spinner>(R.id.countryMenu).apply {
            adapter = countryAdapter
            prompt = "Select Country"
        }

        val cities = arrayOf("Select City", "City 1", "City 2") // Add actual city names
        val cityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        findViewById<Spinner>(R.id.cityMenu).apply {
            adapter = cityAdapter
            prompt = "Select City"
        }
    }

    private fun validateForm(email: String, password: String, name: String, country: String, city: String, contact: String): Boolean {
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter your name.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email address.", Toast.LENGTH_SHORT).show()
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter a password.", Toast.LENGTH_SHORT).show()
            return false
        } else if (password.length < 8) {
            Toast.makeText(this, "Password must be at least 8 characters long.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (country == "Select Country") {
            Toast.makeText(this, "Please select a country.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (city == "Select City") {
            Toast.makeText(this, "Please select a city.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (contact.length != 11 || !contact.startsWith("03")) {
            Toast.makeText(this, "Please enter a valid 11-digit contact number starting with 03.", Toast.LENGTH_SHORT).show()
            return false
        }

        // Add additional validation as needed
        return true
    }


    private fun createUserWithEmail(email: String, password: String, name: String, country: String, city: String,contact: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Authentication succeeded, proceed to navigate
                Toast.makeText(baseContext, "Signup successful.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SignUpActivity, Screen21Activity::class.java)
                startActivity(intent)

                // Now attempt to store the additional details in the database
                val userId = auth.currentUser?.uid ?: ""
                val userMap = mapOf("name" to name, "email" to email, "country" to country, "city" to city, "contact" to contact)
                database.reference.child("users").child(userId).setValue(userMap)
                    .addOnSuccessListener {
                        // Success in writing to the database, additional actions can be handled here if needed
                        Log.d("SignUpActivity", "User data added successfully")
                    }
                    .addOnFailureListener { exception ->
                        // Handle failure to write to the database
                        Log.e("SignUpActivity", "Failed to add user data", exception)
                    }
            } else {
                // Handle failure in authentication
                Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}