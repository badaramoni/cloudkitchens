package com.example.cloudkitchens.presentation.features.orderstatistics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cloudkitchens.R
import com.example.cloudkitchens.domain.model.OrderStatistics
import com.example.cloudkitchens.presentation.di.Injector
import com.example.cloudkitchens.presentation.features.orderstatistics.orderstatisticsviewmodel.OrderStatisticsViewModel
import com.example.cloudkitchens.presentation.features.orderstatistics.orderstatisticsviewmodel.OrderStatisticsViewModelFactory
import com.example.cloudkitchens.presentation.features.orderstatistics.orderstatisticsviewmodel.OrderStatisticsViewState
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import javax.inject.Inject

/**
 * Fragment for displaying order statistics.
 */
class OrderStatisticsFragment : Fragment() {

    @Inject
    lateinit var factory: OrderStatisticsViewModelFactory
    private lateinit var orderViewModel: OrderStatisticsViewModel

    private lateinit var tvOrdersTrashed: TextView
    private lateinit var tvOrdersDelivered: TextView
    private lateinit var tvTotalSales: TextView
    private lateinit var tvTotalWaste: TextView
    private lateinit var tvTotalRevenue: TextView
    private lateinit var pieChart: PieChart
    private lateinit var progressBar: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inject dependencies
        (requireActivity().application as Injector).createStatisticSubComponent().inject(this)
        orderViewModel = ViewModelProvider(this, factory).get(OrderStatisticsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_statistics, container, false)
        tvOrdersTrashed = view.findViewById(R.id.tv_orders_trashed)
        tvOrdersDelivered = view.findViewById(R.id.tv_orders_delivered)
        tvTotalSales = view.findViewById(R.id.tv_total_sales)
        tvTotalWaste = view.findViewById(R.id.tv_total_waste)
        tvTotalRevenue = view.findViewById(R.id.tv_total_revenue)
        pieChart = view.findViewById(R.id.pieChart)
        progressBar = view.findViewById(R.id.progress_bar)

        observeViewModel()
        setupPieChart()

        return view
    }

    /**
     * Observes the view model for changes in order statistics.
     */
    private fun observeViewModel() {
        orderViewModel.viewState.observe(viewLifecycleOwner) { state ->
            updateUi(state)
        }
    }

    /**
     * Updates the UI based on the current view state.
     *
     * @param state The current view state.
     */
    private fun updateUi(state: OrderStatisticsViewState) {
        progressBar.isVisible = state.isLoading

        state.statistics?.let { stats ->
            updateDashboard(stats)
        }

        state.error?.let { error ->
            // Log the error for debugging
            // Log.e("OrderStatisticsFragment", "Error: $error")
            // handleStatisticsError(error)
        }
    }

    /**
     * Updates the dashboard with the given order statistics.
     *
     * @param stats The order statistics.
     */
    private fun updateDashboard(stats: OrderStatistics) {
        tvOrdersTrashed.text = stats.ordersTrashed.toString()
        tvOrdersDelivered.text = stats.ordersDelivered.toString()
        tvTotalSales.text = "$${stats.totalSales}"
        tvTotalWaste.text = "$${stats.totalWaste}"
        tvTotalRevenue.text = "$${stats.totalRevenue}"

        // Update Pie Chart
        val entries = listOf(
            PieEntry(stats.ordersTrashed.toFloat(), "Orders Trashed"),
            PieEntry(stats.ordersDelivered.toFloat(), "Orders Delivered"),
            PieEntry(stats.totalSales.toFloat(), "Total Sales"),
            PieEntry(stats.totalWaste.toFloat(), "Total Waste"),
            PieEntry(stats.totalRevenue.toFloat(), "Total Revenue")
        )
        val dataSet = PieDataSet(entries, "Order Statistics").apply {
            colors = listOf(
                ColorTemplate.rgb("#ff6347"), // Orders Trashed
                ColorTemplate.rgb("#32cd32"), // Orders Delivered
                ColorTemplate.rgb("#ffd700"), // Total Sales
                ColorTemplate.rgb("#8a2be2"), // Total Waste
                ColorTemplate.rgb("#ff7f50")  // Total Revenue
            )
            valueTextSize = 12f
            valueTextColor = Color.WHITE
            sliceSpace = 3f
        }
        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.invalidate() // Refresh the chart
    }

    /**
     * Sets up the pie chart with initial configuration.
     */
    private fun setupPieChart() {
        pieChart.apply {
            description.isEnabled = false
            isDrawHoleEnabled = true
            holeRadius = 45f
            setHoleColor(Color.TRANSPARENT)
            setTransparentCircleAlpha(110)
            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleRadius(50f)
            setEntryLabelColor(Color.BLACK)
            setEntryLabelTextSize(14f)
            setUsePercentValues(true)
            legend.isEnabled = true
            legend.textSize = 12f
            legend.textColor = Color.BLACK
            legend.isWordWrapEnabled = true
            animateY(1400)
        }
    }
}
