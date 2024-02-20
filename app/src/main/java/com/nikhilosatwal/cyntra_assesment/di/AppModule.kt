package com.nikhilosatwal.cyntra_assesment.di

import android.app.Application
import com.nikhilosatwal.cyntra_assesment.viewmodel.DeviceStatusViewModel
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Provides
    fun provideMyViewModel(application: Application): DeviceStatusViewModel {
        return DeviceStatusViewModel(application)
    }
}
