package com.example.sixt.model.service

import com.example.sixt.model.data.CarModel
import retrofit2.Response
import retrofit2.http.GET


interface CarService {
    @GET("/codingtask/cars")
    suspend fun getCarList(): Response<List<CarModel>>
}