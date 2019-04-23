package com.example.testtaskwebantapplicationkotlin

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter internal constructor(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val COUNT = 2

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = PopularFragment()
            1 -> fragment = NewFragment()
        }
        return fragment
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "OBJECT ${(position + 1)}"
    }
}