package com.example.healthyandfoodclean.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.healthyandfoodclean.R
import com.example.healthyandfoodclean.data.UserModel
import com.google.firebase.database.FirebaseDatabase

class DeleteUserActivity : AppCompatActivity() {
    private lateinit var tvDeleteUserName: TextView
    private lateinit var tvDeleteUserEmail: TextView
    private lateinit var btnDeleteUser: Button
    private lateinit var database: FirebaseDatabase
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_user)
        userId = intent.getStringExtra("userId") ?: ""
        tvDeleteUserName = findViewById(R.id.tvDeleteUserName)
        tvDeleteUserEmail = findViewById(R.id.tvDeleteUserEmail)
        btnDeleteUser = findViewById(R.id.btnDeleteUser)
        database = FirebaseDatabase.getInstance()

//        val user = intent.getParcelableExtra<UserModel>("user")
//        if (user != null) {
//            userId = user.userId ?: ""
//            tvDeleteUserName.text = user.name
//            tvDeleteUserEmail.text = user.email
//        }

        btnDeleteUser.setOnClickListener {
            deleteUser()
        }
    }

    private fun deleteUser() {
        val userReference = database.reference.child("User").child(userId)

        userReference.removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to delete user", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
