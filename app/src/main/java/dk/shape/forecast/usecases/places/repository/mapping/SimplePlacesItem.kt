package dk.shape.forecast.usecases.places.repository.mapping

import com.google.gson.annotations.SerializedName

data class SimplePlacesItem(@SerializedName("condition") val condition: SimplesPlacesCondition)