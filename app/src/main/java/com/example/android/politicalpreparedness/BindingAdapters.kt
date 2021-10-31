package com.example.android.politicalpreparedness

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.politicalpreparedness.network.ApiStatus

@BindingAdapter("apiStatus")
fun bindStatus(progressbar: ProgressBar, status: ApiStatus?) {
    progressbar.visibility = View.GONE
    if (status == ApiStatus.LOADING)
        progressbar.visibility = View.VISIBLE
}

@BindingAdapter("errorMessage")
fun bindErrorVisibility(view: TextView, status: ApiStatus?){
    view.visibility = View.GONE
    if (status == ApiStatus.ERROR)
        view.visibility = View.VISIBLE
}