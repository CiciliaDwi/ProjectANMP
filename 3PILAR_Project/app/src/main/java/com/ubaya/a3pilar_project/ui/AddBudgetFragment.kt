package com.ubaya.a3pilar_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ubaya.a3pilar_project.databinding.FragmentAddBudgetBinding
import com.ubaya.a3pilar_project.model.Budget
import com.ubaya.a3pilar_project.viewmodel.BudgetViewModel

class AddBudgetFragment : Fragment() {
    private lateinit var binding: FragmentAddBudgetBinding
    private lateinit var viewModel: BudgetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBudgetBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[BudgetViewModel::class.java]

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val amountStr = binding.etAmount.text.toString()
            val category = binding.etCategory.text.toString()

            if (title.isBlank() || amountStr.isBlank() || category.isBlank()) {
                Toast.makeText(requireContext(), "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountStr.toInt()

            val newBudget = Budget(0, title, amount, category).apply {
                usedAmount = 0
            }

            viewModel.addBudget(newBudget)
            Toast.makeText(requireContext(), "Budget ditambahkan", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        return binding.root
    }
}
