package com.dicoding2.glucofy.ui.costumview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding2.glucofy.R

class CostumButton : AppCompatButton {

    // Konstruktor dari MyButton
    constructor(context: Context) : super(context) // untuk di Activity/Fragment
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)  // untuk di XML

    private var txtColor: Int = 0
    private var enabledBackground: Drawable
    private var disabledBackground: Drawable

    // pemanggilan Resource harus dilakukan saat kelas MyButton diinisialisasi, jadi harus dikeluarkan dari metode onDraw
    init {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button) as Drawable
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_disabled) as Drawable
    }

    // Metode onDraw() digunakan untuk mengcustom button ketika enable dan disable
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if(isEnabled) enabledBackground else disabledBackground

        setTextColor(txtColor)

        gravity = Gravity.CENTER
    }
}