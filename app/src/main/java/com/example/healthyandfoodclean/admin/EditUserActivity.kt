package com.example.healthyandfoodclean.admin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.healthyandfoodclean.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class EditUserActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etCurrentPassword: EditText
    private lateinit var btnSave: Button
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        etName = findViewById(R.id.etName)
        etNewPassword = findViewById(R.id.etNewPassword)
        etCurrentPassword = findViewById(R.id.etCurrentPassword)
        btnSave = findViewById(R.id.btnSave)
        firebaseAuth = FirebaseAuth.getInstance()

        val user = firebaseAuth.currentUser
        etName.setText(user?.displayName)

        btnSave.setOnClickListener {
            val newName = etName.text.toString().trim()
            val newPassword = etNewPassword.text.toString().trim()
            val currentPassword = etCurrentPassword.text.toString().trim()
//kiem tra nguoi dung
            if (newName.isEmpty()) {
                etName.error = "Name is required."
                etName.requestFocus()
                return@setOnClickListener
            }
//xac thuc ten nguoi dung
            val credential = firebaseAuth.currentUser?.email?.let { email ->
                firebaseAuth.currentUser?.let { currentUser ->
                    val credential = EmailAuthProvider.getCredential(email, currentPassword)
                    currentUser.reauthenticate(credential)
                }
            }
//update ten nguoi dung //thong bao loi
            credential?.addOnCompleteListener { reauthTask ->
                if (reauthTask.isSuccessful) {
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(newName)
                        .build()
//truy suat authen kiem tra ton tai/update
                    user?.updateProfile(profileUpdates)?.addOnCompleteListener { updateProfileTask ->
                        if (updateProfileTask.isSuccessful) {
                            if (!newPassword.isEmpty()) {
                                user.updatePassword(newPassword).addOnCompleteListener { updatePasswordTask ->
                                    if (updatePasswordTask.isSuccessful) {
                                        Toast.makeText(this, "Username and password updated successfully", Toast.LENGTH_SHORT).show()
                                        finish()
                                    } else {
                                        Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(this, "Username updated successfully", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        } else {
                            Toast.makeText(this, "Failed to update username", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Authentication failed. Please enter the correct current password.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

