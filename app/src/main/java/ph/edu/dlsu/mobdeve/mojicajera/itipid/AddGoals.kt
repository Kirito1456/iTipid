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
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Goals

class AddGoals : AppCompatActivity() {
    //EditTextViews
    private lateinit var etGoalsLabel: EditText
    private lateinit var etGoalsAmount: EditText
    private lateinit var etGoalsStarting: EditText
    private lateinit var etGoalsDate: EditText

    //Database Reference
    private lateinit var dbRef : DatabaseReference
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var  firebase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_goals)

        etGoalsLabel = findViewById(R.id.editTransactionLabel)
        etGoalsAmount = findViewById(R.id.goalAmount)
        etGoalsStarting = findViewById(R.id.startingAmount)
        etGoalsDate = findViewById(R.id.editTransactionDate)

        dbRef = FirebaseDatabase.getInstance().getReference("Goals")
        firebaseAuth = FirebaseAuth.getInstance()
        firebase = FirebaseDatabase.getInstance()

        //Save Button
        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener{
            saveGoalsData()
        }

        //Cancel Button
        val cancelButton = findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener(){
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }

    private fun saveGoalsData(){

        val goalsName = etGoalsLabel.text.toString()
        val goalsAmount = etGoalsAmount.text.toString()
        val goalsStarting= etGoalsStarting.text.toString()
        val goalsDate = etGoalsDate.text.toString()

        if (goalsName.isEmpty()){
            etGoalsLabel.error = "Please enter Label"
        }
        if (goalsAmount .isEmpty()) {
            etGoalsAmount.error = "Provide Amount"
        }
        if (goalsStarting.isEmpty()) {
            etGoalsStarting.error = "Please enter a Starting Amount"
        }
        if (goalsDate.isEmpty()) {
            etGoalsDate.error = "Please enter Date"
        }

        val goalsId = dbRef.push().key!!
        val uid = firebaseAuth.uid.toString()
        val goals = Goals(uid, goalsDate, goalsStarting.toDouble(), goalsAmount.toDouble(), goalsName, goalsId)

        if(goalsName.isNotEmpty() && goalsAmount.isNotEmpty() && goalsDate.isNotEmpty() && goalsStarting.isNotEmpty()){
            dbRef.child(goalsId).setValue(goals).addOnCompleteListener {
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