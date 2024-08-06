package com.example.cloudkitchens.presentation.features.orderdetails.orderdetailsviewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cloudkitchens.domain.model.Order
import com.example.cloudkitchens.domain.model.OrderState
import com.example.cloudkitchens.domain.model.ShelfType
import com.example.cloudkitchens.domain.usecase.GetOrderDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class OrderDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase = mock()
    private lateinit var viewModel: OrderDetailsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = OrderDetailsViewModel(getOrderDetailsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchOrderDetails success updates LiveData with order details`() = runTest {
        val testOrder = Order(
            id = "1",
            price = 10,
            item = "Burger",
            customer = "John Doe",
            destination = "123 Main St",
            state = OrderState.CREATED,
            shelf = ShelfType.HOT,
            idealShelf = ShelfType.HOT,
            changeLog = mutableListOf() // Added missing changeLog property
        )
        whenever(getOrderDetailsUseCase.execute("1")).thenReturn(Result.success(testOrder))

        viewModel.fetchOrderDetails("1")

        val expectedState = OrderDetailsViewState(order = testOrder)
        assertEquals(expectedState, viewModel.orderDetails.value)
    }

    @Test
    fun `fetchOrderDetails failure updates LiveData with OrderNotFound error`() = runTest {
        whenever(getOrderDetailsUseCase.execute("nonexistent")).thenReturn(Result.failure(NoSuchElementException()))

        viewModel.fetchOrderDetails("nonexistent")

        val expectedState = OrderDetailsViewState(error = OrderDetailsError.OrderNotFound)
        assertEquals(expectedState, viewModel.orderDetails.value)
    }

    @Test
    fun `fetchOrderDetails failure updates LiveData with UnknownError for other exceptions`() = runTest {
        whenever(getOrderDetailsUseCase.execute("error")).thenReturn(Result.failure(Exception("Some error")))

        viewModel.fetchOrderDetails("error")

        val expectedState = OrderDetailsViewState(error = OrderDetailsError.UnknownError)
        assertEquals(expectedState, viewModel.orderDetails.value)
    }
}