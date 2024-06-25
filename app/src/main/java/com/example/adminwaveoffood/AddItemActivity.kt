package com.example.adminwaveoffood

import android.app.Instrumentation.ActivityResult
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.adminwaveoffood.Model.AllMenu
import com.example.adminwaveoffood.databinding.ActivityAddItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {

    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDescription: String
    private lateinit var foodIngredient: String
    private var foodImageUri: Uri? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initializing auth and database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        //implementing AdditemButton funcitonality

        binding.AddItemButton.setOnClickListener{
            //geting data from textfield
            foodName = binding.foodName.text.toString().trim()
            foodPrice = binding.foodPrice.text.toString().trim()
            foodDescription = binding.shortDescription.text.toString().trim()
            foodIngredient = binding.ingredients.text.toString().trim()
            if (!(foodName.isBlank() || foodPrice.isBlank()|| foodDescription.isBlank() || foodIngredient.isBlank())){
                uploadData()
                Toast.makeText(this, "Item Added Successfully", Toast.LENGTH_SHORT).show()
                finish()
                }else{
                Toast.makeText(this, "Fill all the Details", Toast.LENGTH_SHORT).show()
            }
        }
        binding.SelectImage.setOnClickListener{
            pickimage.launch("image/*")
        }
        binding.BackButton.setOnClickListener{
            finish()
        }
    }
    private fun uploadData() {

        // Creating a "menu" Reference for the node for the database
        val MenuRef = database.getReference("Menu")
        //Generate a unique key for the new menu item
        val newItemKey = MenuRef.push().key

        if (foodImageUri != null){
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    downloadUrl->

                    // Create a new menu item
                    val newItem = AllMenu(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodDescription = foodDescription,
                        foodIngredient = foodIngredient,
                        foodImage =  downloadUrl.toString()
                    )
                    newItemKey?.let{
                        key ->
                        MenuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show()

                        }
                            .addOnFailureListener{
                                Toast.makeText(this, "Data Uploading Failed", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }.addOnFailureListener{
                Toast.makeText(this, "Image Upload Failed", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Please Select an Image", Toast.LENGTH_SHORT).show()
        }
    }
    private val pickimage = registerForActivityResult(ActivityResultContracts.GetContent()) {uri ->
        if (uri != null){
            binding.SelectedImage.setImageURI(uri)
            foodImageUri = uri
        }
    }
}