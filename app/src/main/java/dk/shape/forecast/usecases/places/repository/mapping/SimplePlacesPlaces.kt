package dk.shape.forecast.usecases.places.repository.mapping

import com.google.gson.annotations.SerializedName

data class SimplePlacesPlaces(@SerializedName("places") val places: List<SimplePlacesPlace>)