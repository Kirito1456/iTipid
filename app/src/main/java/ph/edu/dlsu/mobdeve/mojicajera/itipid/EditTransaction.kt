package ph.edu.dlsu.mobdeve.mojicajera.itipid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ph.edu.dlsu.mobdeve.mojicajera.itipid.databinding.ActivityEditTransactionBinding
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions
import ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments.BillsFragment

class EditTransaction : AppCompatActivity() {

    companion object {
        const val labelKey : String = "LABEL_KEY"
        const val amountKey : String = "AMOUNT_KEY"
        const val positionKey: String = "POSITION_KEY"
        const val descKey : String = "DESC_KEY"
        const val dateKey : String = "DATE_KEY"
        const val transacKey : String = "TRANSAC_KEY"
    }

    private lateinit var dbRef : DatabaseReference
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var  firebase: FirebaseDatabase


    private lateinit var labelString: String
    private lateinit var amountString: String
    private lateinit var dateString: String
    private lateinit var descriptionString: String
    private lateinit var transacString: String
    private lateinit var binding: ActivityEditTransactionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("Transactions")
        firebaseAuth = FirebaseAuth.getInstance()
        firebase = FirebaseDatabase.getInstance()

        val data = intent.extras
        labelString = data?.getString(labelKey)!!
        amountString = data.getDouble(amountKey).toString()
        dateString = data.getString(dateKey)!!
        descriptionString = data.getString(descKey)!!
        transacString = data.getString(transacKey)!!

        binding.editTransactionLabel.setText(labelString)
        binding.editTransactionAmount.setText(amountString)
        binding.editTransactionDate.setText(dateString)
        binding.editTransactionType.setText(descriptionString)

        binding.saveButton.setOnClickListener {
            val transacId = transacString
            val uid = firebaseAuth.uid.toString()
            val transaction = Transactions(uid, binding.editTransactionLabel.text.toString(),
                binding.editTransactionAmount.text.toString().toDouble(), binding.editTransactionDate.text.toString(),
                binding.editTransactionType.text.toString(), transacId)


                dbRef.child(transacId).setValue(transaction).addOnCompleteListener {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()


                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
    }

        binding.cancelButton.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()

        }
    }
}
