package com.anesabml.hunt.viewModel.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anesabml.hunt.data.repository.AuthenticationRepository
import com.anesabml.hunt.model.Token
import com.anesabml.hunt.utils.Constant
import com.anesabml.hunt.utils.DefaultDispatcherProvider
import com.anesabml.hunt.utils.DispatcherProvider
import com.anesabml.lib.extension.asLiveData
import com.anesabml.lib.network.Result
import com.anesabml.lib.viewModel.AssistedSavedStateViewModelFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class AuthenticationViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val authenticationRepository: AuthenticationRepository,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory :
        AssistedSavedStateViewModelFactory<AuthenticationViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): AuthenticationViewModel
    }

    private val _result: MutableLiveData<Result<Token>> = MutableLiveData()
    val result = _result.asLiveData()

    fun handleAuthorizationCode(code: String) {
        viewModelScope.launch(dispatcherProvider.io()) {
            val result = authenticationRepository.retrieveAccessToken(
                Constant.API_KEY,
                Constant.API_SECRET,
                code
            )

            _result.postValue(result)
        }
    }
}
