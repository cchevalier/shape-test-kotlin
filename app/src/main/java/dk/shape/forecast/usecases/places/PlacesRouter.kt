package dk.shape.forecast.usecases.places

import android.app.Activity
import android.util.Log
import java.lang.ref.WeakReference

class PlacesRouter(activity: Activity) {

    val activityRef = WeakReference<Activity>(activity)

    val interactorAction = object : PlacesInteractorAction {
        override fun onPlaceSelected(woeid: String) {
            Log.d("PlacesRouter", "No forecast to show for WOEID $woeid, please implement this")
            // TODO: Show the forecast for the location identified by the supplied WOEID
        }
    }
}