package hu.bme.sch.monkie.habits.data.network

import com.google.gson.annotations.SerializedName

data class NetworkWeather(
    @SerializedName("current") val current: TheMain,
) {
    data class TheMain(
        @SerializedName("temp") val temp: Double,
        @SerializedName("visibility") val visibility:Double)

}

data class NetworkWeather2(
    @SerializedName("data") val data: List<Data>
) {
    data class Data(
        @SerializedName("temp") val temp: Double,
        @SerializedName("visibility") val visibility: Double
    )
}