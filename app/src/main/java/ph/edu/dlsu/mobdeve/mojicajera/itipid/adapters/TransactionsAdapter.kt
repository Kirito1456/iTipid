package ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.mojicajera.itipid.EditTransaction
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions


class TransactionsAdapter (private val transactionList: ArrayList<Transactions>)
    : RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>()  {
    companion object {
        const val labelKey : String = "LABEL_KEY"
        const val amountKey : String = "AMOUNT_KEY"
        const val positionKey: String = "POSITION_KEY"
        const val descKey : String = "DESC_KEY"
        const val dateKey : String = "DATE_KEY"
        const val transacKey : String = "TRANSAC_KEY"
    }

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
            val pos:Int = holder.adapterPosition

            val bundle = Bundle()
            bundle.putString(labelKey, transactionList[pos].label)
            transactionList[pos].amount?.let { it1 -> bundle.putDouble(amountKey, it1) }
            bundle.putString(dateKey, transactionList[pos].date)
            bundle.putString(descKey, transactionList[pos].description)
            bundle.putString(transacKey, transactionList[pos].transactionID)
            bundle.putInt(positionKey, pos)

            val intent = Intent(holder.itemView.context, EditTransaction::class.java)
            intent.putExtras(bundle)
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