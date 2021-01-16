package com.elpath.managementshipping.Order

import com.elpath.managementshipping.Order.Order

data class OrderRespon(
    val value : String,
    val response : ArrayList<Order>,
    val message : String
)