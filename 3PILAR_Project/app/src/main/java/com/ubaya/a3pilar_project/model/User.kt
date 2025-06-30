package com.ubaya.a3pilar_project.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class User(
    @PrimaryKey val username: String,
    val firstName: String,
    val lastName: String,
    val password: String
)