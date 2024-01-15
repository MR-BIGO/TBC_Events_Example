package com.example.tbc_events.presentation.screen.home

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_events.data.common.Resource
import com.example.tbc_events.domain.use_case.DeleteUsersUseCase
import com.example.tbc_events.domain.use_case.GetUsersUseCase
import com.example.tbc_events.presentation.event.home.ItemEvent
import com.example.tbc_events.presentation.state.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val deleteUsersUseCase: DeleteUsersUseCase
) :
    ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: SharedFlow<HomeState> = _homeState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<NavigationEvent>()
    val uiEvent: SharedFlow<NavigationEvent> get() = _uiEvent


    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            getUsersUseCase.invoke().collect {
                when (it) {
                    is Resource.Success -> {
                        _homeState.update { currentState ->
                            currentState.copy(
                                users = it.data
                            )
                        }
                    }

                    is Resource.Error -> updateError(it.error)

                    is Resource.Loading -> _homeState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: ItemEvent) {
        viewModelScope.launch {
            when (event) {
                is ItemEvent.ItemClicked -> navigateToDetails(event.id)
                is ItemEvent.ItemsDelete -> handleItemsDelete()
                is ItemEvent.ItemOnLongClicked -> handleLongClick(event.id)
                is ItemEvent.ResetErrorMessage -> updateError(null)
            }
        }
    }

    //without Fake delete api. will delete items. uncomment this if you want to delete items.
    //Also, go in the deleteUsersUseCase
//    private fun handleItemsDelete() {
//        viewModelScope.launch {
//            deleteUsersUseCase.invoke(_homeState.value.users!!).collect {
//                if (it is Resource.Success) {
//                    _homeState.update { currentState -> currentState.copy(users = it.data) }
//                }
//            }
//        }
//    }

    //fake api. if you want to actually delete, comment this and uncomment the other handleItemsDelete function.
    //Also, go in the deleteUsersUseCase
    private fun handleItemsDelete() {
        viewModelScope.launch {
            try {
                deleteUsersUseCase.invoke(_homeState.value.users!!)
            } catch (e: Throwable) {
                d("checking error", "${e.message}")
                _homeState.update { currentState -> currentState.copy(errorMessage = "This is Fake Api. Doesn't work") }
            }
        }
    }

    private fun navigateToDetails(id: Int) {
        viewModelScope.launch {
            _uiEvent.emit(NavigationEvent.NavigateToDetails(id))
        }
    }

    private fun handleLongClick(id: Int) {
        for (i in _homeState.value.users!!) {
            if (i.id == id) i.isSelected = !i.isSelected
        }
    }

    private fun updateError(message: String?) {
        _homeState.update { currentState -> currentState.copy(errorMessage = message) }
    }

    sealed class NavigationEvent {
        data class NavigateToDetails(val id: Int) : NavigationEvent()
    }
}