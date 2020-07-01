package com.anesabml.producthunt.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anesabml.producthunt.data.repository.AuthenticationRepository
import com.anesabml.producthunt.model.User
import com.anesabml.lib.extension.asLiveData
import com.anesabml.lib.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _user: MutableLiveData<Result<User>> = MutableLiveData()
    val user = _user.asLiveData()

    fun getCurrentViewer() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = authenticationRepository.getCurrentUser()
            _user.postValue(result)
        }
    }
}
