package com.task.moviedb.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.task.moviedb.R
import com.task.moviedb.adapter.ViewPagerFragmentAdapter
import com.task.moviedb.animation.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPager2()
        initTabLayout()
    }

    fun initViewPager2(){
        mainActivityViewPager.setPageTransformer(ZoomOutPageTransformer())
        mainActivityViewPager!!.setAdapter(ViewPagerFragmentAdapter(supportFragmentManager, lifecycle))

        TabLayoutMediator(mainActivityTabLayout!!, mainActivityViewPager!!, TabLayoutMediator.TabConfigurationStrategy{tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab_discover)
                1 -> tab.text = getString(R.string.tab_search)
            }
        }).attach()
    }

    fun initTabLayout(){
        TabLayoutMediator(mainActivityTabLayout!!, mainActivityViewPager!!, TabLayoutMediator.TabConfigurationStrategy{tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab_discover)
                1 -> tab.text = getString(R.string.tab_search)
            }
        }).attach()
    }
}