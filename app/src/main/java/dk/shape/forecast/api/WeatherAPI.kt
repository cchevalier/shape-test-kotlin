package dk.shape.forecast.api

import dk.shape.forecast.api.mapping.QueryResult
import dk.shape.forecast.usecases.places.repository.mapping.SimplePlacesChannels
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val YQL = "/v1/public/yql"

interface WeatherAPI {
    @GET(YQL)
    fun placesQuery(@Query("q") query: String,
                    @Query("format") format: String = "json"): Call<QueryResult<SimplePlacesChannels?>>
}

fun initWeatherAPI(): WeatherAPI {
    val retrofit = Retrofit.Builder()
            .baseUrl("https://query.yahooapis.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    return retrofit.create(WeatherAPI::class.java)
}