package com.example.tbc_events.presentation.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_events.data.common.Resource
import com.example.tbc_events.domain.use_case.GetUserUseCase
import com.example.tbc_events.presentation.model.UserItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsFragmentViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) : ViewModel() {

    private val _itemFlow = MutableStateFlow<Resource<UserItem>?>(null)
    val itemFLow: StateFlow<Resource<UserItem>?> = _itemFlow.asStateFlow()

    fun loadDetails(id: Int){
        viewModelScope.launch {
            getUserUseCase.invoke(id).collect{
                when(it){
                    is Resource.Success -> _itemFlow.value = Resource.Success(it.data)
                    is Resource.Error -> _itemFlow.value = Resource.Error(it.error)
                    is Resource.Loading -> _itemFlow.value = Resource.Loading(it.loading)
                }
            }
        }
    }
}