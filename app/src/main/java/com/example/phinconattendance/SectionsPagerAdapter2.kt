package com.example.phinconattendance

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter2(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = DayFragment()
            1 -> fragment = WeekFragment()
            2 -> fragment = MonthFragment()
            3 -> fragment = YearFragment()
        }
        return fragment as Fragment
    }

}