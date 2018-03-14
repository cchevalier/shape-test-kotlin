package dk.shape.forecast.weather

data class Temperature(val value: Int,
                       val unit: TemperatureUnit)

sealed class TemperatureUnit(val postFix: String) {
    object Celcius : TemperatureUnit(postFix = "°C")
}