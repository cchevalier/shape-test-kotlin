package dk.shape.forecast

import android.app.Activity
import dk.shape.forecast.api.WeatherAPI
import dk.shape.forecast.api.initWeatherAPI
import dk.shape.forecast.usecases.places.PlacesPresenterOutput
import dk.shape.forecast.usecases.places.placesUseCase
import dk.shape.forecast.usecases.places.repository.PlacesRepository

interface Config {

    fun initPlacesUseCase(activity: Activity,
                          presenterOutput: PlacesPresenterOutput)
}

val config = object : Config {
    private val woeids = listOf(
            "23511822",
            "20229273",
            "28527372",
            "1940345",
            "472856",
            "7225969",
            "23512022",
            "455825",
            "567558",
            "56448143",
            "12906618",
            "854823",
            "55902508")

    private val weatherAPI by lazy { initWeatherAPI() }

    override fun initPlacesUseCase(activity: Activity,
                                   presenterOutput: PlacesPresenterOutput) {
        placesUseCase(
                activity = activity,
                presenterOutput = presenterOutput,
                placesRepository = PlacesRepository(
                        weatherAPI = weatherAPI,
                        woeids = woeids))
    }
}