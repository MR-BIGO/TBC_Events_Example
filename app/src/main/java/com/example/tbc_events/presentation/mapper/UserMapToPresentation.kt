package com.example.tbc_events.presentation.mapper

import com.example.tbc_events.domain.model.User
import com.example.tbc_events.presentation.model.UserItem

fun User.toPresentation(): UserItem {
    return UserItem(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        img = this.img,
        isSelected = false
    )
}