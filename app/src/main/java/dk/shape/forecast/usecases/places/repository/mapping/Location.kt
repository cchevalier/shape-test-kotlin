package dk.shape.forecast.usecases.places.repository.mapping

import com.google.gson.annotations.SerializedName

data class Location(@SerializedName("city") val city: String,
                    @SerializedName("country") val country: String,
                    @SerializedName("region") val region: String)