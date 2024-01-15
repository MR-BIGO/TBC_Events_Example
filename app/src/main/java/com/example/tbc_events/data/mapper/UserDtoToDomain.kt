package com.example.tbc_events.data.mapper

import com.example.tbc_events.data.dto.UserDto
import com.example.tbc_events.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        img = this.img
    )
}