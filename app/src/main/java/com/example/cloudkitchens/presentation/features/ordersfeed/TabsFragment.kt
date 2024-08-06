package com.example.chipnavigationapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.cloudkitchens.R
import com.example.cloudkitchens.presentation.di.Injector
import com.example.cloudkitchens.presentation.features.ordersfeed.TabBadgeManager
import com.example.cloudkitchens.presentation.features.ordersfeed.ViewPagerAdapter
import com.example.cloudkitchens.presentation.features.ordersfeed.ordersviewmodel.OrderViewModel
import com.example.cloudkitchens.presentation.features.ordersfeed.ordersviewmodel.OrderViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Fragment for displaying tabs with orders.
 */
class TabsFragment : Fragment() {

    @Inject
    lateinit var factory: OrderViewModelFactory
    private lateinit var orderViewModel: OrderViewModel

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as Injector).createOrderSubComponent().inject(this)
        orderViewModel = ViewModelProvider(this, factory)[OrderViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tabs, container, false)
        setupViews(view)
        setupViewPager()
        observeOrderCounts()
        observeOrders()
        return view
    }

    /**
     * Sets up the views in the fragment.
     *
     * @param view The root view of the fragment.
     */
    private fun setupViews(view: View) {
        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)
    }

    /**
     * Sets up the ViewPager and TabLayout.
     */
    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(requireActivity())
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 5  // Keep all fragments in memory

        val tabNames = resources.getStringArray(R.array.tab_names)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val tabView = LayoutInflater.from(context).inflate(R.layout.custom_tab, null)
            val tabText = tabView.findViewById<TextView>(R.id.tabText)
            tabText.text = tabNames[position]
            tab.customView = tabView
        }.attach()
    }

    /**
     * Observes the order counts and updates the tab badges accordingly.
     */
    private fun observeOrderCounts() {
        val tabBadgeManager = TabBadgeManager(tabLayout, orderViewModel.orderCounts)
        lifecycleScope.launch {
            tabBadgeManager.startObserving()
        }
    }

    /**
     * Observes the orders and updates the UI accordingly.
     */
    private fun observeOrders() {
        orderViewModel.orders.observe(viewLifecycleOwner) { orders ->
            // Handle orders as needed
        }
    }
}
