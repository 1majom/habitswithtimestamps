package hu.bme.sch.monkie.habits.data.network

data class NetworkWeather (
    val id: Int = 0,
    val temp: Int = 0
)
interface WeatherApi {
    suspend fun getWeather(): NetworkWeather
}