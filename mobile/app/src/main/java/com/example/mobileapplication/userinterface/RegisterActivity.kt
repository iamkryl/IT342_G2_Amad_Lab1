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
import com.example.mobileapplication.models.RegisterRequest
import kotlinx.coroutines.launch

class RegisterActivity : ComponentActivity() {

    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var registerButton: Button
    private lateinit var loginLink: TextView
    private lateinit var errorMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firstNameInput = findViewById(R.id.firstNameInput)
        lastNameInput = findViewById(R.id.lastNameInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        registerButton = findViewById(R.id.registerButton)
        loginLink = findViewById(R.id.loginLink)
        errorMessage = findViewById(R.id.errorMessage)

        setupLoginLink()

        registerButton.setOnClickListener {
            handleRegister()
        }

    }

    private fun setupLoginLink() {
        val fullText = "Already have an account? Login here"
        val spannableString = SpannableString(fullText)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#1D4ED8") // Blue for "Login here"
                ds.isUnderlineText = false
                ds.isFakeBoldText = true
            }
        }

        val startIndex = fullText.indexOf("Login here")
        val endIndex = startIndex + "Login here".length
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        loginLink.text = spannableString
        loginLink.movementMethod = LinkMovementMethod.getInstance()
        loginLink.highlightColor = Color.TRANSPARENT
    }

    private fun handleRegister() {
        val firstName = firstNameInput.text.toString().trim()
        val lastName = lastNameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()
        val confirmPassword = confirmPasswordInput.text.toString()

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
            password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Please fill all fields")
            return
        }

        if (!email.contains("@")) {
            showError("Please include an '@' in the email address. '$email' is missing an '@'.")
            return
        }

        val gmailRegex = Regex("^[a-zA-Z0-9._%+-]+@gmail\\.com$")
        if (!gmailRegex.matches(email)) {
            showError("Please use a valid Gmail address (@gmail.com)")
            return
        }

        if (password != confirmPassword) {
            showError("Passwords do not match!")
            return
        }

        errorMessage.visibility = TextView.GONE

        lifecycleScope.launch {
            try {
                val request = RegisterRequest(firstName, lastName, email, password)
                val response = RetrofitClient.instance.register(request)

                if (response.isSuccessful) {
                    val message = response.body()?.string() ?: "Registration successful!"
                    Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Registration failed"
                    showError(errorMsg)
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