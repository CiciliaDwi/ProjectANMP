package com.ubaya.a3pilar_project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.a3pilar_project.databinding.ItemTransactionBinding
import com.ubaya.a3pilar_project.model.ExpenseTransaction


class TransactionAdapter(
    private val transactions: List<ExpenseTransaction>,
    private val budgetMap: Map<Int, String>,
    private val onClick: (ExpenseTransaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransactionBinding.inflate(inflater, parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val trx = transactions[position]
        holder.binding.tvDate.text = trx.date
        holder.binding.tvAmount.text = "Rp${trx.amount}"

        // Ambil nama budget dari Map berdasarkan ID
        val budgetName = budgetMap[trx.budgetId] ?: "Tidak diketahui"
        holder.binding.chipBudget.text = budgetName

        // Klik nominal untuk tampilkan dialog
        holder.binding.tvAmount.setOnClickListener {
            onClick(trx)
        }
    }

    override fun getItemCount(): Int = transactions.size
}
