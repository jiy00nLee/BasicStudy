package com.example.basestructure.ui.mainhome

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.basestructure.ui.mainhome.BlankFragment

class MainHomeViewPagerAdapter (activity: FragmentActivity) : FragmentStateAdapter(activity){

    override fun getItemCount(): Int = 4 //PagerViewadapter에서 관리할 View 개수를 반환한다.

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> BlankFragment()
            1 -> BlankFragment()
            2 -> BlankFragment()
            3 -> BlankFragment()
            4 -> BlankFragment()
            else -> BlankFragment()
        }
    }
}