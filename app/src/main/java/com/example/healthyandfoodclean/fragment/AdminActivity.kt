package com.example.healthyandfoodclean.fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.healthyandfoodclean.LoginActivity
import com.example.healthyandfoodclean.R
import com.example.healthyandfoodclean.admin.AddUserActivity
import com.example.healthyandfoodclean.admin.EditUserActivity
import com.example.healthyandfoodclean.data.UserModel
import com.google.firebase.database.*

class AdminActivity : AppCompatActivity() {
    private lateinit var listViewUsers: ListView
    private lateinit var btnAddUser: Button
    private lateinit var btnEditUser: Button
    private lateinit var btnDeleteUser: Button
    private lateinit var database: DatabaseReference
    private lateinit var userList: ArrayList<String>
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private var selectedUser: UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        listViewUsers = findViewById(R.id.listViewUsers)
        btnAddUser = findViewById(R.id.btnAddUser)
        btnEditUser = findViewById(R.id.btnEditUser)
        btnDeleteUser = findViewById(R.id.btnDeleteUser)

        // Kết nối tới Firebase database
        database = FirebaseDatabase.getInstance().getReference("User")

        // Khởi tạo danh sách người dùng và adapter
        userList = ArrayList()
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, userList)
        listViewUsers.adapter = arrayAdapter

        // Thiết lập sự kiện khi người dùng chọn một người dùng trong danh sách
        listViewUsers.setOnItemClickListener { _, _, position, _ ->
            selectedUser = getUserFromList(position)
            btnEditUser.isEnabled = true
            btnDeleteUser.isEnabled = true
        }

        // Thiết lập sự kiện khi người dùng nhấn nút "Add User"
        btnAddUser.setOnClickListener {
            showAddUserDialog()
        }

        // Thiết lập sự kiện khi người dùng nhấn nút "Edit User"
        btnEditUser.setOnClickListener {
                showEditUserDialog()
        }

        // Thiết lập sự kiện khi người dùng nhấn nút "Delete User"
        btnDeleteUser.setOnClickListener {
                showDeleteUserDialog()
        }

        // Lấy danh sách người dùng từ Firebase database
        fetchUserList()
    }

    private fun getUserFromList(position: Int): UserModel? {
        if (position >= 0 && position < userList.size) {
            val userInfo = userList[position]
            val email = userInfo.substringBefore(":")
            return UserModel(email, "") // Chỉ cần email để tìm người dùng trong database
        }
        return null
    }
//truy suat nguoi dung tu co so du lieu
    private fun fetchUserList() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(UserModel::class.java)
                    user?.let {
                        userList.add("${it.name}: ${it.email}")
                    }
                }
                arrayAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Failed to fetch user list", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showAddUserDialog() {
        startActivity(
            Intent(
                this,
                AddUserActivity::class.java
            )
        )
    }

    private fun showEditUserDialog() {
        startActivity(
            Intent(
                this,
                EditUserActivity::class.java
            )
        )
    }

    private fun showDeleteUserDialog() {
        startActivity(
            Intent(
                this,
                LoginActivity::class.java
            )
        )
    }
}

