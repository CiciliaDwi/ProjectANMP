package com.ubaya.a3pilar_project.ui

import com.ubaya.a3pilar_project.adapter.BudgetAdapter
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import com.ubaya.a3pilar_project.R
import com.ubaya.a3pilar_project.data.AppDatabase
import com.ubaya.a3pilar_project.databinding.FragmentBudgetBinding
import com.ubaya.a3pilar_project.model.Budget
import com.ubaya.a3pilar_project.viewmodel.BudgetViewModel
import kotlinx.coroutines.launch

class BudgetFragment : Fragment() {

    private lateinit var binding: FragmentBudgetBinding
    private lateinit var viewModel: BudgetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgetBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[BudgetViewModel::class.java]

        // ✅ Tampilkan list budgeting
        binding.rvBudget.layoutManager = LinearLayoutManager(requireContext())
        viewModel.budgetList.observe(viewLifecycleOwner) { budgets ->
            binding.rvBudget.adapter = BudgetAdapter(budgets) { budget ->
                showEditDialog(budget) // ✅ Ganti dari navigate ke form dialog edit
            }
        }

        // ✅ FAB untuk tambah budgeting (pindah ke halaman AddBudgetFragment)
        binding.fabAddBudget.setOnClickListener {
            findNavController().navigate(R.id.action_budgetFragment_to_addBudgetFragment)
        }

        return binding.root
    }

    private fun showEditDialog(budget: Budget) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_budget, null)

        val etTitle = dialogView.findViewById<EditText>(R.id.etDialogTitle)
        val etAmount = dialogView.findViewById<EditText>(R.id.etDialogAmount)
        val etCategory = dialogView.findViewById<EditText>(R.id.etDialogCategory)

        etTitle.setText(budget.title)
        etAmount.setText(budget.maxAmount.toString())
        etCategory.setText(budget.category)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Budget")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val newTitle = etTitle.text.toString()
                val newAmountStr = etAmount.text.toString()
                val newCategory = etCategory.text.toString()

                if (newTitle.isNotEmpty() && newAmountStr.isNotEmpty() && newCategory.isNotEmpty()) {
                    val newAmount = newAmountStr.toInt()

                    // ✅ Validasi: nominal tidak boleh < total expense
                    viewLifecycleOwner.lifecycleScope.launch {
                        val db = AppDatabase.getInstance(requireContext())
                        val totalExpense = db.transactionDao().getTotalByBudget(budget.id) ?: 0

                        if (newAmount < totalExpense) {
                            Toast.makeText(
                                requireContext(),
                                "Nominal tidak boleh di bawah total pengeluaran: Rp$totalExpense",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val updated = budget.copy(
                                title = newTitle,
                                maxAmount = newAmount,
                                category = newCategory
                            )
                            viewModel.updateBudget(updated)
                            Toast.makeText(requireContext(), "Budget diperbarui", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .create()
            .show()
    }
}
