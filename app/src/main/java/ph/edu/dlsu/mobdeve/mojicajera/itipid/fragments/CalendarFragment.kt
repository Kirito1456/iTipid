package ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters.BillsViewAdapter
import ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters.CalendarGoalsAdapter
import ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters.GoalsViewAdapter
import ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters.TransactionsAdapter
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Bills
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Goals
import java.text.DateFormatSymbols
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class CalendarFragment : Fragment() {
    private lateinit var billsRecycler: RecyclerView
    private lateinit var billsList: ArrayList<Bills>
    private lateinit var billsTemp: ArrayList<Bills>
    private lateinit var billsAdapter: TransactionsAdapter

    private lateinit var goalsRecycler: RecyclerView
    private lateinit var goalsList: ArrayList<Goals>
    private lateinit var goalsTemp: ArrayList<Goals>
    private lateinit var goalsAdapter: CalendarGoalsAdapter

    private lateinit var database: DatabaseReference
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var dateSet: String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_calendar, container, false)

        dateSet = "04/02/2001"
        billsRecycler = view.findViewById(R.id.billsRecycler)
        billsRecycler.layoutManager = LinearLayoutManager(activity)
        billsRecycler.setHasFixedSize(true)
        billsList = ArrayList()
        billsTemp = ArrayList()
        billsAdapter = TransactionsAdapter(billsList)
        billsRecycler.adapter = billsAdapter

        goalsRecycler = view.findViewById(R.id.eventsRecyler)
        goalsRecycler.layoutManager = LinearLayoutManager(activity)
        goalsRecycler.setHasFixedSize(true)
        goalsList = ArrayList()
        goalsTemp = ArrayList()
        goalsAdapter = CalendarGoalsAdapter(goalsList)
        goalsRecycler.adapter = goalsAdapter

        firebaseAuth = FirebaseAuth.getInstance()

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val textView = view.findViewById<TextView>(R.id.selectedDate)


        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        dateSet = LocalDate.now().format(formatter)
        textView.text = "Today is : $dateSet"
        billsTemp.clear() // Clear the bills list
        goalsTemp.clear()
        getBillsData()
        getGoalsData()


        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            dateSet = String.format("%02d/%02d/%04d", month+1, dayOfMonth, year)
            textView.text = "Today is : $dateSet"
            billsTemp.clear()
            goalsTemp.clear()
            getBillsData()
            getGoalsData()
        }

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
                    val mAdapter = TransactionsAdapter(billsTemp)
                    billsRecycler.adapter = mAdapter

                    billsRecycler.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })


    }

    fun getGoalsData(){
        goalsRecycler.visibility = View.GONE
        val id = firebaseAuth.uid
        database = FirebaseDatabase.getInstance().getReference("Goals")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                goalsList.clear()
                if(snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val goalsData = empSnap.getValue(Goals::class.java)
                        goalsList.add(goalsData!!)
                    }
                    for(i in goalsList){
                        if(i.uid == id){
                            if(i.date == dateSet){
                                goalsTemp.add(i)
                            }
                        }

                    }
                    val mAdapter =  CalendarGoalsAdapter(goalsTemp)
                    goalsRecycler.adapter = mAdapter

                    goalsRecycler.visibility = View.VISIBLE
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



