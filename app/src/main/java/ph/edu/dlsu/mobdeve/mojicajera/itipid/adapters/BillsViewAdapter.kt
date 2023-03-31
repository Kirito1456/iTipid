package ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.processNextEventInCurrentThread
import ph.edu.dlsu.mobdeve.mojicajera.itipid.EditBill
import ph.edu.dlsu.mobdeve.mojicajera.itipid.EditTransaction
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Bills


class BillsViewAdapter (private val billsList: ArrayList<Bills>)
    : RecyclerView.Adapter<BillsViewAdapter.BillsViewHolder>() {

    companion object {
        const val labelKey : String = "LABEL_KEY"
        const val amountKey : String = "AMOUNT_KEY"
        const val positionKey: String = "POSITION_KEY"
        const val dateKey : String = "DATE_KEY"
        const val billsKey : String = "BILLS_KEY"
    }

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

        holder.itemView.findViewById<ProgressBar>(R.id.progressBar)

        holder.itemView.findViewById<ImageButton>(R.id.imageButton).setOnClickListener{
            val pos:Int = holder.adapterPosition

            val bundle = Bundle()
            bundle.putString(RecyclerViewAdapter.labelKey, billsList[pos].label)
            billsList[pos].amount?.let { it1 -> bundle.putDouble(RecyclerViewAdapter.amountKey, it1) }
            bundle.putString(RecyclerViewAdapter.dateKey, billsList[pos].dueDate)
            bundle.putString(RecyclerViewAdapter.transacKey, billsList[pos].billID)
            bundle.putInt(RecyclerViewAdapter.positionKey, pos)

            val intent = Intent(holder.itemView.context, EditBill::class.java)
            intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)
        }


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