package com.example.tbc_events.presentation.state.home

import com.example.tbc_events.presentation.model.UserItem

data class HomeState(
    val isLoading: Boolean = false,
    val users: List<UserItem>? = null,
    val errorMessage: String? = null
)
