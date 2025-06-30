package com.ubaya.a3pilar_project.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ubaya.a3pilar_project.data.AppDatabase
import com.ubaya.a3pilar_project.model.Budget
import com.ubaya.a3pilar_project.repository.BudgetRepository
import kotlinx.coroutines.launch

class BudgetViewModel(application: Application) : AndroidViewModel(application) {

    private val budgetDao = AppDatabase.getInstance(application).budgetDao()
    private val repository = BudgetRepository(budgetDao)

    val budgetList = MutableLiveData<List<Budget>>()

    fun addBudget(budget: Budget) {
        viewModelScope.launch {
            repository.addBudget(budget)
            loadBudgets()
        }
    }

    fun loadBudgets() {
        viewModelScope.launch {
            val budgets = repository.getAllBudgets()
            budgetList.postValue(budgets)
        }
    }

    fun updateBudget(budget: Budget) {
        viewModelScope.launch {
            val db = AppDatabase.getInstance(getApplication())
            db.budgetDao().update(budget)
            loadBudgets()
        }
    }
}
