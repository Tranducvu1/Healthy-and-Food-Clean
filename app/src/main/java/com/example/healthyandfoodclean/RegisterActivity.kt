package com.example.healthyandfoodclean


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.healthyandfoodclean.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var personname: EditText
    private lateinit var password: EditText
    private lateinit var repassword: EditText
    private lateinit var phonenumber: EditText
    private lateinit var btnregister: Button
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        personname = findViewById(R.id.personname)
        password = findViewById(R.id.password)
        repassword= findViewById(R.id.repassword)
        phonenumber = findViewById(R.id.phone)
        firebaseAuth = FirebaseAuth.getInstance()
        btnregister = binding.btnregister
        btnregister.setOnClickListener {
            val username = personname.text.toString()
            val password = password.text.toString()
            val repassword = repassword.text.toString()
            val phonenumber = phonenumber.text.toString()
            val db = MyDatabaseHelper(applicationContext, "Healthy and Food Clean", null, 1)

            registerUser(username, password)
        }
    }
    private fun registerUser(username: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = firebaseAuth.currentUser
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

    private fun validPassword(): String?
    {
        val passwordText = binding.password.text.toString()
        if(passwordText.length < 8)
        {
            return "Minimum 8 Character Password"
        }
        if(!passwordText.matches(".*[A-Z].*".toRegex()))
        {
            return "Must Contain 1 Upper-case Character"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex()))
        {
            return "Must Contain 1 Lower-case Character"
        }
        if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex()))
        {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }

        return null
    }
    private fun resetForm()
    {
        var message = "personame: " +  binding.personname.text
        message += "\nPassword: " + binding.password.text
        message += "\nrepassword: " + binding.repassword.text
        message += "\nPhonenumber: " + binding.phone.text


        AlertDialog.Builder(this)
            .setTitle("Form submitted")
            .setMessage(message)
            .setPositiveButton("Okay"){ _,_ ->

                binding.password.text = null
                binding.personname.text = null
                binding.repassword.text = null
                binding.phone.text = null



            }
            .show()
    }

    private fun validrePassword(): String?
    {
        val repasswordText = binding.repassword.text.toString()
        if(repasswordText.length < 8)
        {
            return "Minimum 8 Character Password"
        }
        if(!repasswordText.matches(".*[A-Z].*".toRegex()))
        {
            return "Must Contain 1 Upper-case Character"
        }
        if(!repasswordText.matches(".*[a-z].*".toRegex()))
        {
            return "Must Contain 1 Lower-case Character"
        }
        if(!repasswordText.matches(".*[@#\$%^&+=].*".toRegex()))
        {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }

        return null
    }

    private fun validPhone(): String?
    {
        val phoneText = binding.phone.text.toString()
        if(!phoneText.matches(".*[0-9].*".toRegex()))
        {
            return "Must be all Digits"
        }
        if(phoneText.length != 10)
        {
            return "Must be 10 Digits"
        }
        return null
    }
}
