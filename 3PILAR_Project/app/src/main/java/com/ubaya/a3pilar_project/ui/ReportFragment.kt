package com.ubaya.a3pilar_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.a3pilar_project.R
import com.ubaya.a3pilar_project.adapter.ReportAdapter
import com.ubaya.a3pilar_project.data.AppDatabase
import kotlinx.coroutines.launch

class ReportFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var textViewTotalExpense: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_report, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewBudget)
        textViewTotalExpense = view.findViewById(R.id.textViewTotalExpense)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadReportData()

        return view
    }

    private fun loadReportData() {
        lifecycleScope.launch {
            val db = AppDatabase.getInstance(requireContext())
            val budgets = db.budgetDao().getAll()

            // Hitung total usedAmount per budget
            val budgetsWithUsed = budgets.map { budget ->
                val totalUsed = db.transactionDao().getTotalByBudget(budget.id) ?: 0
                budget.usedAmount = totalUsed
                budget
            }

            recyclerView.adapter = ReportAdapter(budgetsWithUsed)

            // Hitung total
            val totalExpense = budgetsWithUsed.sumOf { it.usedAmount }
            val totalBudget = budgetsWithUsed.sumOf { it.maxAmount }
            textViewTotalExpense.text = "Total: Rp$totalExpense / Rp$totalBudget"
        }
    }
}
