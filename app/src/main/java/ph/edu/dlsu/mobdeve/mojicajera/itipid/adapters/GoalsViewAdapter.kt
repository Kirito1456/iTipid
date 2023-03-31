package ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.mojicajera.itipid.EditGoals
import ph.edu.dlsu.mobdeve.mojicajera.itipid.EditTransaction
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Goals
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions

class GoalsViewAdapter (private val goalsList: ArrayList<Goals>)
    : RecyclerView.Adapter<GoalsViewAdapter.GoalsViewHolder>() {
    var onItemClick: ((Transactions) -> Unit)? = null

    companion object {
        const val labelKey : String = "LABEL_KEY"
        const val amountKey : String = "AMOUNT_KEY"
        const val positionKey: String = "POSITION_KEY"
        const val startingKey : String = "STARTING_KEY"
        const val dateKey : String = "DATE_KEY"
        const val goalsKey : String = "GOAL_KEY"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_goals_row,parent,false)
        return GoalsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: GoalsViewHolder, position: Int
    ) {
        val goals = goalsList[position]
        holder.label.text = goals.label
        holder.amount.text = goals.startingAmount.toString()
        holder.date.text = goals.date.toString()

        holder.itemView.findViewById<ImageButton>(R.id.imageButton).setOnClickListener{
            val pos:Int = holder.adapterPosition

            val bundle = Bundle()
            bundle.putString(labelKey, goalsList[pos].label)
            goalsList[pos].startingAmount?.let { it1 -> bundle.putDouble(startingKey, it1) }
            goalsList[pos].goalAmount?.let { it1 -> bundle.putDouble(amountKey, it1) }
            bundle.putString(dateKey, goalsList[pos].date)
            bundle.putString(goalsKey, goalsList[pos].goalId)
            bundle.putInt(positionKey, pos)

            val intent = Intent(holder.itemView.context, EditGoals::class.java)
            intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return goalsList.size
    }

    class GoalsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val label: TextView = itemView.findViewById(R.id.editTransactionLabel)
        val amount: TextView = itemView.findViewById(R.id.startingAmount)
        val date: TextView = itemView.findViewById(R.id.editTransactionDate)
    }
}