package com.example.fetchrewardstakehome.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.fetchrewardstakehome.api.ItemsAPI
import com.example.fetchrewardstakehome.model.Item
import com.example.fetchrewardstakehome.common.AsyncStatus
import com.example.fetchrewardstakehome.common.ItemPair

class ItemsViewModel : ViewModel() {
    var itemsLiveData: LiveData<AsyncStatus<ItemPair>> = liveData {
        // Inform the view that the data is being loaded
        emit(AsyncStatus.Loading())

        try {
            // Call the API to get the data
            val response = ItemsAPI.getInstance().getItems()
            Log.i("ItemsViewModel", "Response: ${response.body()}")

            // If the response body is not null and the status code is OK
            if (response.body() != null && response.code() == 200) {
                val items: List<Item> = response.body()!!
                // Format the items into the list of listIds and the Map of listIds to list of Items
                // as required by the view (ExpandableListView)
                emit(AsyncStatus.Success(formatItemsIntoItemPair(items)))
            }

            // If the body of the response is null, inform the view of the error
            else {
                emit(AsyncStatus.Failure("API call body returned null"))
            }
        } catch (e: Exception) {
            // If an exception is encountered, inform the view of the error
            Log.e("ItemsViewModel", "Error: $e")
            emit(AsyncStatus.Failure("API call failed with error: $e"))

        }
    }

    private fun formatItemsIntoItemPair(rawItems: List<Item>) : ItemPair {
        // Filter the items with empty or null names
        val items = rawItems.filter { item -> item.name != null && item.name != "" }

        val listIdsToItems: MutableMap<Int, MutableList<Item>> = HashMap()

        // For each listId, create a list of items with the same listId
        // and keep track of them in the listIdsToItems Map
        for (item in items) {
            val key = item.listId
            if (listIdsToItems.containsKey(key)) {
                listIdsToItems[key]!!.add(item)
            } else {
                listIdsToItems[key] = mutableListOf()
            }
        }

        // For each listId, sort its list of items
        for ((listId, _) in listIdsToItems.entries) {
            listIdsToItems[listId]!!.sortBy { item -> item.name }
        }

        // Create the sorted list of listIds
        val listIds = listIdsToItems.keys.toList().sorted()

        return Pair(listIds, listIdsToItems)
    }
}