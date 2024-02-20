package com.nikhilosatwal.cyntra_assesment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nikhilosatwal.cyntra_assesment.databinding.ActivityMainBinding

class LogInActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val sharedPreference =  getSharedPreferences("LOGIN_CRED", Context.MODE_PRIVATE)
        if (sharedPreference.getString("username","").equals("10000001") && (sharedPreference.getInt("password",0)==12345678)) {
            startActivity(Intent(this, DeviceStatusActivity::class.java))
        }
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding.loginButton.setOnClickListener {
            val empId = binding.editTextEmpId.text.toString()
            val password = binding.editTextNumberPassword.text.toString()
            if (empId == "10000001" && password == "12345678") {
                val sharedPreference =  getSharedPreferences("LOGIN_CRED", Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()
                editor.putString("username","10000001")
                editor.putInt("password",12345678)
                editor.apply()
                binding.editTextEmpId.setText("")
                binding.editTextNumberPassword.setText("")
                startActivity(Intent(this, DeviceStatusActivity::class.java))
            } else {
                Toast.makeText(this,"Invalid Employee ID / Password",Toast.LENGTH_LONG).show()
            }
        }
    }
}