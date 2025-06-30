package com.ubaya.a3pilar_project.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ubaya.a3pilar_project.data.AppDatabase
import com.ubaya.a3pilar_project.model.ExpenseTransaction
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    val transactionList = MutableLiveData<List<ExpenseTransaction>>()

    fun loadTransactions() {
        viewModelScope.launch {
            val db = AppDatabase.getInstance(getApplication())

            // 1. Tambahkan dummy budget jika belum ada
            val budgets = db.budgetDao().getAll()
            val budgetId = if (budgets.isEmpty()) {
                val dummyBudget = com.ubaya.a3pilar_project.model.Budget(
                    title = "Test Budget",
                    maxAmount = 100000,
                    category = "Test"
                )
                val id = db.budgetDao().insert(dummyBudget).toInt()
                id
            } else {
                budgets[0].id // ambil budget pertama yg ada
            }

            // 2. Tambahkan dummy transaction jika belum ada
            if (db.transactionDao().getAll().isEmpty()) {
                db.transactionDao().insert(
                    com.ubaya.a3pilar_project.model.ExpenseTransaction(
                        budgetId = budgetId,
                        amount = 15000,
                        description = "Dummy Test Transaction",
                        date = "2025-06-30"
                    )
                )
            }

            // 3. Tampilkan
            transactionList.postValue(db.transactionDao().getAll())
        }
    }


    fun addTransaction(transaction: ExpenseTransaction) {
        viewModelScope.launch {
            val db = AppDatabase.getInstance(getApplication())
            db.transactionDao().insert(transaction)
            loadTransactions()
        }
    }
}
