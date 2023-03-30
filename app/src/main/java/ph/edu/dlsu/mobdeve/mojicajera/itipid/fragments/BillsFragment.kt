package ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Bills
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Goals
import ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments.adapters.BillsViewAdapter
import ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments.adapters.GoalsViewAdapter


class BillsFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var billsList: ArrayList<Bills>
    private lateinit var billsAdapter: BillsViewAdapter
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bills, container, false)
        recyclerView = view.findViewById(R.id.billsRecycler)

        // TO DO: Fetch Data From FireBase

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        billsList = ArrayList()
        billsAdapter = BillsViewAdapter(billsList)
        recyclerView.adapter = billsAdapter

        getTransactionData()


        return view
    }


    private fun getTransactionData() {
        recyclerView.visibility = View.GONE
        database = FirebaseDatabase.getInstance().getReference("Bills")



        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                billsList.clear()
                if (snapshot.exists()) {
                    for (empSnap in snapshot.children) {
                        val billsData = empSnap.getValue(Bills::class.java)
                        billsList.add(billsData!!)
                    }
                    val mAdapter = BillsViewAdapter(billsList)
                    recyclerView.adapter = mAdapter

                    recyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
}