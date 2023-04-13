package ph.edu.dlsu.mobdeve.mojicajera.itipid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ph.edu.dlsu.mobdeve.mojicajera.itipid.databinding.ActivityEditBillBinding
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Bills

class EditBill : AppCompatActivity() {
    companion object {
        const val billsKey : String = "BILLS_KEY"
        const val labelKey : String = "LABEL_KEY"
        const val amountKey : String = "AMOUNT_KEY"
        const val dateKey : String = "DATE_KEY"
    }

    private lateinit var dbRef : DatabaseReference
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var  firebase: FirebaseDatabase

    private lateinit var billsString: String
    private lateinit var labelString: String
    private lateinit var amountString: String
    private lateinit var dateString: String
    private lateinit var binding: ActivityEditBillBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("Bills")
        firebaseAuth = FirebaseAuth.getInstance()
        firebase = FirebaseDatabase.getInstance()

        val data = intent.extras
        labelString = data?.getString(labelKey)!!
        amountString = data.getDouble(amountKey).toString()
        dateString = data.getString(dateKey)!!
        billsString = data.getString(billsKey)!!

        binding.editTransactionLabel.setText(labelString)
        binding.editTransactionAmount.setText(amountString)
        binding.editTransactionDate.setText(dateString)

        binding.saveButton.setOnClickListener {
            val billsId = billsString
            val uid = firebaseAuth.uid.toString()
            val bills = Bills(uid,
                binding.editTransactionLabel.text.toString(),
                binding.editTransactionAmount.text.toString().toDouble(),
                binding.editTransactionDate.text.toString(),
                billsId)

                dbRef.child(billsId).setValue(bills).addOnCompleteListener {
                    Toast.makeText(this, "Data Updated successfully", Toast.LENGTH_LONG).show()

                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
        binding.deleteButton.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("Confirm Delete")
            builder.setMessage("Are you sure you want to delete this item?")
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                val billsId = billsString
                dbRef.child(billsId).removeValue()
                Toast.makeText(applicationContext, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
                finish()
            }
            builder.setNegativeButton("No") { dialogInterface, which ->
            }

            val alertDialog: AlertDialog = builder.create()

            alertDialog.setCancelable(false)
            alertDialog.show()

        }

        binding.cancelButton.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
    }
}