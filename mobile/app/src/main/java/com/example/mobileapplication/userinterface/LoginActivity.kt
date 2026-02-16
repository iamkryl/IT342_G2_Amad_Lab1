package com.example.mobileapplication.userinterface

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.mobileapplication.R
import com.example.mobileapplication.api.RetrofitClient
import com.example.mobileapplication.models.LoginRequest
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var registerLink: TextView
    private lateinit var errorMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        registerLink = findViewById(R.id.registerLink)
        errorMessage = findViewById(R.id.errorMessage)

        setupRegisterLink()

        loginButton.setOnClickListener {
            handleLogin()
        }
    }

    private fun setupRegisterLink() {
        val fullText = "Don't have an account? Register here"
        val spannableString = SpannableString(fullText)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#1D4ED8") // Blue for "Register here"
                ds.isUnderlineText = false
                ds.isFakeBoldText = true
            }
        }

        val startIndex = fullText.indexOf("Register here")
        val endIndex = startIndex + "Register here".length
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        registerLink.text = spannableString
        registerLink.movementMethod = LinkMovementMethod.getInstance()
        registerLink.highlightColor = Color.TRANSPARENT
    }

    private fun handleLogin() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            showError("Please fill all fields")
            return
        }

        if (!email.contains("@")) {
            showError("Please include an '@' in the email address. '$email' is missing an '@'.")
            return
        }

        errorMessage.visibility = TextView.GONE

        lifecycleScope.launch {
            try {
                val request = LoginRequest(email, password)
                val response = RetrofitClient.instance.login(request)

                if (response.isSuccessful && response.body() != null) {
                    val authResponse = response.body()!!

                    // Save user data to SharedPreferences
                    val sharedPref = getSharedPreferences("UserAuth", MODE_PRIVATE)
                    sharedPref.edit().apply {
                        putString("token", authResponse.token)
                        putString("email", authResponse.email)
                        putString("firstName", authResponse.firstName)
                        putString("lastName", authResponse.lastName)
                        apply()
                    }

                    Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()

                    // Navigate to Dashboard
                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showError("Login failed: User not found")
                }
            } catch (e: Exception) {
                showError("Error: ${e.message}")
            }
        }
    }

    private fun showError(message: String) {
        errorMessage.text = message
        errorMessage.visibility = TextView.VISIBLE
    }
}