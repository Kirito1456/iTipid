package ph.edu.dlsu.mobdeve.mojicajera.itipid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ph.edu.dlsu.mobdeve.mojicajera.itipid.databinding.ActivityEditGoalsBinding
import ph.edu.dlsu.mobdeve.mojicajera.itipid.databinding.ActivityEditTransactionBinding
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Goals
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions
import ph.edu.dlsu.mobdeve.mojicajera.itipid.fragments.GoalsFragment

class EditGoals : AppCompatActivity() {
    companion object {
        const val labelKey : String = "LABEL_KEY"
        const val amountKey : String = "AMOUNT_KEY"
        const val positionKey: String = "POSITION_KEY"
        const val startingKey : String = "DESC_KEY"
        const val dateKey : String = "DATE_KEY"
        const val goalsKey : String = "GOAL_KEY"
    }
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
        amountString = data.getDouble(amountKey).toString()
        dateString = data.getString(dateKey)!!
        startingString = data.getDouble(startingKey).toString()
        goalsString = data.getString(goalsKey)!!

        binding.editGoalsLabel.setText(labelString)
        binding.editGoalsAmount.setText(amountString)
        binding.editGoalsDate.setText(dateString)
        binding.editGoalsStarting.setText(startingString)

        binding.saveButton.setOnClickListener {
            val goalsId = goalsString
            val uid = firebaseAuth.uid.toString()
            val goals = Goals(uid, binding.editGoalsDate.text.toString(),
                binding.editGoalsStarting.text.toString().toDouble(), binding.editGoalsAmount.text.toString().toDouble(),
                binding.editGoalsLabel.text.toString(), goalsId)


            dbRef.child(goalsId).setValue(goals).addOnCompleteListener {
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