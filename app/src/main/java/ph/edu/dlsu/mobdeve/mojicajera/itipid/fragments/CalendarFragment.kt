package ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import java.text.DateFormatSymbols


class CalendarFragment : Fragment() {

    private lateinit var dateSet: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_calendar, container, false)

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val textView = view.findViewById<TextView>(R.id.events)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            dateSet = String.format("%02d/%02d/%04d", month+1, dayOfMonth, year)
            var date = convertDateFormat(dateSet)
            textView.text = "Events for: $date"
        }
        return view
    }
}

fun convertDateFormat(inputDate: String): String {
    val dateParts = inputDate.split("/")
    val month = dateParts[0].toInt()
    val day = dateParts[1].toInt()
    val year = dateParts[2]

    val monthString = DateFormatSymbols().months[month - 1]
    return "$year, $monthString $day"
}

