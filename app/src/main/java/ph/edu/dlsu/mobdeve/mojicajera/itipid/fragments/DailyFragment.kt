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
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions
import ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters.RecyclerViewAdapter


class DailyFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionList: ArrayList<Transactions>
    private lateinit var transactionAdapter: RecyclerViewAdapter
    private lateinit var database: DatabaseReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_daily, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)


        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        transactionList = ArrayList()
        transactionAdapter =  RecyclerViewAdapter(transactionList)
        recyclerView.adapter = transactionAdapter

         getTransactionData()


        return view
    }

    // TODO : Update DashBoard
//    private fun updateDashBoard(){
//
//        val totalIncome : Double = transactionList.map{it.amount}.sum()
//        val totalExpense : Double = transactionList.filter{ it.amount!! > 0.0 }.map{it.amount}.sum()
//
//    }

    private fun getTransactionData(){
    recyclerView.visibility = View.GONE
    database = FirebaseDatabase.getInstance().getReference("Transactions")



    database.addValueEventListener(object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            transactionList.clear()
            if(snapshot.exists()){
                for (empSnap in snapshot.children){
                    val transacData = empSnap.getValue(Transactions::class.java)
                    transactionList.add(transacData!!)
                }
                val mAdapter =  RecyclerViewAdapter(transactionList)
                recyclerView.adapter = mAdapter

                recyclerView.visibility = View.VISIBLE
            }
        }

        override fun onCancelled(error: DatabaseError) {

        }

    })
}

}