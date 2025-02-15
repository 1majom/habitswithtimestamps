
package hu.bme.sch.monkie.habits.feature.habit

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.bme.sch.monkie.habits.BuildConfig
import hu.bme.sch.monkie.habits.data.datasource.DateRepository
import hu.bme.sch.monkie.habits.data.datasource.HabitRepository
import hu.bme.sch.monkie.habits.data.local.database.LocalHabit
import hu.bme.sch.monkie.habits.data.network.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HabitListViewModel @Inject constructor(
    private val habitRepository: HabitRepository,
    private val dateRepository: DateRepository,
    private val api: WeatherApi,
    @SuppressLint("StaticFieldLeak") @ApplicationContext private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient,

    ) : ViewModel() {
    private var _uiState = MutableStateFlow(HabitListUiState())
    val uiState = _uiState.asStateFlow()

    var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    init {
        viewModelScope.launch( Dispatchers.IO) {
            var currentLatLng = requestLocation()

            if (currentLatLng == null) {
                Log.i("HabitListViewModel", "currentLatLng is null")
                currentLatLng = LatLng(47.498,19.0399)
            }
            else{
                Log.i("HabitListViewModel", "currentLatLng is not null, it worked ${currentLatLng.lat} ${currentLatLng.lon}")
            }

            var weather = api.getCurrentWeather(currentLatLng.lat.toString(), currentLatLng.lon.toString(), "metric", BuildConfig.API_KEY, "hourly,daily,minutely")
            _uiState.update { state ->
                state.copy(
                    temperature = weather.current.temp, visibility = weather.current.visibility
                )
            }
        }
        viewModelScope.launch( Dispatchers.IO) {
            habitRepository.getAll().collect{
                _uiState.value=_uiState.value.copy(isLoading = false,habits=it, null)
            }
        }
    }
    fun addNewHabit(new:String){
        viewModelScope.launch(Dispatchers.IO) {
            if (new.isEmpty()){
                _uiState.update { it.copy(errorMsg = "Name cannot be empty") }
                return@launch
            }
            habitRepository.add(new)
            firebaseAnalytics.logEvent("new_habit") {
                param("name", new)
            }
        }
    }
    fun addNew(i:Int){
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("HabitListViewModel", "!!!!!!!!!!!!addNew: ${uiState.value.temperature}   ${uiState.value.visibility}")
            dateRepository.add(i, uiState.value.temperature?:0.0,uiState.value.visibility?:0.0)
        }
    }
    fun deleteLast(i:Int){
        viewModelScope.launch( Dispatchers.IO) {
            dateRepository.deleteLastFromHabit(i)
        }
    }
    @SuppressLint("MissingPermission")
    suspend fun requestLocation(): LatLng? {
        try {
            if (hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) || hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                val priority =
                    if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) Priority.PRIORITY_HIGH_ACCURACY else Priority.PRIORITY_BALANCED_POWER_ACCURACY
                Log.i("Location", priority.toString())
                val result = try {
                    fusedLocationClient.getCurrentLocation(
                        priority,
                        CancellationTokenSource().token,
                    ).await()
                } catch (e: Exception) {
                    Log.e("Location", "Error requesting location", e)
                    null
                }

                return result?.let { LatLng(it.latitude, it.longitude) }
            }
            Log.i("Location", "No permission")
        } catch (e: Exception) {
            Log.e("Location", "Error in requestLocation", e)
        }

        return null
    }

    private fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }




    data class LatLng(
        val lat: Double,
        val lon: Double
    )
}
data class HabitListUiState (
    var isLoading:Boolean=true,
    var habits: List<LocalHabit> = listOf(),
    val errorMsg: String? = null,
    val temperature:Double?=null,
    val visibility:Double?=null


)
