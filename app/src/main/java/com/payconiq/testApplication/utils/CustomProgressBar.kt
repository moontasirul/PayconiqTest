package com.payconiq.testApplication.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.payconiq.testApplication.R
import com.payconiq.testApplication.databinding.ViewProgressBinding

class CustomProgressBar : ConstraintLayout {
    private lateinit var binding: ViewProgressBinding

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_progress,
            this,
            true
        )
    }
}