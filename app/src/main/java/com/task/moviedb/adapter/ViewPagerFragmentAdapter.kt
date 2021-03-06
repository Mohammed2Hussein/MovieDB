package com.task.moviedb.adapter

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.task.moviedb.view.fragments.DiscoverFragment
import com.task.moviedb.view.fragments.SearchFragment

class ViewPagerFragmentAdapter(fm: FragmentManager?, lifecycle: Lifecycle) : FragmentStateAdapter(fm!!, lifecycle) {
    private val int_items = 2
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = DiscoverFragment()
            1 -> fragment = SearchFragment()
        }
        return fragment!!
    }
    override fun getItemCount(): Int {
        return int_items
    }
}