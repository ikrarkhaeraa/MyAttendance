package com.example.phinconattendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.phinconattendance.databinding.ActivityRegisterBinding
import com.example.phinconattendance.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = Firebase.auth
        goToLogin()
    }

    private fun goToLogin() {
        binding.buttonresetpassword.setOnClickListener {

            val email = binding.emailfield.text.toString()

            if (email.isBlank()) {
                Toast.makeText(this, "Please fill the form", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val intentToLogin = Intent(this@ResetPasswordActivity, LoginActivity::class.java)
                    startActivity(intentToLogin)
                    Toast.makeText(this, "Reset Password Success, Please check your email!", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("cekresetpass", "Error send reset Password", it.exception)
                    Toast.makeText(this, "Reset Password Failed!", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

}