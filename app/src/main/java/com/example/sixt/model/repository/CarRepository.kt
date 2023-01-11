package com.example.sixt.model.repository

import com.example.sixt.model.data.CarModel
import com.example.sixt.model.service.CarService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CarRepository {

    // Retrofit instance with baseurl
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://cdn.sixt.io")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val carService = retrofit.create(CarService::class.java)

    suspend fun getCarList(): List<CarModel>? {

        val response = carService.getCarList()
        if (response.isSuccessful) {
            if (response.body() != null) {
                return response.body()!!
            }
        }

        return null
    }

}

