package com.example.phinconattendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.phinconattendance.databinding.ActivityLoginBinding
import com.example.phinconattendance.databinding.FragmentHomeBinding
import com.example.phinconattendance.ui.profile.ProfileFragment
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    companion object {
        const val FULLNAME = "fullname"
        const val EMAIL = "email"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        login()
        goToResetPassword()
        goToRegister()
    }

    private fun goToRegister() {
        binding.textregister.setOnClickListener {
            val intentToRegister = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intentToRegister)
        }
    }

    private fun goToResetPassword() {
        binding.textresetpassword.setOnClickListener {
            val intentToReset = Intent(this@LoginActivity, ResetPasswordActivity::class.java)
            startActivity(intentToReset)
        }
    }

    private fun login() {
        binding.buttonloginnow.setOnClickListener {

            val username = binding.usernamefield.text.toString()
            val password = binding.passwordField.text.toString()

            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill the all form", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val intentToHome = Intent(this@LoginActivity, HomeActivity::class.java)
                    val fullname = intent.getSerializableExtra(FULLNAME)
                    val email = intent.getSerializableExtra(EMAIL)
                    intentToHome.putExtra(HomeActivity.FULLNAME, fullname)
                    intentToHome.putExtra(HomeActivity.EMAIL, email)
                    startActivity(intentToHome)
                } else {
                    Log.e("AUTH", "Error signing in", it.exception)
                    Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}