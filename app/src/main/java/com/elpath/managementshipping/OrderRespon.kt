package com.elpath.managementshipping

data class OrderRespon(
    val value : String,
    val response : ArrayList<Order>,
    val message : String
)