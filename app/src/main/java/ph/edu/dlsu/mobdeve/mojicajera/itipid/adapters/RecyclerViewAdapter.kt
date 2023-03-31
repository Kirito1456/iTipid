package ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import ph.edu.dlsu.mobdeve.mojicajera.itipid.EditTransaction
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
       // holder.bindData(this.videoData[position])
        val transaction = transactionList[position]
        holder.label.text = transaction.label
        holder.amount.text = transaction.amount.toString()
        holder.date.text = transaction.date
        holder.type.text = transaction.description


        if(holder.type.text == "Income"){
            holder.amount.text = "+ %.2f".format(transaction.amount)
            holder.amount.setTextColor(Color.parseColor("#00FF00"))
        }else {holder.amount.setTextColor(Color.parseColor("#FF0000"))
            holder.amount.text = "- %.2f".format(transaction.amount)}


        holder.itemView.findViewById<ImageButton>(R.id.editButton).setOnClickListener{
            var intent = Intent(it.context, EditTransaction::class.java)

            intent.putExtra("label", transaction.label)
            intent.putExtra("amount", transactionList[position].amount)
            intent.putExtra("date", transactionList[position].date)
            intent.putExtra("description", transactionList[position].description)

            holder.itemView.context.startActivity(intent)
        }


    }
    override fun getItemCount(): Int {
        return transactionList.size
    }



    class TransactionViewHolder(itemView: View)  : RecyclerView.ViewHolder(itemView){

        val label: TextView = itemView.findViewById(R.id.tvLabel)
        val amount: TextView = itemView.findViewById(R.id.transactionAmount)
        val date: TextView = itemView.findViewById(R.id.tvDate)
        val type: TextView = itemView.findViewById(R.id.type)


    }
}