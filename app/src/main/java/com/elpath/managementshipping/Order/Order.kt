package com.elpath.managementshipping.Order

import android.os.Parcel
import android.os.Parcelable

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
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(alamat)
        parcel.writeString(date_order)
        parcel.writeString(nama_barang)
        parcel.writeString(jumlah_barang)
        parcel.writeString(id_order)
        parcel.writeString(kode_kirim)
        parcel.writeString(kurir)
        parcel.writeString(nama_pembeli)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }
}