package com.example.fetchrewardstakehome.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.fetchrewardstakehome.api.ItemsAPI
import com.example.fetchrewardstakehome.model.Item
import com.example.fetchrewardstakehome.utilities.AsyncStatus

class ItemsViewModel : ViewModel() {
    var itemsLiveData: LiveData<AsyncStatus<List<Item>>> = liveData {
        emit(AsyncStatus.Loading())
        try {
            val response = ItemsAPI.create().getItems()
            Log.i("Response", response.body().toString())
            if (response.body() != null) {
                var items: List<Item> = response.body()!!
                items = items.filter { item -> item.name != null && item.name != "" }
                emit(AsyncStatus.Success(items))
            } else {
                emit(AsyncStatus.Failure("API Call body returned null"))
            }
        } catch (e: Exception) {
            Log.e("Error", e.toString())
            emit(AsyncStatus.Failure("API call failed with error: $e"))
        }
    }

}