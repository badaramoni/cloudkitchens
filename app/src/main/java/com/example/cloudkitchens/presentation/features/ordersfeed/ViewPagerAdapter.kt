package com.example.cloudkitchens.presentation.features.ordersfeed

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cloudkitchens.R
import com.example.cloudkitchens.presentation.features.ordershelf.ShelfFragment

/**
 * Adapter for managing shelf fragments in a ViewPager2.
 *
 * @param fragmentActivity The parent FragmentActivity.
 */
class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val shelfTypes = fragmentActivity.resources.getStringArray(R.array.shelf_types).toList()
    private val fragmentCache = mutableMapOf<Int, Fragment>()

    /**
     * Returns the number of fragments.
     *
     * @return The number of fragments.
     */
    override fun getItemCount(): Int = shelfTypes.size

    /**
     * Creates a fragment for the given position.
     *
     * @param position The position of the fragment.
     * @return The created fragment.
     */
    override fun createFragment(position: Int): Fragment {
        return fragmentCache.getOrPut(position) { ShelfFragment.newInstance(shelfTypes[position]) }
    }
}
