package com.ubaya.a3pilar_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.a3pilar_project.R
import com.ubaya.a3pilar_project.adapter.TransactionAdapter
import com.ubaya.a3pilar_project.data.AppDatabase
import com.ubaya.a3pilar_project.databinding.FragmentTransactionBinding
import com.ubaya.a3pilar_project.model.ExpenseTransaction
import kotlinx.coroutines.launch

class TransactionFragment : Fragment() {

    private lateinit var binding: FragmentTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)

        // Setup RecyclerView
        binding.rvTransaction.layoutManager = LinearLayoutManager(requireContext())
        loadTransactionList()

        // FAB ➜ navigasi ke halaman baru (bukan dialog lagi)
        binding.fabAddTransaction.setOnClickListener {
            findNavController().navigate(R.id.action_transactionFragment_to_addTransactionFragment)
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
        val message = """
            Tanggal: ${trx.date}
            Nominal: Rp${trx.amount}
            Keterangan: ${trx.description}
            Budget: $budgetName
        """.trimIndent()

        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Detail Transaksi")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
