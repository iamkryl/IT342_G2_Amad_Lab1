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

class DashboardActivity : ComponentActivity() {

    private lateinit var welcomeTitle: TextView
    private lateinit var emailStat: TextView
    private lateinit var fullNameStat: TextView
    private lateinit var firstNameInfo: TextView
    private lateinit var lastNameInfo: TextView
    private lateinit var emailInfo: TextView
    private lateinit var dashboardButton: Button
    private lateinit var profileButton: Button
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val sharedPref = getSharedPreferences("UserAuth", MODE_PRIVATE)
        val token = sharedPref.getString("token", null)

        if (token == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        welcomeTitle = findViewById(R.id.welcomeTitle)
        emailStat = findViewById(R.id.emailStat)
        fullNameStat = findViewById(R.id.fullNameStat)
        firstNameInfo = findViewById(R.id.firstNameInfo)
        lastNameInfo = findViewById(R.id.lastNameInfo)
        emailInfo = findViewById(R.id.emailInfo)
        dashboardButton = findViewById(R.id.dashboardButton)
        profileButton = findViewById(R.id.profileButton)
        logoutButton = findViewById(R.id.logoutButton)

        loadUserData()

        dashboardButton.setOnClickListener {
        }

        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        logoutButton.setOnClickListener {
            handleLogout()
        }
    }

    private fun loadUserData() {
        val sharedPref = getSharedPreferences("UserAuth", MODE_PRIVATE)
        val firstName = sharedPref.getString("firstName", "User") ?: "User"
        val lastName = sharedPref.getString("lastName", "") ?: ""
        val email = sharedPref.getString("email", "") ?: ""

        welcomeTitle.text = "Welcome back, $firstName! 👋"
        emailStat.text = email
        fullNameStat.text = "$firstName $lastName"
        firstNameInfo.text = firstName
        lastNameInfo.text = lastName
        emailInfo.text = email
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
                    Toast.makeText(this@DashboardActivity, "Logged out successfully!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@DashboardActivity, "Logout failed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                sharedPref.edit().clear().apply()
                val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}