package com.example.testtaskwebantapplicationkotlin

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.example.testtaskwebantapplicationkotlin.fragments.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPagerAdapter()
    }

    private fun initViewPagerAdapter() {
        tab_layout.addTab(tab_layout.newTab().setText("New"))
        tab_layout.addTab(tab_layout.newTab().setText("Popular"))

        val viewPager = findViewById<ViewPager>(R.id.container)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }
        })
    }
}

