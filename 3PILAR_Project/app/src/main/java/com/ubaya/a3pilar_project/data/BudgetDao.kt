package com.ubaya.a3pilar_project.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ubaya.a3pilar_project.model.Budget

@Dao
interface BudgetDao {
    @Insert
    suspend fun insert(budget: Budget): Long

    @Update
    suspend fun update(budget: Budget)

    @Query("SELECT * FROM budget")
    suspend fun getAll(): List<Budget>

}
