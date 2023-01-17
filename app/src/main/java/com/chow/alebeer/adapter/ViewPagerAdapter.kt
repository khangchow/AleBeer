package com.chow.alebeer.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chow.alebeer.ui.main_screen.tab.BeerFragment
import com.chow.alebeer.ui.main_screen.tab.FavoriteFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) BeerFragment.newInstance() else FavoriteFragment.newInstance()
    }

    override fun getItemCount(): Int {
        return 2
    }
}