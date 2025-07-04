package com.ubaya.a3pilar_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.a3pilar_project.R
import com.ubaya.a3pilar_project.model.Budget

class BudgetAdapter(
    val budgets: List<Budget>,
    val onClick: ((Budget) -> Unit)? = null  // <- opsional
) : RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    class BudgetViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_budget, parent, false)
        return BudgetViewHolder(view)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        val budget = budgets[position]
        with(holder.view) {
            findViewById<TextView>(R.id.textViewBudgetName).text = budget.title
            findViewById<TextView>(R.id.textViewUsedMax).text =
                "IDR ${budget.usedAmount} / IDR ${budget.maxAmount}"
            findViewById<TextView>(R.id.textViewBudgetLeft).text =
                "Budget left: IDR ${budget.maxAmount - budget.usedAmount}"

            val progress = (budget.usedAmount.toFloat() / budget.maxAmount * 100).toInt()
            findViewById<ProgressBar>(R.id.progressBudget).progress = progress

            // Kalau ada onClick, panggil
            setOnClickListener {
                onClick?.invoke(budget)
            }
        }
    }

    override fun getItemCount() = budgets.size
}

