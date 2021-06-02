package com.example.fashionecommercemobileapp.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Product: Parcelable {
    @SerializedName("idProduct")
    @Expose
    var idProduct: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("idProductCode")
    @Expose
    var idProductCode: String? = null

    @SerializedName("price")
    @Expose
    var price: String? = null

    @SerializedName("quantity")
    @Expose
    var quantity: String? = null

    @SerializedName("unit")
    @Expose
    var unit: String? = null

    @SerializedName("imageFile")
    @Expose
    var imageFile: String? = null

    @SerializedName("discount")
    @Expose
    var discount: String? = null

    @SerializedName("rating")
    @Expose
    var rating: String? = null

    @SerializedName("isDeleted")
    @Expose
    var isDeleted: String? = null

}