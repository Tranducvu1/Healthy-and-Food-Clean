package com.example.healthyandfoodclean.admin


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.healthyandfoodclean.HomeActivity
import com.example.healthyandfoodclean.LoginActivity
import com.example.healthyandfoodclean.R
import com.example.healthyandfoodclean.data.UserModel
import com.example.healthyandfoodclean.fragment.AdminActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.FirebaseDatabase

class AddUserActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etPassword: EditText
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var btnAddUser: Button
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        firebaseAuth = FirebaseAuth.getInstance()
        etName = findViewById(R.id.etName)
        etPassword = findViewById(R.id.etPassword)
        btnAddUser = findViewById(R.id.btnAddUser)
        database = FirebaseDatabase.getInstance()

        btnAddUser.setOnClickListener {
            val name = etName.text.toString().trim()
            val password = etPassword.text.toString().trim()
            registerUser(name,password)
        }
    }
    private fun registerUser(name: String, password: String) {
        // Kiểm tra mật khẩu chỉ chứa số
        if (!password.matches(Regex("\\d+"))) {
            Toast.makeText(
                baseContext, "Password must contain only numbers.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(name, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
// Đăng nhập thành công, cập nhật giao diện người dùng với thông tin người dùng đã đăng nhập                    val user = firebaseAuth.currentUser
                    val intent = Intent(this, AdminActivity::class.java)
                    startActivity(intent)
                } else {
                    // Nếu đăng nhập không thành công, hiển thị thông báo cho người dùng.
                    val exception = task.exception
                    if (exception is FirebaseAuthWeakPasswordException) {
                        Toast.makeText(
                            baseContext, "Password is too weak.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

}





