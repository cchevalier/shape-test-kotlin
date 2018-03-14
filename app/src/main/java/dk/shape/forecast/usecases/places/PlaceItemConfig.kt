package dk.shape.forecast.usecases.places

import android.content.res.ColorStateList

class PlaceItemConfig(val city: String,
                      val country: String,
                      val temperature: String,
                      val temperatureTintList: ColorStateList,
                      val onClick: () -> Unit)