package com.example.tbc_events.presentation.model

data class UserItem(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val img: String,
    var isSelected: Boolean
)
