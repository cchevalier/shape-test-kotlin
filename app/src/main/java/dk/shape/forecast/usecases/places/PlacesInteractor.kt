package dk.shape.forecast.usecases.places

import dk.shape.forecast.usecases.places.repository.Place
import dk.shape.forecast.usecases.places.repository.PlacesRepository

class PlacesInteractor(private val placesRepository: PlacesRepository) {
    var output: PlacesInteractorOutput? = null
        set(value) {
            field = value
            output?.onStateChanged(state)
        }

    var action: PlacesInteractorAction? = null

    private var state: State = State.Loading
        set(value) {
            if (field != value) {
                field = value
                output?.onStateChanged(value)
            }
        }

    sealed class State {
        class Content(val places: List<Place>) : State()
        object Loading : State()
        class Error : State()
    }

    fun start() {
        loadData()
    }

    private fun loadData() {
        state = State.Loading
        placesRepository.places
                .onSuccess { places ->
                    state = State.Content(places = places)
                }
                .onError {
                    state = State.Error()
                }
    }

    val presenterAction = object : PlacesPresenterAction {
        override fun onRetry() {
            loadData()
        }

        override fun onPlaceItemSelected(woeid: String) {
            action?.onPlaceSelected(woeid)
        }
    }
}

interface PlacesInteractorOutput {
    fun onStateChanged(state: PlacesInteractor.State)
}

interface PlacesInteractorAction {
    fun onPlaceSelected(woeid: String)
}