package dk.shape.forecast.weather

import android.content.Context
import android.content.res.ColorStateList
import dk.shape.forecast.R

fun Temperature.toColorStateList(context: Context): ColorStateList {
    return when (unit) {
        TemperatureUnit.Celcius -> {
            val states = array2dOfInt(1, 1)
            val colors = intArrayOf(value.celciusToColor(context = context))
            ColorStateList(states, colors)
        }
    }
}

private fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray> = Array(sizeOuter) { IntArray(sizeInner) }

private fun Int.celciusToColor(context: Context): Int {
    val colorResource = when (this) {
        in Int.MIN_VALUE..-5 -> R.color.temperature1
        in -4..2 -> R.color.temperature2
        in 3..14 -> R.color.temperature3
        in 15..26 -> R.color.temperature4
        else -> R.color.temperature5
    }

    return context.resources.getColor(colorResource)
}