package dk.shape.forecast.usecases.places.repository.mapping

import com.google.gson.annotations.SerializedName

data class SimplePlacesChannel(@SerializedName("location") val location: Location,
                               @SerializedName("item") val item: SimplePlacesItem)