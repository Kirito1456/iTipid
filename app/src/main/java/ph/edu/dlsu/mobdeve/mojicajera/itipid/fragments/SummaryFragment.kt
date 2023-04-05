package ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions


class SummaryFragment : Fragment() {
    lateinit var pieChart:PieChart
    private val list: ArrayList<PieEntry> = ArrayList()
    private val pieDataSet = PieDataSet(list,"List")
    private val pieData = PieData(pieDataSet)


    private lateinit var transactionList: ArrayList<Transactions>
    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_summary, container, false)
        mAuth = FirebaseAuth.getInstance()
        transactionList = ArrayList()
        pieChart = view.findViewById(R.id.pie_chart)
        setUpPieChart()
        return view
    }

    private fun setUpPieChart() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Transactions")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val entries = ArrayList<PieEntry>()
                var totalIncome = 0.0
                var totalExpense = 0.0

                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        val transacData = snap.getValue(Transactions::class.java)
                        if (transacData?.uid == mAuth.uid) {
                            if (transacData != null) {
                                if (transacData.description == "Income") {
                                    totalIncome += transacData.amount!!
                                } else {
                                    totalExpense += transacData.amount!!
                                }
                            }
                        }
                    }
                }

                entries.add(PieEntry(totalIncome.toFloat(), "Income"))
                entries.add(PieEntry(totalExpense.toFloat(), "Expense"))

                val pastelBlue = Color.parseColor("#ADD8E6")
                val pastelRed = Color.parseColor("#fbb30c")
                val colors = arrayListOf(pastelBlue, pastelRed)

                val pieDataSet = PieDataSet(entries, "")
                pieDataSet.setColors(colors)
                pieDataSet.valueTextSize = 15f
                pieDataSet.valueTextColor = Color.BLACK

                val pieData = PieData(pieDataSet)
                pieChart.data = pieData
                pieChart.description.text = "Monthly Chart"
                pieChart.centerText = "Transactions"

                pieChart.animateY(200)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


}