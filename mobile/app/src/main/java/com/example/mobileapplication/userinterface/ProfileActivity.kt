package com.example.mobileapplication.userinterface

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.mobileapplication.R
import com.example.mobileapplication.api.RetrofitClient
import com.example.mobileapplication.models.LogoutRequest
import kotlinx.coroutines.launch

class ProfileActivity : ComponentActivity() {

    private lateinit var profileAvatar: TextView
    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView
    private lateinit var firstNameValue: TextView
    private lateinit var lastNameValue: TextView
    private lateinit var emailValue: TextView
    private lateinit var userIdValue: TextView
    private lateinit var dashboardButton: Button
    private lateinit var profileButton: Button
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val sharedPref = getSharedPreferences("UserAuth", MODE_PRIVATE)
        val token = sharedPref.getString("token", null)

        if (token == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        profileAvatar = findViewById(R.id.profileAvatar)
        profileName = findViewById(R.id.profileName)
        profileEmail = findViewById(R.id.profileEmail)
        firstNameValue = findViewById(R.id.firstNameValue)
        lastNameValue = findViewById(R.id.lastNameValue)
        emailValue = findViewById(R.id.emailValue)
        userIdValue = findViewById(R.id.userIdValue)
        dashboardButton = findViewById(R.id.dashboardButton)
        profileButton = findViewById(R.id.profileButton)
        logoutButton = findViewById(R.id.logoutButton)

        loadUserData()

        dashboardButton.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

        profileButton.setOnClickListener {
        }

        logoutButton.setOnClickListener {
            handleLogout()
        }
    }

    private fun loadUserData() {
        val sharedPref = getSharedPreferences("UserAuth", MODE_PRIVATE)
        val firstName = sharedPref.getString("firstName", "") ?: ""
        val lastName = sharedPref.getString("lastName", "") ?: ""
        val email = sharedPref.getString("email", "") ?: ""
        val token = sharedPref.getString("token", "") ?: ""

        val initials = "${firstName.firstOrNull() ?: ""}${lastName.firstOrNull() ?: ""}"
        profileAvatar.text = initials.toUpperCase()
        profileName.text = "$firstName $lastName"
        profileEmail.text = email
        firstNameValue.text = firstName
        lastNameValue.text = lastName
        emailValue.text = email
        userIdValue.text = token
    }

    private fun handleLogout() {
        val sharedPref = getSharedPreferences("UserAuth", MODE_PRIVATE)
        val token = sharedPref.getString("token", "") ?: ""

        lifecycleScope.launch {
            try {
                val request = LogoutRequest(token)
                val response = RetrofitClient.instance.logout(request)

                if (response.isSuccessful) {
                    sharedPref.edit().clear().apply()
                    Toast.makeText(this@ProfileActivity, "Logged out successfully!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@ProfileActivity, "Logout failed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                sharedPref.edit().clear().apply()
                val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}