package com.nikhilosatwal.cyntra_assesment.di

import com.nikhilosatwal.cyntra_assesment.DeviceStatusActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: DeviceStatusActivity)
}