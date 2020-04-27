package com.anesabml.hunt.extension

import android.app.Activity
import com.anesabml.hunt.MyApp

val Activity.injector get() = (application as MyApp).appComponent
