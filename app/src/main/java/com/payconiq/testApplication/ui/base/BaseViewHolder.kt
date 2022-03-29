package com.payconiq.testApplication.ui.base


import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Parent ViewHolder class of All RecyclerView.ViewHolder Class.
 * RecyclerView.ViewHolder class must inherit this base class
 */
abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(position: Int)
}
