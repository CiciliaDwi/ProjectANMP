package com.ubaya.a3pilar_project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.a3pilar_project.databinding.ItemReportBinding
import com.ubaya.a3pilar_project.model.Budget

class ReportAdapter(private val budgets: List<Budget>) :
    RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    class ReportViewHolder(val binding: ItemReportBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReportBinding.inflate(inflater, parent, false)
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val item = budgets[position]
        val sisa = item.maxAmount - item.usedAmount
        val percent = if (item.maxAmount == 0) 0 else (item.usedAmount * 100 / item.maxAmount).coerceAtMost(100)

        with(holder.binding) {
            tvBudgetName.text = "Budget: ${item.title}"
            tvUsed.text = "Terpakai: Rp${item.usedAmount}"
            tvMax.text = "Total Budget: Rp${item.maxAmount}"
            tvRemaining.text = "Sisa: Rp$sisa"
            progressBar.progress = percent
        }
    }

    override fun getItemCount(): Int = budgets.size
}
