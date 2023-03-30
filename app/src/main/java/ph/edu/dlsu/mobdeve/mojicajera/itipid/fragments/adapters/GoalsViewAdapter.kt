package ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Goals
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions

class GoalsViewAdapter (private val goalsList: ArrayList<Goals>)
    : RecyclerView.Adapter<GoalsViewAdapter.GoalsViewHolder>() {
    var onItemClick: ((Transactions) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalsViewAdapter.GoalsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_goals_row,parent,false)
        return GoalsViewAdapter.GoalsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecyclerViewAdapter.TransactionViewHolder,
        position: Int
    ) {
        val goals = goalsList[position]
        holder.label.text = goals.label
        holder.amount.text = goals.amount.toString()
        holder.date.text = goals.date
        holder.type.text = goals.description


    }

    override fun getItemCount(): Int {
        return goalsList.size
    }

    class GoalsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val label: TextView = itemView.findViewById(R.id.tvLabel)
        val amount: TextView = itemView.findViewById(R.id.transactionAmount)
        val date: TextView = itemView.findViewById(R.id.tvDate)
        val type: TextView = itemView.findViewById(R.id.type)


    }
}