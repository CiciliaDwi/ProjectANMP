package com.ubaya.a3pilar_project.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ubaya.a3pilar_project.MainActivity
import com.ubaya.a3pilar_project.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cek session login
        val prefs = getSharedPreferences("session", Context.MODE_PRIVATE)
        val username = prefs.getString("username", null)

        if (username != null) {
            // Sudah login, langsung ke MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tampilkan LoginFragment saat pertama kali
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, LoginFragment())
            .commit()
    }
}
