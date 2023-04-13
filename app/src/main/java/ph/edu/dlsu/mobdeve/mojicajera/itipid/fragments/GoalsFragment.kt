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
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Goals
import ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters.GoalsViewAdapter


class GoalsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var goalsList: ArrayList<Goals>
    private lateinit var goalsTemp: ArrayList<Goals>
    private lateinit var goalsAdapter: GoalsViewAdapter
    private lateinit var database: DatabaseReference
    private lateinit var  firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_goals, container, false)
        recyclerView = view.findViewById(R.id.goalsRecycler)


        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        goalsList = ArrayList()
        goalsTemp = ArrayList()
        goalsAdapter =  GoalsViewAdapter(goalsList)
        recyclerView.adapter = goalsAdapter
        firebaseAuth = FirebaseAuth.getInstance()
        getTransactionData()

        return view
    }

     fun getTransactionData(){
        recyclerView.visibility = View.GONE
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
                        if(i.uid==id){
                            goalsTemp.add(i)
                        }
                    }
                    val mAdapter =  GoalsViewAdapter(goalsTemp)
                    recyclerView.adapter = mAdapter

                    recyclerView.visibility = View.VISIBLE
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })
    }


}