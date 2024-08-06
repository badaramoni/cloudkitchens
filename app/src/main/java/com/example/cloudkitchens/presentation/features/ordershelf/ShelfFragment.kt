package com.example.cloudkitchens.presentation.features.ordershelf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.example.cloudkitchens.R
import com.example.cloudkitchens.presentation.di.Injector
import com.example.cloudkitchens.presentation.features.orderdetails.OrderDetailFragment
import com.example.cloudkitchens.presentation.features.ordershelf.shelfordersviewmodel.ShelfOrdersViewModel
import com.example.cloudkitchens.presentation.features.ordershelf.shelfordersviewmodel.ShelfOrderViewModelFactory
import java.util.PriorityQueue
import javax.inject.Inject

class ShelfFragment : Fragment() {

    @Inject
    lateinit var factory: ShelfOrderViewModelFactory
    private lateinit var shelfOrdersViewModel: ShelfOrdersViewModel

    private lateinit var shelfFeedAdapter: ShelfFeedAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var emptyAnimationView: LottieAnimationView
    private val orderPriorityQueue = PriorityQueue(OrderStateTimestampComparator)

    private var shelfType: String? = null

    companion object {
        private const val ARG_SHELF_TYPE = "shelf_type"

        fun newInstance(shelfType: String): ShelfFragment {
            val fragment = ShelfFragment()
            val args = Bundle()
            args.putString(ARG_SHELF_TYPE, shelfType)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as Injector).createOrderShelfSubComponent().inject(this)
        shelfOrdersViewModel = ViewModelProvider(this, factory).get(ShelfOrdersViewModel::class.java)
        shelfType = arguments?.getString(ARG_SHELF_TYPE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_orders_feed, container, false)
        emptyAnimationView = view.findViewById(R.id.empty_animation_view)

        setupRecyclerView(view)
        setupSwipeRefreshLayout(view)
        observeOrders()
        return view
    }

    private fun setupRecyclerView(view: View) {
        shelfFeedAdapter = ShelfFeedAdapter(mutableListOf()) { order ->
            val fragment = OrderDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("orderId", order.id)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        recyclerView = view.findViewById(R.id.stores_view)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = shelfFeedAdapter
        }
    }

    private fun setupSwipeRefreshLayout(view: View) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_container)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            observeOrders()
        }
        swipeRefreshLayout.isEnabled = true
    }

    private fun observeOrders() {
        shelfOrdersViewModel.getOrders(shelfType!!).observe(viewLifecycleOwner) { orders ->
            if (orders != null && orders.isNotEmpty()) {
                val filteredOrders = orders.sortedWith(OrderStateTimestampComparator)

                orderPriorityQueue.clear()
                orderPriorityQueue.addAll(filteredOrders)

                shelfFeedAdapter.updateList(orderPriorityQueue.toList())
                updateEmptyView(false) // List is not empty
            } else {
                handleEmptyOrders()
            }
        }
    }

    private fun handleEmptyOrders() {
        recyclerView.visibility = View.GONE
        emptyAnimationView.visibility = View.VISIBLE
        emptyAnimationView.playAnimation()
    }

    private fun updateEmptyView(isListEmpty: Boolean) {
        recyclerView.visibility = if (isListEmpty) View.GONE else View.VISIBLE
        emptyAnimationView.visibility = if (isListEmpty) View.VISIBLE else View.GONE

        if (isListEmpty) {
            emptyAnimationView.playAnimation()
        } else {
            emptyAnimationView.pauseAnimation()
        }
    }
}
