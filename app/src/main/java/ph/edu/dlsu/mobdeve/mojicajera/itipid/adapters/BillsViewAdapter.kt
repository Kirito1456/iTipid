package ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Bills


class BillsViewAdapter (private val billsList: ArrayList<Bills>)
    : RecyclerView.Adapter<BillsViewAdapter.BillsViewHolder>() {
    var onItemClick: ((Bills) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_bills_row,parent,false)
        return BillsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: BillsViewHolder, position: Int
    ) {
        val bills = billsList[position]
        holder.label.text = bills.label
        holder.amount.text = bills.amount.toString()
        holder.date.text = bills.dueDate.toString()


    }

    override fun getItemCount(): Int {
        return billsList.size
    }

    class BillsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val label: TextView = itemView.findViewById(R.id.billLabel)
        val amount: TextView = itemView.findViewById(R.id.billAmount)
        val date: TextView = itemView.findViewById(R.id.billDue)
    }
}