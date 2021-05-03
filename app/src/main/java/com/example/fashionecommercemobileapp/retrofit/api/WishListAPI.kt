package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.Product
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface WishListAPI {
    @FormUrlEncoded
    @POST("get_wishlistbyid.php")
    fun getWishListProduct(@Field ("idAccount" ) idAccount : Int) : Call<List<Product>>

    @FormUrlEncoded
    @POST("post_deletewishproduct.php")
    fun deleteWishProduct(@Field ("idAccount") idAccount: Int,
                          @Field ("idProduct") idProduct : Int)  : Call<String>
}