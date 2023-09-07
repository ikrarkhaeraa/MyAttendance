package com.example.phinconattendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.phinconattendance.databinding.ActivityLoginBinding
import com.example.phinconattendance.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = Firebase.auth
        register()
        goToLogin()
    }

    private fun goToLogin() {
        binding.textlogin.setOnClickListener {
            val intentToLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intentToLogin)
        }
    }

    private fun register() {
        binding.buttonloginnow.setOnClickListener {

            val fullname = binding.fullnameField.text.toString()
            val email = binding.emailfield.text.toString()
            val password = binding.passwordField.text.toString()
            val repeat = binding.repeatpasswordField.text.toString()

            Log.d("cekinput", fullname)
            Log.d("cekinput", email)
            Log.d("cekinput", password)
            Log.d("cekinput", repeat)

            if (fullname.isBlank() || email.isBlank() || password.isBlank() || repeat.isBlank()) {
                Toast.makeText(this, "Please fill the all form", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password != repeat) {
                Toast.makeText(this, "Password and Repeat Password do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val intentToLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
                    intentToLogin.putExtra(LoginActivity.FULLNAME, fullname)
                    intentToLogin.putExtra(LoginActivity.EMAIL, email)
                    startActivity(intentToLogin)
                    finish()
                } else {
                    Log.e("AUTH", "Error signing in", it.exception)
                    Toast.makeText(this, "Signed Up Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}