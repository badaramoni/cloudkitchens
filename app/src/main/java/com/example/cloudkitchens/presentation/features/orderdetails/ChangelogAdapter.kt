package com.example.cloudkitchens.presentation.features.orderdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudkitchens.R

/**
 * Data class representing a changelog item.
 *
 * @property state The state of the order.
 * @property timestamp The timestamp of the state change.
 */
data class ChangelogItem(val state: String, val timestamp: String)

/**
 * Adapter for displaying changelog items in a RecyclerView.
 *
 * @param changelogItems The list of changelog items to display.
 */
class ChangelogAdapter(private var changelogItems: List<ChangelogItem>) :
    RecyclerView.Adapter<ChangelogAdapter.ChangelogViewHolder>() {

    /**
     * Creates a new ViewHolder for a changelog item.
     *
     * @param parent The parent ViewGroup.
     * @param viewType The view type.
     * @return A new ChangelogViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangelogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_changelog, parent, false)
        return ChangelogViewHolder(view)
    }

    /**
     * Binds a changelog item to a ViewHolder.
     *
     * @param holder The ViewHolder to bind to.
     * @param position The position of the item in the list.
     */
    override fun onBindViewHolder(holder: ChangelogViewHolder, position: Int) {
        val changelogItem = changelogItems[position]
        holder.stateTextView.text = changelogItem.state
        holder.timestampTextView.text = changelogItem.timestamp
    }

    /**
     * Returns the number of changelog items.
     *
     * @return The number of changelog items.
     */
    override fun getItemCount(): Int {
        return changelogItems.size
    }

    /**
     * Updates the list of changelog items and notifies the adapter.
     *
     * @param newList The new list of changelog items.
     */
    fun updateList(newList: List<ChangelogItem>) {
        changelogItems = newList
        notifyDataSetChanged()
    }

    /**
     * ViewHolder for a changelog item.
     *
     * @param itemView The view for a changelog item.
     */
    class ChangelogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stateTextView: TextView = itemView.findViewById(R.id.state_text_view)
        val timestampTextView: TextView = itemView.findViewById(R.id.timestamp_text_view)
    }
}
