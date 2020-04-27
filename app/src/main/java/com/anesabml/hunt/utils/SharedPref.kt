package com.anesabml.hunt.utils

import android.content.Context
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPref @Inject constructor(context: Context) {

    private val sharedPreference = context.getSharedPreferences("sh_pr", Context.MODE_PRIVATE)

    var token: String = ""
        set(value) {
            sharedPreference.edit().putString("token", value).apply()
            field = value
        }
        get() = sharedPreference.getString("token", "")!!

    var shouldSetupPodWorker: Boolean = true
        set(value) {
            sharedPreference.edit {
                putBoolean("should_setup_pod_worker", value)
            }
            field = value
        }
        get() = sharedPreference.getBoolean("should_setup_pod_worker", true)
}
