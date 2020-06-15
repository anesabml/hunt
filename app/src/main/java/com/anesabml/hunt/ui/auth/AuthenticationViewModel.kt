package com.anesabml.hunt.ui.auth

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anesabml.hunt.data.repository.AuthenticationRepository
import com.anesabml.hunt.model.Token
import com.anesabml.hunt.utils.Constant
import com.anesabml.lib.utils.DefaultDispatcherProvider
import com.anesabml.lib.utils.DispatcherProvider
import com.anesabml.lib.extension.asLiveData
import com.anesabml.lib.network.Result
import kotlinx.coroutines.launch

class AuthenticationViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val authenticationRepository: AuthenticationRepository,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

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
