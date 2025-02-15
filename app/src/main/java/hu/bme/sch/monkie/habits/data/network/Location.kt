package hu.bme.sch.monkie.habits.data.network

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class Location @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Application
)
{
    @SuppressLint("MissingPermission")
    suspend fun requestLocation(): LatLng? {
        if (hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) || hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            val priority =
                if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) Priority.PRIORITY_HIGH_ACCURACY else Priority.PRIORITY_BALANCED_POWER_ACCURACY
            Log.i("Location", priority.toString())
            val result = fusedLocationClient.getCurrentLocation(
                priority,
                CancellationTokenSource().token,
            ).await()

            return result?.let { LatLng(it.latitude, it.longitude) }
        }
        Log.i("Location", "No permission")

        return null
    }

    private fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

}


data class LatLng(
    val lat: Double,
    val lon: Double
)