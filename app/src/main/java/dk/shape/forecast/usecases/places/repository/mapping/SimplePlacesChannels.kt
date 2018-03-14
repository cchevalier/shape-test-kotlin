package dk.shape.forecast.usecases.places.repository.mapping

import com.google.gson.annotations.SerializedName

data class SimplePlacesChannels(@SerializedName("channel") val channels: List<SimplePlacesChannel>)