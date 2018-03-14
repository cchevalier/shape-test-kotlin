package dk.shape.forecast.usecases.places.repository

import dk.shape.forecast.weather.Temperature

data class Place(val woeid: String,
                 val city: String,
                 val country: String,
                 val temperature: Temperature,
                 val weatherCode: String)