package com.example.fetchrewardstakehome.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fetchrewardstakehome.R
import com.example.fetchrewardstakehome.model.Item
import com.example.fetchrewardstakehome.utilities.AsyncStatus
import com.example.fetchrewardstakehome.viewmodel.ItemsViewModel

class MainActivity : AppCompatActivity() {

    lateinit var itemsViewModel: ItemsViewModel

    lateinit var loadingProgressBar: ProgressBar
    lateinit var errorLinearLayout: LinearLayout
    lateinit var errorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadingProgressBar = findViewById(R.id.progressBar)
        errorLinearLayout = findViewById(R.id.errorLinearLayout)
        errorTextView = findViewById(R.id.errorTextView)

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
        loadingProgressBar.visibility = GONE
        Log.i("Main", items.toString())
    }

    private fun onFailure(message: String?) {
        loadingProgressBar.visibility = GONE
        errorLinearLayout.visibility = VISIBLE
        errorTextView.text = "Error encountered: $message"
    }

    private fun onLoading() {
        Log.i("Main", "Loading items")
    }
}