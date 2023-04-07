package ph.edu.dlsu.mobdeve.mojicajera.itipid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ph.edu.dlsu.mobdeve.mojicajera.itipid.databinding.ActivityEditGoalsBinding
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Goals

class EditGoals : AppCompatActivity() {
    companion object {
        const val labelKey : String = "LABEL_KEY"
        const val amountKey : String = "AMOUNT_KEY"
        //const val positionKey: String = "POSITION_KEY"
        const val startingKey : String = "STARTING_KEY"
        const val dateKey : String = "DATE_KEY"
        const val goalsKey : String = "GOAL_KEY"
    }
    //Database References
    private lateinit var dbRef : DatabaseReference
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var  firebase: FirebaseDatabase

    private lateinit var labelString: String
    private lateinit var amountString: String
    private lateinit var dateString: String
    private lateinit var startingString: String
    private lateinit var goalsString: String
    private lateinit var binding: ActivityEditGoalsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditGoalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("Goals")
        firebaseAuth = FirebaseAuth.getInstance()
        firebase = FirebaseDatabase.getInstance()

        val data = intent.extras
        labelString = data?.getString(labelKey)!!
        startingString = data.getDouble(startingKey).toString()
        amountString = data.getDouble(amountKey).toString()
        dateString = data.getString(dateKey)!!
        goalsString = data.getString(goalsKey)!!

        binding.editGoalsLabel.setText(labelString)
        binding.editGoalsStarting.setText(startingString)
        binding.editGoalsAmount.setText(amountString)
        binding.editGoalsDate.setText(dateString)

        //save Edited Text
        binding.saveButton.setOnClickListener {
            val goalsId = goalsString
            val uid = firebaseAuth.uid.toString()
            val goals = Goals(uid, binding.editGoalsDate.text.toString(),
                binding.editGoalsStarting.text.toString().toDouble(), binding.editGoalsAmount.text.toString().toDouble(),
                binding.editGoalsLabel.text.toString(), goalsId)

            dbRef.child(goalsId).setValue(goals).addOnCompleteListener {
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
                val goalsId = goalsString
                dbRef.child(goalsId).removeValue()
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