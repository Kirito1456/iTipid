package ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceControl.Transaction
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions
import ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments.adapters.RecyclerViewAdapter


class DailyFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionList: ArrayList<Transactions>
    private lateinit var transactionAdapter: RecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_daily, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        transactionList = ArrayList()

        transactionList.add(Transactions("wew","Label", 20.0,"Jan1", "ewan"))
        transactionList.add(Transactions("wew","Label", 20.0,"Jan1", "ewan"))
        transactionList.add(Transactions("wew","Label", 20.0,"Jan1", "ewan"))
        transactionList.add(Transactions("wew","Label", 20.0,"Jan1", "ewan"))
        transactionList.add(Transactions("wew","Label", 20.0,"Jan1", "ewan"))
        transactionList.add(Transactions("wew","Label", 20.0,"Jan1", "ewan"))
        transactionList.add(Transactions("wew","Label", 20.0,"Jan1", "ewan"))
        transactionList.add(Transactions("wew","Label", 20.0,"Jan1", "ewan"))


        transactionAdapter =  RecyclerViewAdapter(transactionList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = transactionAdapter
        return view
    }


}