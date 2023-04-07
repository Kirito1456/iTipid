package ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ph.edu.dlsu.mobdeve.mojicajera.itipid.R
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions


class CalendarFragment : Fragment() {

    private lateinit var dateSet: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_calendar, container, false)


        return view
    }


    fun getTransactionsForDate(date: String): ArrayList<bills> {
        // Query your database or API for all transactions on the specified date
        // For example:
        val transactions = mutableListOf<Transactions>()
        val query = "SELECT * FROM transactions WHERE date = '$date'"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val transaction = Transactions(
                    cursor.getString(cursor.getColumnIndex("uid")),
                    cursor.getString(cursor.getColumnIndex("label")),
                    cursor.getDouble(cursor.getColumnIndex("amount")),
                    cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getString(cursor.getColumnIndex("transactionID"))
                )
                transactions.add(transaction)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return transactions
    }


}


