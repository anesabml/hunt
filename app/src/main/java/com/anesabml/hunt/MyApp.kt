package com.anesabml.hunt

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.anesabml.hunt.di.AppComponent
import com.anesabml.hunt.di.DaggerAppComponent
import com.anesabml.hunt.utils.Constant
import com.anesabml.hunt.utils.SharedPref
import timber.log.Timber
import timber.log.Timber.DebugTree

class MyApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = SharedPref(applicationContext)
        val token =
            if (sharedPreferences.token.isNotBlank()) sharedPreferences.token else Constant.TOKEN
        appComponent = DaggerAppComponent.factory().create(this, token)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        // Init WorkManager
        WorkManager.initialize(
            this,
            Configuration.Builder().setWorkerFactory(appComponent.workerFactory()).build()
        )
    }
}
