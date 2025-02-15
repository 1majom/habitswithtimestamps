package hu.bme.sch.monkie.habits.data.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.sch.monkie.habits.data.network.Location
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocationDataModule {
    @Provides
    @Singleton
    fun provideFusedLocationClient(application: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application)
    }
    @Provides
    fun provideLocation(application: Application, fusedLocationClient: FusedLocationProviderClient): Location {
        return Location(fusedLocationClient, application)
    }
}