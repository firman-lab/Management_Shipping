package com.elpath.managementshipping.Network

import com.elpath.managementshipping.Login.LoginResponse
import com.elpath.managementshipping.Order.OrderRespon
import com.elpath.managementshipping.Order.ResponseReq
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.lang.StringBuilder

interface Api {
    @Headers("Accept-Encoding: identity")
    @FormUrlEncoded
    @POST("login.php?apicall=login")
    fun login(
        @Field("username") username:String,
        @Field("password") password:String
    ): Call<LoginResponse>


    @Headers("Accept-Encoding: identity")
    @POST("loadorder.php")
    suspend fun orders(
    ): Response<OrderRespon>

    @Headers("Accept-Encoding: identity")
    @FormUrlEncoded
    @POST("insert.php?apicall=konfirmasi")
    fun konfirmasiOrder(
        @Field("id_order")id_order:String,
        @Field("status") status : String
    ): Call<ResponseReq>

    @Headers("Accept-Encoding: identity")
    @FormUrlEncoded
    @POST("insert.php?apicall=insertorder")
    fun addOrder(
        @Field("nama_barang") nama_barang:String,
        @Field("jumlah_barang") jumlah_barang : String,
        @Field("nama_pembeli") nama_pembeli :String,
        @Field("kurir") kurir:String,
        @Field("kode_kirim") kode_kirim :String,
        @Field("alamat") Alamat :String
    ): Call<ResponseReq>



}