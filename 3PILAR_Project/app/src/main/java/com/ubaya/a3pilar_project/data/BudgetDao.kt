package com.ubaya.a3pilar_project.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ubaya.a3pilar_project.model.Budget

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budget")
    fun getAllBudgets(): LiveData<List<Budget>>

    @Query("SELECT * FROM budget")
    suspend fun getAll(): List<Budget>

    @Insert
    suspend fun insert(budget: Budget):Long

    @Update
    suspend fun update(budget: Budget)
}

