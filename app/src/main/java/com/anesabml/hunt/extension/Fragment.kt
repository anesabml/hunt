package com.anesabml.hunt.extension

import androidx.fragment.app.Fragment
import com.anesabml.hunt.MyApp

val Fragment.injector get() = (requireContext().applicationContext as MyApp).appComponent
