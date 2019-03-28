package com.softsandr.placesearch.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import androidx.appcompat.widget.AppCompatImageView

/**
 * Created by Oleksandr Drachuk on 28.03.19.
 */
class CheckableImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs), Checkable {
    private var checked: Boolean = false

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

    override fun toggle() {
        isChecked = !checked
    }

    override fun isChecked(): Boolean {
        return checked
    }

    override fun setChecked(checked: Boolean) {
        if (this.checked == checked)
            return

        this.checked = checked
        refreshDrawableState()
    }

    companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }
}
