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




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_daily, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recycler
        recyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)

        // Fetch Data Logic

        transactionAdapter = RecyclerViewAdapter(transactionList)
        recyclerView.adapter = transactionAdapter

        // Edit Button Logic

    }


}