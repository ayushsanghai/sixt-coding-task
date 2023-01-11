package com.example.sixt.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CarModel {

    @SerializedName("id")
    @Expose
    val id: String? = null

    @SerializedName("modelName")
    @Expose
    val modelName: String? = null

    @SerializedName("color")
    @Expose
    val color: String? = null

    @SerializedName("fuelType")
    @Expose
    val fuelType: String? = null

    @SerializedName("fuelLevel")
    @Expose
    val fuelLevel: Double? = null

    @SerializedName("transmission")
    @Expose
    val transmission: String? = null

    @SerializedName("licensePlate")
    @Expose
    val licensePlate: String? = null

    @SerializedName("latitude")
    @Expose
    val latitude: String? = null

    @SerializedName("longitude")
    @Expose
    val longitude: String? = null

    @SerializedName("innerCleanliness")
    @Expose
    val innerCleanliness: String? = null

    @SerializedName("carImageUrl")
    @Expose
    val carImageUrl: String? = null

}