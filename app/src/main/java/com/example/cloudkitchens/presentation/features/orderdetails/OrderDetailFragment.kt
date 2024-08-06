package com.example.cloudkitchens.presentation.features.orderdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudkitchens.R
import com.example.cloudkitchens.domain.model.Order
import com.example.cloudkitchens.presentation.di.Injector
import com.example.cloudkitchens.presentation.features.orderdetails.orderdetailsviewmodel.OrderDetailsViewModel
import com.example.cloudkitchens.presentation.features.orderdetails.orderdetailsviewmodel.OrderDetailsViewModelFactory
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

/**
 * Fragment for displaying order details.
 */
class OrderDetailFragment : Fragment() {

    @Inject
    lateinit var factory: OrderDetailsViewModelFactory
    private lateinit var orderDetailsViewModel: OrderDetailsViewModel

    private lateinit var orderNumberTextView: TextView
    private lateinit var customerTextView: TextView
    private lateinit var destinationTextView: TextView
    private lateinit var itemTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var stateTextView: TextView
    private lateinit var changelogRecyclerView: RecyclerView
    private lateinit var changelogAdapter: ChangelogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as Injector).createOrderDetailsSubComponent().inject(this)
        orderDetailsViewModel = ViewModelProvider(this, factory).get(OrderDetailsViewModel::class.java)

        val orderId = arguments?.getString("orderId") ?: return
        orderDetailsViewModel.fetchOrderDetails(orderId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_detail, container, false)
        setupViews(view)
        observeOrderDetails()
        return view
    }

    /**
     * Sets up the views in the fragment.
     *
     * @param view The root view of the fragment.
     */
    private fun setupViews(view: View) {
        orderNumberTextView = view.findViewById(R.id.order_number_text_view)
        customerTextView = view.findViewById(R.id.customer_text_view)
        destinationTextView = view.findViewById(R.id.destination_text_view)
        itemTextView = view.findViewById(R.id.item_text_view)
        priceTextView = view.findViewById(R.id.price_text_view)
        stateTextView = view.findViewById(R.id.state_text_view)
        changelogRecyclerView = view.findViewById(R.id.movement_log_recycler_view)

        changelogAdapter = ChangelogAdapter(emptyList())
        changelogRecyclerView.layoutManager = LinearLayoutManager(context)
        changelogRecyclerView.adapter = changelogAdapter
    }

    /**
     * Observes changes to the order details and updates the UI accordingly.
     */
    private fun observeOrderDetails() {
        orderDetailsViewModel.orderDetails.observe(viewLifecycleOwner) { state ->
            when {
                state.isLoading -> {
                    // Show a loading indicator (e.g., progress bar)
                }
                state.error != null -> {
                    // Handle the error (e.g., display an error message)
                }
                state.order != null -> {
                    // Update the UI with the order details
                    updateOrderDetails(state.order)
                }
            }
        }
    }

    /**
     * Updates the UI with the order details.
     *
     * @param order The order details to display.
     */
    private fun updateOrderDetails(order: Order) {
        orderNumberTextView.text = getString(R.string.order_number, order.id)
        customerTextView.text = order.customer
        priceTextView.text = formatPrice(order.price)
        destinationTextView.text = order.destination
        itemTextView.text = order.item
        stateTextView.text = order.state.name

        val changelogItems = order.changeLog.map { change ->
            ChangelogItem(change.state.name, TimeUtils.getRelativeTimeAgo(change.timestamp))
        }
        changelogAdapter.updateList(changelogItems)
    }

    /**
     * Formats the price to display it in dollars.
     *
     * @param price The price in cents.
     * @return The formatted price in dollars.
     */
    private fun formatPrice(price: Int): String {
        val priceInDollars = price / 100.0
        val format = NumberFormat.getCurrencyInstance(Locale.US)
        return format.format(priceInDollars)
    }
}
