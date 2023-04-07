package ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters.BillsViewAdapter
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Bills
import java.text.DateFormatSymbols


class CalendarFragment : Fragment() {
    private lateinit var billsRecycler: RecyclerView
    private lateinit var billsList: ArrayList<Bills>
    private lateinit var billsTemp: ArrayList<Bills>
    private lateinit var billsAdapter: BillsViewAdapter
    private lateinit var database: DatabaseReference
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var dateSet: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_calendar, container, false)


        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val textView = view.findViewById<TextView>(R.id.selectedDate)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            dateSet = String.format("%02d/%02d/%04d", month+1, dayOfMonth, year)
            var date = convertDateFormat(dateSet)
            textView.text = "$dateSet"
        }

        billsRecycler = view.findViewById(R.id.billsRecycler)
        billsRecycler.layoutManager = LinearLayoutManager(activity)
        billsRecycler.setHasFixedSize(true)

        billsList = ArrayList()
        billsTemp = ArrayList()
        billsAdapter = BillsViewAdapter(billsList)
        billsRecycler.adapter = billsAdapter
        firebaseAuth = FirebaseAuth.getInstance()
        getBillsData()

        return view
    }

    private fun getBillsData() {
        billsRecycler.visibility = View.GONE
        val id = firebaseAuth.uid
        database = FirebaseDatabase.getInstance().getReference("Bills")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                billsList.clear()
                if (snapshot.exists()) {
                    for (empSnap in snapshot.children) {
                        val billsData = empSnap.getValue(Bills::class.java)
                        billsList.add(billsData!!)
                    }
                    for (i in billsList){
                        if(i.uid == id){
                            if(i.dueDate == dateSet){
                                billsTemp.add(i)
                            }

                        }
                    }
                    val mAdapter = BillsViewAdapter(billsTemp)
                    billsRecycler.adapter = mAdapter

                    billsRecycler.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })


    }
}

fun convertDateFormat(inputDate: String): String {
    val dateParts = inputDate.split("/")
    val month = dateParts[0].toInt()
    val day = dateParts[1].toInt()
    val year = dateParts[2]

    val monthString = DateFormatSymbols().months[month - 1]
    return "$year, $monthString $day"
}



