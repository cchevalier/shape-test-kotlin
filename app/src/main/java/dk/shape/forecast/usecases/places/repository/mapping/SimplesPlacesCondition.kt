package dk.shape.forecast.usecases.places.repository.mapping

import com.google.gson.annotations.SerializedName

data class SimplesPlacesCondition(@SerializedName("temp") val temperature: String,
                                  @SerializedName("code") val code: String)