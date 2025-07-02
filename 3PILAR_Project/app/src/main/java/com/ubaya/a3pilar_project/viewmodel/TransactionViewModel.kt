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
            val budgets = db.budgetDao().getAll()

            // 1. Tambahkan dummy budget jika belum ada
            var budgetId = 0

            if (budgets.isEmpty()) {
                val dummyBudget = com.ubaya.a3pilar_project.model.Budget(
                    title = "Budget",
                    maxAmount = 100000,
                    usedAmount = 50000,
                    category = "Kategori"
                )
                val insertedId = db.budgetDao().insert(dummyBudget)
                budgetId = insertedId.toInt() // ⏪ update var
            } else {
                budgetId = budgets[0].id
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
