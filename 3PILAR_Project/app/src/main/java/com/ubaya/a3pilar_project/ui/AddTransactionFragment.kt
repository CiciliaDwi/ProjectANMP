package com.ubaya.a3pilar_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ubaya.a3pilar_project.R
import com.ubaya.a3pilar_project.data.AppDatabase
import com.ubaya.a3pilar_project.databinding.FragmentAddTransactionBinding
import com.ubaya.a3pilar_project.model.ExpenseTransaction
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddTransactionFragment : Fragment() {
    private lateinit var binding: FragmentAddTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTransactionBinding.inflate(inflater, container, false)

        // Setup Spinner and Progress Bar
        val spinner = binding.spinnerBudget
        val tvSisa = binding.tvBudgetRemaining
        val progressBar = binding.progressBudget
        val etAmount = binding.etAmount
        val etDescription = binding.etNotes

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

            binding.btnSave.setOnClickListener {
                val amountString = etAmount.text.toString()
                if (amountString.isEmpty()) {
                    Toast.makeText(requireContext(), "Nominal tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val amount = amountString.toIntOrNull()
                if (amount == null || amount <= 0) {
                    Toast.makeText(requireContext(), "Nominal harus lebih dari 0", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val trx = ExpenseTransaction(
                    budgetId = selectedBudget!!.id,
                    amount = amount,
                    description = etDescription.text.toString(),
                    date = LocalDate.now().toString()
                )

                lifecycleScope.launch {
                    db.transactionDao().insert(trx)
                    requireActivity().onBackPressed()
                }
            }
        }

        return binding.root
    }
}
