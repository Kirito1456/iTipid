package ph.edu.dlsu.mobdeve.mojicajera.itipid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //This Segments Handles Create Account and Trouble Logging In Text View//

        // On Click Listener for Create an Account Text View
        val registerTV = findViewById<TextView>(R.id.createAccount)
        registerTV.setOnClickListener(){
            val intent = Intent(this, Register::class.java)
            startActivity(intent)

        }

        // Trouble Logging In?
        val forgotTV = findViewById<TextView>(R.id.forgetPass)
        forgotTV.setOnClickListener(){
            val intent = Intent(this, forgotPass::class.java)
            startActivity(intent)

        }

        // Handles On Click for Button
        val loginButton = findViewById<Button>(R.id.button)
        loginButton.setOnClickListener(){
            val intent = Intent(this, Register::class.java)
            startActivity(intent)

        }

    }






}