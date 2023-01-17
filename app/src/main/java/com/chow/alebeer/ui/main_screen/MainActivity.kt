package com.chow.alebeer.ui.main_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.chow.alebeer.R
import com.chow.alebeer.adapter.ViewPagerAdapter
import com.chow.alebeer.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            viewPager.apply {
                adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        binding.tabLayout.run {
                            selectTab(getTabAt(position))
                        }
                    }
                })
            }
            tabLayout.apply {
                addTab(newTab().setText(getString(R.string.tab_beer)))
                addTab(newTab().setText(getString(R.string.tab_favorite)))
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        binding.viewPager.currentItem = tab.position
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {

                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {

                    }
                })
            }
        }
    }
}