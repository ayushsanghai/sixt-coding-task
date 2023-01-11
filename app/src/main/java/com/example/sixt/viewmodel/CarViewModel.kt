package com.example.sixt.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sixt.model.data.CarModel
import com.example.sixt.model.repository.CarRepository
import kotlinx.coroutines.launch

class CarViewModel :ViewModel() {

    private val repository = CarRepository()

    val toastMessageObservable: MutableLiveData<String> = MutableLiveData<String>()
    val isLoadingObservable: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val carList: MutableLiveData<List<CarModel>> by lazy {
        MutableLiveData<List<CarModel>>()
    }
    val selectedCar = MutableLiveData<CarModel>()

    //Fetch car list from the repository
    fun getCarList() {
        viewModelScope.launch {
            isLoadingObservable.value = true
            try{
                val carList = repository.getCarList()
                this@CarViewModel.carList.value = carList
            }catch (e: Exception){
                toastMessageObservable.value="Failed to fetch car list: ${e.message}"
                Log.e("CarViewModel", "Failed to fetch car List: ${e.message}")
            }
            isLoadingObservable.value = true
        }
    }

    //Set car model when marker is selected
    fun setSelectedCar(id:String){
        if(carList.value?.find { it.id==id }!=null){
            selectedCar.value = carList.value?.find { it.id==id }
        }
    }




}