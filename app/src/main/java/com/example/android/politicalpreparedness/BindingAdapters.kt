package com.example.android.politicalpreparedness

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.politicalpreparedness.network.ApiStatus

@BindingAdapter("apiStatus")
fun bindStatus(progressbar: ProgressBar, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            progressbar.visibility = View.VISIBLE
        }
        ApiStatus.ERROR -> {
            progressbar.visibility = View.GONE
        }
        ApiStatus.DONE -> {
            progressbar.visibility = View.GONE
        }
    }
}

@BindingAdapter("errorMessage")
fun bindErrorVisibility(view: TextView, status: ApiStatus?){
    if (status == ApiStatus.ERROR){
        view.visibility = View.VISIBLE
    }
    else{
        view.visibility = View.GONE
    }
}