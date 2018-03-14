package dk.shape.forecast.usecases.places

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import dk.shape.forecast.R
import dk.shape.forecast.config
import kotlinx.android.synthetic.main.places.*

class PlacesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_places)

        config.initPlacesUseCase(
                activity = this,
                presenterOutput = presenterOutput)
    }

    private val presenterOutput = object : PlacesPresenterOutput {
        override fun onStateChanged(state: PlacesPresenter.State) {
            when (state) {
                is PlacesPresenter.State.Content -> {
                    placesRecyclerView.adapter = PlacesAdapter(state.itemConfigs)
                    placesRecyclerView.visibility = View.VISIBLE
                    loadingView.visibility = View.GONE
                    errorView.visibility = View.GONE
                }
                is PlacesPresenter.State.Loading -> {
                    placesRecyclerView.visibility = View.GONE
                    loadingView.visibility = View.VISIBLE
                    errorView.visibility = View.GONE
                }
                is PlacesPresenter.State.Error -> {
                    placesRecyclerView.visibility = View.GONE
                    loadingView.visibility = View.GONE
                    errorView.visibility = View.VISIBLE

                    errorRetryButton.setOnClickListener {
                        state.onRetry()
                    }
                }
            }
        }
    }
}