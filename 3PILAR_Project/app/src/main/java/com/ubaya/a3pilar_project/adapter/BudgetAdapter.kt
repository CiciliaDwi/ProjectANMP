package com.ubaya.a3pilar_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.a3pilar_project.R
import com.ubaya.a3pilar_project.databinding.ItemBudgetBinding
import com.ubaya.a3pilar_project.model.Budget

class BudgetAdapter(
    private val budgets: List<Budget>,
    private val onClick: (Budget) -> Unit
) : RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    inner class BudgetViewHolder(val binding: ItemBudgetBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBudgetBinding.inflate(inflater, parent, false)
        return BudgetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        val budget = budgets[position]
        with(holder.binding) {
            tvTitle.text = budget.title
            tvMaxAmount.text = "IDR ${budget.maxAmount}"
            root.setOnClickListener { onClick(budget) }
        }
    }

    override fun getItemCount(): Int = budgets.size
}


