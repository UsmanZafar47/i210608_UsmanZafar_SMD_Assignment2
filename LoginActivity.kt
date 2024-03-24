package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.login_btn2)
        val forgotButton = findViewById<TextView>(R.id.forgot_btn)
        val signupTextView = findViewById<TextView>(R.id.SignupView)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Please enter both email and password.", Toast.LENGTH_SHORT).show()
            }
        }

        forgotButton.setOnClickListener {
            startActivity(Intent(this, ForgotActivity::class.java))
        }

        signupTextView.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(baseContext, "Login successful.", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Screen21Activity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(baseContext, "Login failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
