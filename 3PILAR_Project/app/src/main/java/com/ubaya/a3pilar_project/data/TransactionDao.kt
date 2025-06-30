package com.ubaya.a3pilar_project.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ubaya.a3pilar_project.model.ExpenseTransaction


@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: ExpenseTransaction)

    @Query("SELECT * FROM `transaction` ORDER BY date DESC")
    suspend fun getAll(): List<ExpenseTransaction>

    @Query("SELECT SUM(amount) FROM `transaction` WHERE budgetId = :budgetId")
    suspend fun getTotalByBudget(budgetId: Int): Int?
}


