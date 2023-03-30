package ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments

import android.os.Bundle
import android.renderscript.Sampler.Value
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceControl.Transaction
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions
import ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments.adapters.RecyclerViewAdapter


class DailyFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionList: ArrayList<Transactions>
    private lateinit var transactionAdapter: RecyclerViewAdapter
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_daily, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)



        // TO DO: Fetch Data From FireBase

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        transactionList = ArrayList()
        transactionAdapter =  RecyclerViewAdapter(transactionList)
        recyclerView.adapter = transactionAdapter

         getTransactionData()

        return view
    }

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
            TODO("Not yet implemented")
        }

    })
}

}