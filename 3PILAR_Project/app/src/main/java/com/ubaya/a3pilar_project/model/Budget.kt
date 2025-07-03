package com.ubaya.a3pilar_project.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Ignore

@Entity(tableName = "budget")
data class Budget(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val maxAmount: Int,
    val category: String
) {
    @Ignore
    var usedAmount: Int = 0
}

