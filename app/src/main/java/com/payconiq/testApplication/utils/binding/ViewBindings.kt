package com.payconiq.testApplication.utils.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField

class ViewBindings {
    companion object {
        @JvmStatic
        @BindingAdapter("visibility")
        fun setVisibilityByInt(view: View, value: ObservableField<Int>) {
            if (value.get() == 0) {
                view.visibility = View.VISIBLE
            } else if (value.get() == 1) {
                view.visibility = View.INVISIBLE
            } else {
                view.visibility = View.GONE
            }
        }

        @JvmStatic
        @BindingAdapter("visibility")
        fun setVisibilityByBool(view: View, value: ObservableBoolean) {
            if (value.get()) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.GONE
            }
        }

        @JvmStatic
        @BindingAdapter("visibility")
        fun setVisibilityByBool(view: View, value: Boolean) {
            if (value) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.GONE
            }
        }
    }
}