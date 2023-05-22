package com.example.healthyandfoodclean




import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.healthyandfoodclean.data.UserModel
import com.example.healthyandfoodclean.databinding.ActivityLoginBinding
import com.example.healthyandfoodclean.fragment.AdminActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.Executor


class LoginActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var regis: TextView
    lateinit var info: String
    private lateinit var btnimage:ImageButton
    private lateinit var binding: ActivityLoginBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sEmail : String
    private lateinit var sPassword : String
    private lateinit var userID: String
    private lateinit var database: DatabaseReference
// ...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
       binding.btnLogin.isEnabled = false
        database = FirebaseDatabase.getInstance().getReference()

        firebaseAuth = FirebaseAuth.getInstance()

        binding.imgFingure.setOnClickListener {
            checkDeviceHasBiometric()
        }

        name = binding.personname
        password = binding.password
        val btnLogin = binding.btnLogin
        val forgot = binding.forgot
        regis = binding.regis

        btnimage = findViewById(R.id.imgFingure)

        fun saveData() {
            sEmail = name.text.toString().trim()
            sPassword = password.text.toString().trim()
            val user = UserModel(sEmail, sPassword)
            userID = FirebaseAuth.getInstance().currentUser!!.uid
            database.child("User").child(userID).setValue(user)
        }

        btnLogin.setOnClickListener {
            val username = name.text.toString()
            val password = password.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                loading()
                if (username == "useradmin" && password == "123456") {
                    // Đăng nhập với quyền admin
                    showAdminInfo()
                } else {
                    // Đăng nhập với quyền user
                    firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener { loginTask ->
                        if (loginTask.isSuccessful) {
                            loading()
                            saveData()
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            loading()
                            biometricPrompt.authenticate(promptInfo)
                            Toast.makeText(this, loginTask.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }


        regis.setOnClickListener {
            loading()
            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )

        }

        forgot.setOnClickListener {
            OnClickForGotpassword()

        }

    }
    private fun OnClickForGotpassword(){
        val emailAddress = "vutd.21it@vku.udn.vn"

        firebaseAuth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "Email sent.")
                }
            }
    }
    private fun showAdminInfo() {
        // Hiển thị thông tin tất cả người dùng (dành cho admin)
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
    }
    //Hàm này được gọi để kiểm tra vân tay trên thiết bị
    private fun checkDeviceHasBiometric() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
           //kiem tra khi ma hoa vân tay
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
                info = "App can authenticate using biometrics."
                binding.btnLogin.isEnabled = true
                initBiometricPrompt()
            }
            //vô hiệu đăng nhập
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.e("MY_APP_TAG", "No biometric features available on this device.")
                info = "No biometric features available on this device."
                binding.btnLogin.isEnabled = false
            }
            //vô hiệu đăng nhập
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
                info = "Biometric features are currently unavailable."
                binding.btnLogin.isEnabled = false
            }
            // mở cài đặt đăng ký sinh trắc học
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // Prompts the user to create credentials that your app accepts.
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                    )
                }
                startActivity(enrollIntent)
                binding.btnLogin.isEnabled = false
            }
        }
    }
//cau hinh vân tay
    private fun initBiometricPrompt() {
        executor = ContextCompat.getMainExecutor(this)
    //xu li kiem tra dau van tay
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@LoginActivity, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
            }
        })
//cấu hình vẫn tay
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login")
            .setSubtitle("Log in using your biometric credentials")
            .setNegativeButtonText("Use password")
            .build()
    }
    fun loading(){
        val loading = LoadingDialog(this)
        loading.startLoading()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object :Runnable{
            override fun run() {
               loading.isDismiss()
            }

        },5000)
    }

}

