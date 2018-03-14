package dk.shape.forecast.usecases.places

import android.content.Context
import dk.shape.forecast.weather.toColorStateList

class PlacesPresenter(private val context: Context) {

    var output: PlacesPresenterOutput? = null
        set(value) {
            field = value
            output?.onStateChanged(state)
        }

    var action: PlacesPresenterAction? = null

    private var state: State = State.Loading
        set(value) {
            if (field != value) {
                field = value
                output?.onStateChanged(value)
            }
        }

    sealed class State {
        class Content(val itemConfigs: List<PlaceItemConfig>) : State()
        object Loading : State()
        class Error(val onRetry: () -> Unit) : State()
    }

    val interactorOutput = object : PlacesInteractorOutput {
        override fun onStateChanged(state: PlacesInteractor.State) {
            this@PlacesPresenter.state = when (state) {
                is PlacesInteractor.State.Content -> state.map(
                        context = context,
                        onPlaceItemSelected = { woeid ->
                            action?.onPlaceItemSelected(woeid)
                        })
                is PlacesInteractor.State.Loading -> State.Loading
                is PlacesInteractor.State.Error -> State.Error(
                        onRetry = { action?.onRetry() }
                )
            }
        }
    }
}

private fun PlacesInteractor.State.Content.map(context: Context, onPlaceItemSelected: (String) -> Unit): PlacesPresenter.State.Content {
    val itemConfigs = places.map { place ->
        PlaceItemConfig(
                city = place.city,
                country = place.country,
                temperature = "${place.temperature.value} ${place.temperature.unit.postFix}",
                temperatureTintList = place.temperature.toColorStateList(context = context),
                onClick = {
                    onPlaceItemSelected(place.woeid)
                })
    }
    return PlacesPresenter.State.Content(itemConfigs = itemConfigs)
}

interface PlacesPresenterOutput {
    fun onStateChanged(state: PlacesPresenter.State)
}

interface PlacesPresenterAction {
    fun onPlaceItemSelected(woeid: String)
    fun onRetry()
}