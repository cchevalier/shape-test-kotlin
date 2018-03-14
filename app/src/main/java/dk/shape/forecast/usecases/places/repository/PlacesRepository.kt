package dk.shape.forecast.usecases.places.repository

import dk.shape.forecast.api.mapping.QueryResult
import dk.shape.forecast.api.WeatherAPI
import dk.shape.forecast.framework.Promise
import dk.shape.forecast.usecases.places.repository.mapping.SimplePlacesChannels
import dk.shape.forecast.weather.Temperature
import dk.shape.forecast.weather.TemperatureUnit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class PlacesRepository(private val weatherAPI: WeatherAPI,
                       private val woeids: List<String>) {

    private val formattedWoeids = woeids.joinToString(
            separator = ",",
            transform = { it })
    private val locationsQuery = "select location, item.condition from weather.forecast where woeid in ($formattedWoeids) and u='c'"

    val places: Promise<List<Place>>
        get() {
            val promise = Promise<List<Place>>()

            weatherAPI.placesQuery(query = locationsQuery)
                    .enqueue(object : Callback<QueryResult<SimplePlacesChannels?>> {
                        override fun onResponse(call: Call<QueryResult<SimplePlacesChannels?>>, response: Response<QueryResult<SimplePlacesChannels?>>) {
                            if (!response.isSuccessful) {
                                promise.error(IOException("Response was unsuccessful: ${response.code()}"))
                                return
                            }

                            val result = response.body()
                            if (result == null) {
                                promise.error(IOException("Result had no body"))
                                return
                            }

                            val channels = result.query.results
                            channels?.let { channels ->
                                promise.success(channels.asPlaces(woeids))
                            } ?: promise.error(IOException("Query Result had no channels"))
                        }

                        override fun onFailure(call: Call<QueryResult<SimplePlacesChannels?>>, t: Throwable) {
                            promise.error(t)
                        }
                    })

            return promise
        }
}

fun SimplePlacesChannels.asPlaces(woeids: List<String>): List<Place> {
    return woeids
            .zip(channels)
            .mapNotNull { place ->
                val woeid = place.first
                val channel = place.second

                val weatherCode = channel.item.condition.code
                val city = channel.location.city
                val country = channel.location.country
                val temperature = channel.item.condition.temperature.toIntOrNull()

                temperature?.let { temperature ->
                    Place(
                            woeid = woeid,
                            city = city,
                            country = country,
                            temperature = Temperature(
                                    value = temperature,
                                    unit = TemperatureUnit.Celcius),
                            weatherCode = weatherCode)
                }
            }
}