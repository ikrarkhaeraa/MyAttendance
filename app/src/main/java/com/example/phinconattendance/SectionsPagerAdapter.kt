package com.example.phinconattendance

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = OnBoard1Fragment()
            1 -> fragment = OnBoard2Fragment()
            2 -> fragment = OnBoard3Fragment()
        }
        return fragment as Fragment
    }

}