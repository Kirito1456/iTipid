package ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Bills
import ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters.BillsViewAdapter


class BillsFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var billsList: ArrayList<Bills>
    private lateinit var billsTemp: ArrayList<Bills>
    private lateinit var billsAdapter: BillsViewAdapter
    private lateinit var database: DatabaseReference
    private lateinit var  firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bills, container, false)
        recyclerView = view.findViewById(R.id.billsRecycler)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        billsList = ArrayList()
        billsTemp = ArrayList()
        billsAdapter = BillsViewAdapter(billsList)
        recyclerView.adapter = billsAdapter
        firebaseAuth = FirebaseAuth.getInstance()
        getTransactionData()

        return view
    }


    private fun getTransactionData() {
        recyclerView.visibility = View.GONE
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
                            billsTemp.add(i)
                        }
                    }
                    val mAdapter = BillsViewAdapter(billsTemp)
                    recyclerView.adapter = mAdapter

                    recyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })


    }
}