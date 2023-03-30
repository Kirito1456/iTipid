package ph.edu.dlsu.mobdeve.mojicajera.itipid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ph.edu.dlsu.mobdeve.mojicajera.itipid.databinding.ActivityAddBalanceBinding
import ph.edu.dlsu.mobdeve.mojicajera.itipid.databinding.ActivityRegisterBinding
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.User
import java.util.*
import kotlin.collections.ArrayList


class AddBalance : AppCompatActivity() {

//    //EditTextViews
//    private lateinit var etTransacName: EditText
//    private lateinit var etTransacAmount: EditText
//    private lateinit var etTransacDate: EditText
//    private lateinit var etTransacDescription: EditText

    private var transacType: String = ""

    //Buttons
    private lateinit var  binding: ActivityAddBalanceBinding
    //Database Reference
    private lateinit var dbRef : DatabaseReference
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var  firebase: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBalanceBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_add_balance)


//        etTransacName = findViewById(R.id.label)
//        etTransacAmount = findViewById(R.id.amount)
//        etTransacDate = findViewById(R.id.date)
//        etTransacDescription = findViewById(R.id.description)

        dbRef = FirebaseDatabase.getInstance().getReference("Transactions")
        firebaseAuth = FirebaseAuth.getInstance()
        firebase = FirebaseDatabase.getInstance()



        // Income Button

        binding.incomeButton.setOnClickListener(){
            binding.expenseButton.isEnabled = false

        }

        // Expense Button
        binding.expenseButton.setOnClickListener(){
            binding.incomeButton.isEnabled = false

        }

        // Save Button

        binding.saveButton.setOnClickListener{
            saveTransactionData()
        }

        // Cancel Button

        binding.cancelButton.setOnClickListener(){
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }



    }

    private fun saveTransactionData() {

        if(!binding.expenseButton.isEnabled){
             transacType =  binding.incomeButton.text.toString()
        } else if (!binding.incomeButton.isEnabled){
             transacType =  binding.expenseButton.text.toString()
        }else{
            binding.incomeButton.error = "Please choose one"
        }

        // Get Values
        val transacName = binding.label.text.toString()
        val transacAmount = binding.amount.text.toString()
        val transacDate = binding.date.text.toString()
        val transacDescription = binding.description.text.toString()


        if (transacName.isEmpty()){
            binding.label.error = "Please enter Name"
        }
        if (transacAmount.isEmpty()) {
            binding.amount.error = "Provide Amount"
        }
        if (transacDate.isEmpty()) {
            binding.date.error = "Please enter Date"
        }
        if (transacDescription.isEmpty()) {
            binding.description.error = "Please enter Description"
        }

        val transacId = dbRef.push().key!!
        val uid = firebaseAuth.uid.toString()
        val transaction = Transactions(uid, transacName, transacAmount.toDouble(), Date(transacDate), transacDescription, transacType)

        dbRef.child(transacId).setValue(transaction).addOnCompleteListener {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()


                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
    }
}