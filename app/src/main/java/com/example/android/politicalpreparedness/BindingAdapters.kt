package com.example.android.politicalpreparedness

import android.view.View
import android.widget.ProgressBar
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