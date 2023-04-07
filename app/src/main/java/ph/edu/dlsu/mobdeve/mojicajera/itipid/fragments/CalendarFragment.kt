package ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ph.edu.dlsu.mobdeve.mojicajera.itipid.R


class CalendarFragment : Fragment() {

    private lateinit var dateSet: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_calendar, container, false)


        return view
    }
}


