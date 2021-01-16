package com.elpath.managementshipping.Network

import com.elpath.managementshipping.Login.LoginResponse
import com.elpath.managementshipping.Order.OrderRespon
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

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


}