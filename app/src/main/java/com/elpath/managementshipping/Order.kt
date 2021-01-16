package com.elpath.managementshipping

data class Order (
    val alamat: String,
    val date_order: String,
    val nama_barang:String,
    val jumlah_barang : String,
    val id_order: String,
    val kode_kirim: String,
    val kurir: String,
    val nama_pembeli: String,
    var status: String
    )