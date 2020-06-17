package com.anesabml.hunt.utils

import android.app.Application
import android.content.Context
import javax.inject.Inject

class SharedPref @Inject constructor(application: Application) {

    private val sharedPreference = application.getSharedPreferences("sh_pr", Context.MODE_PRIVATE)

    var token: String = ""
        set(value) {
            sharedPreference.edit().putString("token", value).apply()
            field = value
        }
        get() = sharedPreference.getString("token", "")!!
}
