package com.example.train_speed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.train_speed.models.InputData

class SpeedometerViewModel : ViewModel() {
    private var _params = MutableLiveData(InputData(25)) //TODO add room

    // state
    val params: LiveData<InputData> = _params


}