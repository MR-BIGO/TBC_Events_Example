package com.example.tbc_events.presentation.event.home

sealed class ItemEvent {
    data class ItemClicked(val id: Int) : ItemEvent()
    data class ItemOnLongClicked(val id: Int) : ItemEvent()
    data object ItemsDelete : ItemEvent()
    data object ResetErrorMessage : ItemEvent()
}