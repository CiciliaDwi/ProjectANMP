//package com.ubaya.a3pilar_project.viewmodel
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.viewModelScope
//import com.ubaya.a3pilar_project.data.AppDatabase
//import com.ubaya.a3pilar_project.model.Budget
//import com.ubaya.a3pilar_project.repository.BudgetRepository
//import kotlinx.coroutines.launch
//
//class BudgetViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val budgetDao = AppDatabase.getInstance(application).budgetDao()
//    private val repository = BudgetRepository(budgetDao)
//
//
//    val budgetList: LiveData<List<Budget>>
//        get() = budgetDao.getAllBudgets()
//
//
//
//    fun addBudget(budget: Budget) {
//        viewModelScope.launch { budgetDao.insertBudget(budget) }
//    }
//
//    fun updateBudget(budget: Budget) {
//        viewModelScope.launch { budgetDao.updateBudget(budget) }
//    }
//
//    fun loadBudgets() {
//        viewModelScope.launch {
//            val budgets = repository.getAllBudgets()
//            budgetList.postValue(budgets)
//        }
//    }
//}
package com.ubaya.a3pilar_project.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ubaya.a3pilar_project.data.AppDatabase
import com.ubaya.a3pilar_project.model.Budget
import com.ubaya.a3pilar_project.repository.BudgetRepository
import kotlinx.coroutines.launch

class BudgetViewModel(application: Application) : AndroidViewModel(application) {

    private val budgetDao = AppDatabase.getInstance(application).budgetDao()
    private val repository = BudgetRepository(budgetDao)

    val budgetList: LiveData<List<Budget>> = budgetDao.getAllBudgets()

    fun addBudget(budget: Budget) {
        viewModelScope.launch {
            repository.addBudget(budget)
        }
    }

    fun updateBudget(budget: Budget) {
        viewModelScope.launch {
            repository.updateBudget(budget)
        }
    }
}
