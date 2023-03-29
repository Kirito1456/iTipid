package ph.edu.dlsu.mobdeve.mojicajera.itipid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Bills
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Goals
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.User


class AddBalance : AppCompatActivity() {

    //EditTextViews
    private lateinit var etTransacName: EditText
    private lateinit var etTransacAmount: EditText
    private lateinit var etTransacDate: EditText
    private lateinit var etTransacDescription: EditText
    //Buttons

    //Database Reference
    private lateinit var dbRef : DatabaseReference
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var  firebase: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_balance)



        etTransacName = findViewById(R.id.label)
        etTransacAmount = findViewById(R.id.amount)
        etTransacDate = findViewById(R.id.date)
        etTransacDescription = findViewById(R.id.description)

        dbRef = FirebaseDatabase.getInstance().getReference("User")
        firebaseAuth = FirebaseAuth.getInstance()
        firebase = FirebaseDatabase.getInstance()


        // Save Button
        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener{
            saveTransactionData()
        }

        // Cancel Button
        val cancelButton = findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener(){
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

    }

    private fun saveTransactionData(){

        var arrayList : ArrayList<Transactions>
        val list = ArrayList<User>()
        // Get Values
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
        if (transacDescription.isEmpty()) {
            etTransacDescription.error = "Please enter Description"
        }

        val transacId = firebaseAuth.uid.toString()
        val uid = firebaseAuth.uid.toString()
        var transaction1 = Transactions(transacName, transacAmount.toDouble(), transacDate.toBoolean())

        dbRef.child("users").child(transacId).child("arrayList").setValue(transaction1).addOnCompleteListener {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()


                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
    }
}