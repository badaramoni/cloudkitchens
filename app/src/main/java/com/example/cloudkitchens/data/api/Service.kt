package com.example.cloudkitchens.data.api

/**
 * Provides API endpoints for fetching order events.
 */

import com.example.cloudkitchens.data.model.OrderEvent
import retrofit2.Response
import retrofit2.http.GET

interface Service {

   /**
    * Fetches a list of order events from the server.
    *
    * @return A Response object containing a list of [OrderEvent] objects, or an error response.
    */
   @GET("/order_events")
   suspend fun getOrderEvent(): Response<List<OrderEvent>>
}