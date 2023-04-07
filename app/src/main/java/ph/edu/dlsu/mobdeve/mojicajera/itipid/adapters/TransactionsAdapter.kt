package ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions

class TransactionsAdapter(private val transactions: List<Transactions>) :
    RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define the views for each item in the RecyclerView here
        // For example:
        val labelTextView: TextView = itemView.findViewById(R.id.eventTitle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.events_row, parent, false)
        return TransactionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        // Bind the data to the views here
        // For example:
        holder.labelTextView.text = transactions[position].label

    }

    override fun getItemCount() = transactions.size
}
