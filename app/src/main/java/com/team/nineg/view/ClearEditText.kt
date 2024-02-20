package com.team.nineg.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.team.nineg.R

class ClearEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), OnTouchListener, OnFocusChangeListener {
    private lateinit var clearDrawable: Drawable
    private var focusChangeListener: OnFocusChangeListener? = null
    private var touchListener: OnTouchListener? = null

    init {
        init()
    }

    override fun setOnFocusChangeListener(onFocusChangeListener: OnFocusChangeListener) {
        this.focusChangeListener = onFocusChangeListener
    }

    override fun setOnTouchListener(onTouchListener: OnTouchListener) {
        this.touchListener = onTouchListener
    }

    private fun init() {
        val tempDrawable = ContextCompat.getDrawable(context, R.drawable.ic_shape_cancel)
        clearDrawable = DrawableCompat.wrap(tempDrawable!!)
        DrawableCompat.setTintList(clearDrawable, hintTextColors)
        clearDrawable.setBounds(
            0,
            0,
            clearDrawable.intrinsicWidth,
            clearDrawable.intrinsicHeight
        )
        setClearIconVisible(false)
        super.setOnTouchListener(this)
        super.setOnFocusChangeListener(this)
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        setClearIconVisible(hasFocus)
        focusChangeListener?.onFocusChange(view, hasFocus)
    }

    override fun onTouch(view: View?, motionEvent: MotionEvent): Boolean {
        val x = motionEvent.x.toInt()
        if (clearDrawable.isVisible && x > width - paddingRight - clearDrawable.intrinsicWidth) {
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                error = null
                text = null
            }
            return true
        }

        return touchListener?.onTouch(view, motionEvent) ?: false
    }

    private fun setClearIconVisible(visible: Boolean) {
        clearDrawable.setVisible(visible, false)
        setCompoundDrawables(null, null, if (visible) clearDrawable else null, null)
    }
}