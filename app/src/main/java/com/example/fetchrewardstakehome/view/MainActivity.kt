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
import com.example.fetchrewardstakehome.common.AsyncStatus
import com.example.fetchrewardstakehome.common.ItemPair
import com.example.fetchrewardstakehome.viewmodel.ItemsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var itemsViewModel: ItemsViewModel

    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var errorLinearLayout: LinearLayout
    private lateinit var errorTextView: TextView

    private lateinit var expandableListView: ExpandableListView
    private lateinit var expandableListAdapter: ExpandableListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set all the View class members for easy access
        loadingProgressBar = findViewById(R.id.progressBar)
        errorLinearLayout = findViewById(R.id.errorLinearLayout)
        errorTextView = findViewById(R.id.errorTextView)
        expandableListView = findViewById(R.id.expandableListView)

        // Get the ItemViewModel instance
        itemsViewModel = ViewModelProvider(this).get(ItemsViewModel::class.java)

        // Setup Observer for itemsLiveData
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


    private fun onData(itemPair: ItemPair) {
        hideLoadingProgressBar()
        setExpandableListViewAdapter(itemPair)
        expandableListView.visibility = VISIBLE
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

    private fun setExpandableListViewAdapter(itemPair: ItemPair) {
        val (listIds, listIdsToItems) = itemPair
        expandableListAdapter = ItemExpandableListAdapter(this, listIds, listIdsToItems)
        expandableListView.setAdapter(expandableListAdapter)
        expandAllGroups(expandableListAdapter)
    }

    private fun expandAllGroups(expandableListAdapter: ExpandableListAdapter) {
        for (i in 0 until expandableListAdapter.groupCount) {
            expandableListView.expandGroup(i)
        }
    }
}