package com.ubaya.a3pilar_project.repository

import androidx.lifecycle.LiveData
import com.ubaya.a3pilar_project.data.BudgetDao
import com.ubaya.a3pilar_project.model.Budget

//class BudgetRepository(private val budgetDao: BudgetDao) {
//
//    suspend fun addBudget(budget: Budget) = budgetDao.insert(budget)
//
//    suspend fun getAllBudgets(): List<Budget> = budgetDao.getAll()
//}

class BudgetRepository(private val budgetDao: BudgetDao) {

    suspend fun addBudget(budget: Budget) {
        budgetDao.insert(budget)
    }

    suspend fun updateBudget(budget: Budget) {
        budgetDao.update(budget)
    }

    fun getAllBudgets(): LiveData<List<Budget>> = budgetDao.getAllBudgets()
}

