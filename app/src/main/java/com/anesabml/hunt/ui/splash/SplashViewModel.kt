package com.anesabml.hunt.ui.splash

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anesabml.hunt.data.repository.AuthenticationRepository
import com.anesabml.hunt.model.User
import com.anesabml.lib.extension.asLiveData
import com.anesabml.lib.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _userResult: MutableLiveData<Result<User>> = MutableLiveData()
    val userResult = _userResult.asLiveData()

    fun getCurrentViewer() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = authenticationRepository.getCurrentUser()
            _userResult.postValue(result)
        }
    }
}
