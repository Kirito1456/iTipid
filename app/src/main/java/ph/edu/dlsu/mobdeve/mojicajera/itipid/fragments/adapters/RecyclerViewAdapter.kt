package ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions


class RecyclerViewAdapter (private val transactionList: ArrayList<Transactions>)
    : RecyclerView.Adapter<RecyclerViewAdapter.TransactionViewHolder>()  {

    var onItemClick : ((Transactions) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_daily_row,parent,false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactionList[position]
        holder.label.text = transaction.label
        holder.amount.text = transaction.amount.toString()
        holder.date.text = transaction.date


        // Clickable
//        holder.itemView.setOnClickListener(){
//            onItemClick?.invoke(transaction)
//        }

    }
    override fun getItemCount(): Int {
        return transactionList.size
    }

    class TransactionViewHolder(itemView: View)  : RecyclerView.ViewHolder(itemView){
        val label: TextView = itemView.findViewById(R.id.tvLabel)
        val amount: TextView = itemView.findViewById(R.id.transactionAmount)
        val date: TextView = itemView.findViewById(R.id.tvDate)
    }
}