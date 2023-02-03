package com.example.fetchrewardstakehome.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fetchrewardstakehome.R
import com.example.fetchrewardstakehome.adapters.ItemExpandableListAdapter
import com.example.fetchrewardstakehome.model.Item
import com.example.fetchrewardstakehome.utilities.AsyncStatus
import com.example.fetchrewardstakehome.viewmodel.ItemsViewModel

class MainActivity : AppCompatActivity() {

    lateinit var itemsViewModel: ItemsViewModel

    lateinit var loadingProgressBar: ProgressBar
    lateinit var errorLinearLayout: LinearLayout
    lateinit var errorTextView: TextView

    lateinit var expandableListView: ExpandableListView
    lateinit var expandableListAdapter: ExpandableListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadingProgressBar = findViewById(R.id.progressBar)
        errorLinearLayout = findViewById(R.id.errorLinearLayout)
        errorTextView = findViewById(R.id.errorTextView)
        expandableListView = findViewById(R.id.expandableListView)

        itemsViewModel = ViewModelProvider(this).get(ItemsViewModel::class.java)
        itemsViewModel.itemsLiveData.observe(this, Observer { asyncStatus ->
            when (asyncStatus) {
                is AsyncStatus.Loading -> {
                    onLoading()
                }
                is AsyncStatus.Failure -> {
                    onFailure(asyncStatus.message)
                }
                is AsyncStatus.Success -> {
                    onData(asyncStatus.value)
                }
            }
        })
    }


    private fun onData(items: List<Item>) {
        hideLoadingProgressBar()
        val (listIds, listIdsToItems) = formatItemsForDisplay(items)
        setExpandableListViewAdapter(listIds, listIdsToItems)
        Log.i("Main", items.toString())
    }

    private fun onFailure(message: String?) {
        hideLoadingProgressBar()
        errorLinearLayout.visibility = VISIBLE
        errorTextView.text = "Error encountered: $message"
    }

    private fun onLoading() {
        Log.i("Main", "Loading items")
    }

    private fun hideLoadingProgressBar() {
        loadingProgressBar.visibility = GONE
    }

    private fun setExpandableListViewAdapter(listIds: List<Int>, listIdsToItems: Map<Int, List<Item>>) {
        expandableListAdapter = ItemExpandableListAdapter(this, listIds, listIdsToItems)
        expandableListView.setAdapter(expandableListAdapter)
    }

    private fun formatItemsForDisplay(items: List<Item>) : Pair<List<Int>, Map<Int, List<Item>>> {
        val listIdsToItems: MutableMap<Int, MutableList<Item>> = HashMap()

        for (item in items) {
            val key = item.listId
            if (listIdsToItems.containsKey(key)) {
                listIdsToItems[key]!!.add(item)
            } else {
                listIdsToItems[key] = mutableListOf()
            }
        }

        for ((key, _) in listIdsToItems.entries) {
            listIdsToItems[key]!!.sortBy { item -> item.name }
        }

        val listIds = listIdsToItems.keys.toList().sorted()

        return Pair(listIds, listIdsToItems)
    }
}