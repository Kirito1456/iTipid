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
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions
import ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters.TransactionsAdapter


class DailyFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionList: ArrayList<Transactions>
    private lateinit var transactionTemp: ArrayList<Transactions>
    private lateinit var transactionAdapter: TransactionsAdapter
    private lateinit var database: DatabaseReference
    private lateinit var  firebaseAuth: FirebaseAuth


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_daily, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)


        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        transactionList = ArrayList()
        transactionTemp = ArrayList()
        transactionAdapter =  TransactionsAdapter(transactionList)
        recyclerView.adapter = transactionAdapter
        firebaseAuth = FirebaseAuth.getInstance()
         getTransactionData()


        return view
    }


    private fun getTransactionData(){
    recyclerView.visibility = View.GONE
        val id = firebaseAuth.uid.toString()

    database = FirebaseDatabase.getInstance().getReference("Transactions")



    database.addValueEventListener(object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            transactionList.clear()
            if(snapshot.exists()){
                for (empSnap in snapshot.children){
                    val transacData = empSnap.getValue(Transactions::class.java)
                    transactionList.add(transacData!!)
                }
                for(i in transactionList){
                    if(i.uid == id){
                        transactionTemp.add(i)
                    }
                }
                transactionTemp.reverse()
                val mAdapter =  TransactionsAdapter(transactionTemp)
                recyclerView.adapter = mAdapter

                recyclerView.visibility = View.VISIBLE
            }
        }

        override fun onCancelled(error: DatabaseError) {

        }

    })
}

}