package ph.edu.dlsu.mobdeve.mojicajera.itipid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions



class AddBalance : AppCompatActivity() {

    //EditTextViews
    private lateinit var etTransacName: EditText
    private lateinit var etTransacAmount: EditText
    private lateinit var etTransacDate: EditText
    private lateinit var etTransacDescription: TextView
    //Buttons

    //Database Reference
    private lateinit var dbRef : DatabaseReference
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var  firebase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_balance)

        etTransacName = findViewById(R.id.editTransactionLabel)
        etTransacAmount = findViewById(R.id.editTransactionAmount)
        etTransacDate = findViewById(R.id.editTransactionDate)
        etTransacDescription = findViewById(R.id.description)

        dbRef = FirebaseDatabase.getInstance().getReference("Transactions")
        firebaseAuth = FirebaseAuth.getInstance()
        firebase = FirebaseDatabase.getInstance()

        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener{
            saveTransactionData()

        }

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener(){
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }


        val incomeButton = findViewById<Button>(R.id.incomeButton)
        val expenseButton = findViewById<Button>(R.id.expenseButton)
        incomeButton.setOnClickListener(){
            etTransacDescription.text = "Income"
        }

        expenseButton.setOnClickListener(){
            etTransacDescription.text ="Expense"
        }
    }

    private fun saveTransactionData(){

        val transacName = etTransacName.text.toString()
        val transacAmount = etTransacAmount.text.toString()
        val transacDate = etTransacDate.text.toString()
        val transacDescription = etTransacDescription.text.toString()

        if (transacName.isEmpty()){
            etTransacName.error = "Please enter Name"
        }
        if (transacAmount.isEmpty()) {
            etTransacAmount.error = "Provide Amount"
        }
        if (transacDate.isEmpty()) {
            etTransacDate.error = "Please enter Date"
        }
        if (transacDescription == "Transaction") {
            etTransacDescription.error = "Please pick a Transaction"
        }

        val transacId = dbRef.push().key!!
        val uid = firebaseAuth.uid.toString()
        val transaction = Transactions(uid, transacName, transacAmount.toDouble(), transacDate, transacDescription, transacId)


        if(transacName.isNotEmpty() && transacAmount.isNotEmpty() && transacDate.isNotEmpty() && transacDescription!="Transaction"){
            dbRef.child(transacId).setValue(transaction).addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
        }
        }

}
