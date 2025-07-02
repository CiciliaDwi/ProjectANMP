package com.ubaya.a3pilar_project.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.a3pilar_project.R
import com.ubaya.a3pilar_project.adapter.TransactionAdapter
import com.ubaya.a3pilar_project.data.AppDatabase
import com.ubaya.a3pilar_project.databinding.FragmentTransactionBinding
import com.ubaya.a3pilar_project.model.ExpenseTransaction
import kotlinx.coroutines.launch
import java.time.LocalDate

class TransactionFragment : Fragment() {

    private lateinit var binding: FragmentTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)

        // Setup RecyclerView
        binding.rvTransaction.layoutManager = LinearLayoutManager(requireContext())

        loadTransactionList()

        // FAB Tambah Transaksi
        binding.fabAddTransaction.setOnClickListener {
            showAddTransactionDialog()
        }

        return binding.root
    }

    private fun loadTransactionList() {
        lifecycleScope.launch {
            val db = AppDatabase.getInstance(requireContext())
            val budgets = db.budgetDao().getAll()
            val transactions = db.transactionDao().getAll()
            val budgetMap = budgets.associateBy({ it.id }, { it.title })

            binding.rvTransaction.adapter = TransactionAdapter(transactions, budgetMap) {
                showDetailDialog(it, budgetMap[it.budgetId] ?: "Tidak diketahui")
            }
        }
    }

    private fun showDetailDialog(trx: ExpenseTransaction, budgetName: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Detail Transaksi")
            .setMessage(
                "Tanggal: ${trx.date}\n" +
                        "Nominal: Rp${trx.amount}\n" +
                        "Keterangan: ${trx.description}\n" +
                        "Budget: $budgetName"
            )
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showAddTransactionDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_transaction, null)
        val etAmount = dialogView.findViewById<EditText>(R.id.etAmount)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)
        val spinner = dialogView.findViewById<Spinner>(R.id.spinnerBudget)
        val tvSisa = dialogView.findViewById<TextView>(R.id.tvBudgetRemaining)
        val progressBar = dialogView.findViewById<ProgressBar>(R.id.progressBudget)

        lifecycleScope.launch {
            val db = AppDatabase.getInstance(requireContext())
            val budgets = db.budgetDao().getAll()
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, budgets.map { it.title })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            var selectedBudget = budgets.firstOrNull()
            var totalExpense = 0

            fun updateSisaBudget() {
                lifecycleScope.launch {
                    if (selectedBudget != null) {
                        totalExpense = db.transactionDao().getTotalByBudget(selectedBudget!!.id) ?: 0
                        val sisa = selectedBudget!!.maxAmount - totalExpense
                        tvSisa.text = "Sisa Budget: Rp$sisa"
                        progressBar.progress = (totalExpense * 100 / selectedBudget!!.maxAmount).coerceAtMost(100)
                    }
                }
            }

            updateSisaBudget()

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    selectedBudget = budgets[position]
                    updateSisaBudget()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

            AlertDialog.Builder(requireContext())
                .setTitle("Tambah Transaksi")
                .setView(dialogView)
                .setPositiveButton("Simpan") { _, _ ->
                    val amountString = etAmount.text.toString()
                    val amount = amountString.toInt()

                    val trx = ExpenseTransaction(
                        budgetId = selectedBudget!!.id,
                        amount = amount,
                        description = etDescription.text.toString(),
                        date = LocalDate.now().toString()
                    )

                    lifecycleScope.launch {
                        db.transactionDao().insert(trx)
                        loadTransactionList()
                    }
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }
}
