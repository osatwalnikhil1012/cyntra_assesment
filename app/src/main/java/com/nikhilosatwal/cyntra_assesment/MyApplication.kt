package com.nikhilosatwal.cyntra_assesment

import android.app.Application
import com.nikhilosatwal.cyntra_assesment.di.AppComponent
import com.nikhilosatwal.cyntra_assesment.di.AppModule
import com.nikhilosatwal.cyntra_assesment.di.DaggerAppComponent

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}