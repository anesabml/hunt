package com.anesabml.producthunt.ui.auth

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anesabml.producthunt.data.repository.AuthenticationRepository
import com.anesabml.producthunt.model.Token
import com.anesabml.producthunt.utils.Constant
import com.anesabml.lib.extension.asLiveData
import com.anesabml.lib.network.Result
import kotlinx.coroutines.launch

class AuthenticationViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _token: MutableLiveData<Result<Token>> = MutableLiveData()
    val token = _token.asLiveData()

    fun handleAuthorizationCode(code: String) {
        viewModelScope.launch {
            val result = authenticationRepository.retrieveAccessToken(
                Constant.API_KEY,
                Constant.API_SECRET,
                code
            )
            _token.postValue(result)
        }
    }
}
