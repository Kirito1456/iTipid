package ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Bills

class CalendarBillsAdapter(private val billsList: ArrayList<Bills>)
    : RecyclerView.Adapter<CalendarBillsAdapter.EventsViewHolder>() {

    companion object {
        const val labelKey : String = "LABEL_KEY"
        const val amountKey : String = "AMOUNT_KEY"
        const val positionKey: String = "POSITION_KEY"
        const val dateKey : String = "DATE_KEY"
        const val billsKey : String = "BILLS_KEY"
    }

    var onItemClick: ((Bills) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_row_layout,parent,false)
        return EventsViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val bills = billsList[position]
        holder.label.text = bills.label
//        holder.amount.text = bills.amount.toString()
//        holder.date.text = bills.dueDate.toString()
    }


    override fun getItemCount(): Int {
        return billsList.size
    }

    class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val label: TextView = itemView.findViewById(R.id.)
        val label: TextView = itemView.findViewById(R.id.eventTitle)
    }
}
