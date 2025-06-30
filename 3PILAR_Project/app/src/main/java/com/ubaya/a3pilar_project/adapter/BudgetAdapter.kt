package com.ubaya.a3pilar_project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.a3pilar_project.databinding.ItemBudgetBinding
import com.ubaya.a3pilar_project.model.Budget

class BudgetAdapter(
    private val budgets: List<Budget>,
    private val onClick: (Budget) -> Unit
) : RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    class BudgetViewHolder(val binding: ItemBudgetBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBudgetBinding.inflate(inflater, parent, false)
        return BudgetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        val budget = budgets[position]
        holder.binding.tvTitle.text = budget.title
        holder.binding.tvCategory.text = budget.category
        holder.binding.tvAmount.text = "Rp${budget.maxAmount}"


        holder.itemView.setOnClickListener {
            onClick(budget)
        }
    }

    override fun getItemCount(): Int = budgets.size
}
