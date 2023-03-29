package ph.edu.dlsu.mobdeve.mojicajera.itipid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import ph.edu.dlsu.mobdeve.mojicajera.itipid.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {
    private lateinit var  binding: ActivityRegisterBinding
    private lateinit var  firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.SubmitButton.setOnClickListener(){

            val username = binding.Username.text.toString()
            val pass = binding.Password.text.toString()
            val confirmPass = binding.ConfirmPassword.text.toString()

            if(username.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if(pass == confirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(username, pass).addOnCompleteListener{
                        if(it.isSuccessful){

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                }else{
                    Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Empty Fields are not allowed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}