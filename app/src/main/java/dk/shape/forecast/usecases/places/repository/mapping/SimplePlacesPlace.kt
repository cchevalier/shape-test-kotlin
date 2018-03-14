package dk.shape.forecast.usecases.places.repository.mapping

import com.google.gson.annotations.SerializedName

data class SimplePlacesPlace(@SerializedName("woeid") val woeid: String)