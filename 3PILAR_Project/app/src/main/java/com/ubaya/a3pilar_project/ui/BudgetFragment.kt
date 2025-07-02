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
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.a3pilar_project.R
import com.ubaya.a3pilar_project.databinding.FragmentBudgetBinding
import com.ubaya.a3pilar_project.model.Budget
import com.ubaya.a3pilar_project.viewmodel.BudgetViewModel

class BudgetFragment : Fragment() {

    private lateinit var binding: FragmentBudgetBinding
    private lateinit var viewModel: BudgetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgetBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[BudgetViewModel::class.java]

        // Tampilkan list budgeting
        binding.rvBudget.layoutManager = LinearLayoutManager(requireContext())
        viewModel.budgetList.observe(viewLifecycleOwner) {
            binding.rvBudget.adapter = BudgetAdapter(it) { budget ->
                showEditDialog(budget)
            }
        }

        // Tombol FAB untuk tambah budgeting
        binding.fabAddBudget.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_budget, null)

            val etTitle = dialogView.findViewById<EditText>(R.id.etDialogTitle)
            val etAmount = dialogView.findViewById<EditText>(R.id.etDialogAmount)
            val etCategory = dialogView.findViewById<EditText>(R.id.etDialogCategory)

            AlertDialog.Builder(requireContext())
                .setTitle("Tambah Budget")
                .setView(dialogView)
                .setPositiveButton("Simpan") { _, _ ->
                    val amountString = etAmount.text.toString()
                    val amount = amountString.toInt()

                    val budget = Budget(
                        id = 0,
                        title = etTitle.text.toString(),
                        maxAmount = amount,
                        usedAmount = 0,
                        category = etCategory.text.toString()
                    )

                    viewModel.addBudget(budget)
                }
                .setNegativeButton("Batal", null)
                .show()

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
                val newAmount = etAmount.text.toString()
                val newCategory = etCategory.text.toString()

                if (newTitle.isNotEmpty() && newAmount.isNotEmpty() && newCategory.isNotEmpty()) {
                    val updated = budget.copy(
                        title = newTitle,
                        maxAmount = newAmount.toInt(),
                        category = newCategory
                    )
                    viewModel.updateBudget(updated)
                    Toast.makeText(requireContext(), "Budget diperbarui", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .create()
            .show()
    }
}
