package dk.shape.forecast.usecases.places

import android.app.Activity
import dk.shape.forecast.usecases.places.repository.PlacesRepository

fun placesUseCase(activity: Activity,
                  presenterOutput: PlacesPresenterOutput,
                  placesRepository: PlacesRepository) {

    val interactor = PlacesInteractor(placesRepository = placesRepository)
    val presenter = PlacesPresenter(context = activity)
    val router = PlacesRouter(activity)

    interactor.output = presenter.interactorOutput
    interactor.action = router.interactorAction
    presenter.output = presenterOutput
    presenter.action = interactor.presenterAction

    interactor.start()
}