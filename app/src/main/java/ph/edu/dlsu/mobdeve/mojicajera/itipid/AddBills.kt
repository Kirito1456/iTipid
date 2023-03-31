package ph.edu.dlsu.mobdeve.mojicajera.itipid

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Bills

class AddBills : AppCompatActivity() {
    //EditTextViews
    private lateinit var etBillsLabel: EditText
    private lateinit var etBillsAmount: EditText
    private lateinit var etBillsDate: EditText
    //Buttons

    //Database Reference
    private lateinit var dbRef : DatabaseReference
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var  firebase: FirebaseDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bills)

        etBillsLabel = findViewById(R.id.editTransactionLabel)
        etBillsAmount = findViewById(R.id.editTransactionAmount)
        etBillsDate = findViewById(R.id.editTransactionDate)

        dbRef = FirebaseDatabase.getInstance().getReference("Bills")
        firebaseAuth = FirebaseAuth.getInstance()
        firebase = FirebaseDatabase.getInstance()


        // Save Button
        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener{
            saveGoalsData()
        }

        // Cancel Button
        val cancelButton = findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener(){
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveGoalsData(){

        // Get Values
        val billsName = etBillsLabel.text.toString()
        val billsAmount = etBillsAmount.text.toString()
        val billsDate = etBillsDate.text.toString()

        if (billsName.isEmpty()){
            etBillsLabel.error = "Please enter Label"
        }
        if (billsAmount .isEmpty()) {
            etBillsAmount.error = "Provide Amount"
        }
        if (billsDate.isEmpty()) {
            etBillsDate.error = "Please enter Date"
        }

        val billsId = dbRef.push().key!!
        val uid = firebaseAuth.uid.toString()
        val bills = Bills(uid, billsName,billsAmount.toDouble(), billsDate, billsId)

        if(billsName.isNotEmpty() && billsAmount.isNotEmpty() && billsDate.isNotEmpty()){
            dbRef.child(billsId).setValue(bills).addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}