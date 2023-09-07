package com.example.phinconattendance

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.phinconattendance.databinding.ActivityOnBoardingBinding
import com.google.android.material.tabs.TabLayoutMediator


class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewpager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { _, _ ->
            binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == 1 || position == 2) {
                        binding.buttonsignup.setBackgroundColor(resources.getColor(R.color.white))
                        binding.buttonsignup.setTextColor(resources.getColor(R.color.grey_indicator_selected))
                        val gradientdrawable = GradientDrawable()
                        gradientdrawable.setStroke(5, resources.getColor(R.color.grey_indicator_selected))
                        gradientdrawable.cornerRadius = 20F
                        binding.buttonsignup.background = gradientdrawable
                    } else {
                        binding.buttonsignup.setTextColor(resources.getColor(R.color.white))
                        val gradientdrawable = GradientDrawable()
                        gradientdrawable.setColor(resources.getColor(R.color.blue))
                        gradientdrawable.cornerRadius = 20F
                        binding.buttonsignup.background = gradientdrawable
                    }
                }
            })
        }.attach()
        supportActionBar?.elevation = 0f

        chooseButton()
    }

    private fun chooseButton() {
        binding.buttonlogin.setOnClickListener {
            val intentToLogin = Intent(this@OnBoardingActivity, LoginActivity::class.java)
            startActivity(intentToLogin)
        }
        binding.buttonsignup.setOnClickListener {
            val intentToSignUp = Intent (this@OnBoardingActivity, RegisterActivity::class.java)
            startActivity(intentToSignUp)
        }
    }

}