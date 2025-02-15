package hu.bme.sch.monkie.habits.data.network

import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {
    @GET("data/3.0/onecall")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String,
        @Query("appid") appid: String,
        @Query("exclude") exclude: String,
        ): NetworkWeather

    @GET("data/3.0/onecall/timemachine")
    suspend fun getPastWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String,
        @Query("appid") appid: String,
        @Query("dt") dt: String,

        ): NetworkWeather2

}




class FakeWeatherApi : WeatherApi {
    override suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        units: String,
        apiKey: String,
        exclude: String
    ): NetworkWeather {
        return NetworkWeather(
            NetworkWeather.TheMain(
                temp = 20.0,
                visibility = 10000.0
            )
        )
    }

    override suspend fun getPastWeather(
        lat: String,
        lon: String,
        units: String,
        appid: String,
        dt: String
    ): NetworkWeather2 {
        return NetworkWeather2(
            data = listOf(
                NetworkWeather2.Data(
                    temp = 15.0,
                    visibility = 8000.0
                )
            )
        )
    }
}
