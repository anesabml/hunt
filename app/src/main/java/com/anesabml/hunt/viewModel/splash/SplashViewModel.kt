package com.anesabml.hunt.viewModel.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anesabml.hunt.data.repository.AuthenticationRepository
import com.anesabml.hunt.model.User
import com.anesabml.lib.extension.asLiveData
import com.anesabml.lib.network.Result
import com.anesabml.lib.viewModel.AssistedSavedStateViewModelFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory :
        AssistedSavedStateViewModelFactory<SplashViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): SplashViewModel
    }

    private val _userResult: MutableLiveData<Result<User>> = MutableLiveData()
    val userResult = _userResult.asLiveData()

    fun getCurrentViewer() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = authenticationRepository.getCurrentUser()
            _userResult.postValue(result)
        }
    }
}
