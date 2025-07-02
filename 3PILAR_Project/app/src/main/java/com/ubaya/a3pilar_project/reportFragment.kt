package com.ubaya.a3pilar_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.a3pilar_project.adapter.BudgetAdapter
import com.ubaya.a3pilar_project.viewmodel.BudgetViewModel

class reportFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var textViewTotalExpense: TextView
    private lateinit var viewModel: BudgetViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_report, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewBudget)
        textViewTotalExpense = view.findViewById(R.id.textViewTotalExpense)
        viewModel = ViewModelProvider(this)[BudgetViewModel::class.java]


        viewModel.budgetList.observe(viewLifecycleOwner) { budgets ->
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = BudgetAdapter(budgets)

            val totalExpense = budgets.sumOf { it.usedAmount }
            val totalBudget = budgets.sumOf { it.maxAmount }
            textViewTotalExpense.text = "Total: IDR $totalExpense / IDR $totalBudget"
        }
        return view
    }
}


