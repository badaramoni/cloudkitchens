package com.example.cloudkitchens.presentation.features.ordersfeed

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.cloudkitchens.domain.model.OrderCounts
import com.google.android.material.tabs.TabLayout

/**
 * Manages badges on a TabLayout based on order counts.
 *
 * @param tabLayout The TabLayout to manage.
 * @param orderCountsLiveData The LiveData providing order counts.
 */
class TabBadgeManager(
    private val tabLayout: TabLayout,
    private val orderCountsLiveData: LiveData<OrderCounts>
) {

    private val badges = mutableMapOf<Int, TabLayout.Tab>()

    /**
     * Starts observing the order counts LiveData and updates the badges accordingly.
     */
    fun startObserving() {
        orderCountsLiveData.observe(tabLayout.context as? LifecycleOwner ?: return) { counts ->
            Log.d("TabBadgeManager", "Received counts: $counts")
            updateBadge(0, counts.all)
            updateBadge(1, counts.hot)
            updateBadge(2, counts.cold)
            updateBadge(3, counts.frozen)
            updateBadge(4, counts.overflow)
        }
    }

    /**
     * Updates the badge for the specified tab position.
     *
     * @param position The position of the tab.
     * @param count The count to display in the badge.
     */
    private fun updateBadge(position: Int, count: Int) {
        val tab = tabLayout.getTabAt(position)
        if (tab == null) {
            Log.e("TabBadgeManager", "Tab at position $position is null")
            return
        }

        if (count > 0) {
            val badge = tab.orCreateBadge
            badge.isVisible = true
            badge.number = count
            Log.d("TabBadgeManager", "Updating badge at position $position with count: $count")
        } else {
            tab.removeBadge()
            Log.d("TabBadgeManager", "Hiding badge at position $position")
        }
    }
}
