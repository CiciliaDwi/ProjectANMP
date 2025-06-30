package com.ubaya.a3pilar_project.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "transaction",
    foreignKeys = [
        ForeignKey(
            entity = Budget::class,
            parentColumns = ["id"],
            childColumns = ["budgetId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExpenseTransaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val budgetId: Int,
    val amount: Int,
    val description: String,
    val date: String
)
