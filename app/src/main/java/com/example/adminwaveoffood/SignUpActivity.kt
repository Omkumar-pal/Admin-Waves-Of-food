package com.example.adminwaveoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.adminwaveoffood.Model.UserModel
import com.example.adminwaveoffood.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var userName: String
    private lateinit var nameOfRestaurent: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var database: DatabaseReference
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //initializing auth
        auth = Firebase.auth
        //initializing Database
        database = Firebase.database.reference
        binding.createUserButton.setOnClickListener {
            //getting text from editText
            userName = binding.name.text.toString().trim()
            nameOfRestaurent = binding.restaurent.text.toString().trim()
            email = binding.email.text.toString().trim()
            password = binding.password.text.toString().trim()
             if (userName.isBlank() || nameOfRestaurent.isBlank() || email.isBlank() || password.isBlank()){
                 Toast.makeText(this, "Please fill all the details",Toast.LENGTH_SHORT ).show()
             }else{
                 createAccount(email,password)
             }
        }
        binding.AlreadyHaveButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
        val locationList = arrayOf("Jaipur", "Odisha", "Bundi", "Sikar")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.ListOfLocation
        autoCompleteTextView.setAdapter(adapter)

    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ Task->
            if (Task.isSuccessful){
            Toast.makeText(this, "Account created Successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account","createAccount: Failure",Task.exception)
        }
    }
}

    private fun saveUserData() {
        //getting text from editText
        userName = binding.name.text.toString().trim()
        nameOfRestaurent = binding.restaurent.text.toString().trim()
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()
        val user = UserModel(userName, nameOfRestaurent,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)
    }
}