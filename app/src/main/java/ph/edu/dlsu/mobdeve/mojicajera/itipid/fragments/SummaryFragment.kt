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
import com.github.mikephil.charting.utils.ColorTemplate
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R



class SummaryFragment : Fragment() {
    lateinit var pieChart:PieChart
    private val list: ArrayList<PieEntry> = ArrayList()
    private val pieDataSet = PieDataSet(list,"List")
    private val pieData = PieData(pieDataSet)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_summary, container, false)

        list.add(PieEntry(100f,"Income"))
        list.add(PieEntry(101f, "Expense"))
        pieChart = view.findViewById(R.id.pie_chart)
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)
        pieDataSet.valueTextSize = 15f
        pieDataSet.valueTextColor = Color.BLACK
        pieChart.data = pieData
        pieChart.description.text = "Monthly Chart"
        pieChart.centerText= "Transactions"

        pieChart.animateY(200)

        // Inflate the layout for this fragment
        return view
    }





}