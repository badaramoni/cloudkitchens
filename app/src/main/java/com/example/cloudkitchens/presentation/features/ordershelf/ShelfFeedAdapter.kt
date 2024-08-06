package com.example.cloudkitchens.presentation.features.ordershelf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudkitchens.R
import com.example.cloudkitchens.domain.model.Order
import com.example.cloudkitchens.domain.model.OrderState

/**
 * Adapter for displaying orders in a RecyclerView.
 *
 * @param orders The list of orders to display.
 * @param onItemClick The callback to invoke when an order item is clicked.
 */
class ShelfFeedAdapter(
    private var orders: List<Order>,
    private val onItemClick: (Order) -> Unit
) : RecyclerView.Adapter<ShelfFeedAdapter.OrderViewHolder>() {

    /**
     * Creates a new ViewHolder for an order item.
     *
     * @param parent The parent ViewGroup.
     * @param viewType The view type.
     * @return A new OrderViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        )
    }

    /**
     * Binds an order to a ViewHolder.
     *
     * @param holder The ViewHolder to bind to.
     * @param position The position of the order in the list.
     */
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        with(holder.itemView) {
            findViewById<TextView>(R.id.item).text = order.idealShelf.name
            findViewById<TextView>(R.id.order_number).text = order.item
            findViewById<TextView>(R.id.state).text = order.state.name
            findViewById<TextView>(R.id.time).text = order.changeLog.lastOrNull()?.timestamp?.let {
                TimeUtils.getRelativeTimeAgo(it)
            }
            val stateImageRes = when (order.state) {
                OrderState.CREATED -> R.drawable.created
                OrderState.COOKING -> R.drawable.cooking
                OrderState.WAITING -> R.drawable.waiting
                OrderState.DELIVERED -> R.drawable.delivered
                OrderState.TRASHED -> R.drawable.trashed
                OrderState.CANCELLED -> R.drawable.cancelled
                else -> R.drawable.check_circle // Default image if state is unknown
            }
            findViewById<ImageView>(R.id.icon).setImageResource(stateImageRes)

            setOnClickListener {
                onItemClick(order)
            }
        }
    }

    /**
     * Returns the number of orders.
     *
     * @return The number of orders.
     */
    override fun getItemCount(): Int = orders.size

    /**
     * Updates the list of orders and notifies the adapter.
     *
     * @param newList The new list of orders.
     */
    fun updateList(newList: List<Order>) {
        orders = newList
        notifyDataSetChanged()
    }

    /**
     * ViewHolder for an order item.
     *
     * @param itemView The view for an order item.
     */
    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
