package com.example.adminwaveoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwaveoffood.adapter.PendingOrderAdapter
import com.example.adminwaveoffood.databinding.ActivityPendingOrderBinding
import com.example.adminwaveoffood.databinding.PendingItemListBinding

class PendingOrderActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityPendingOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val customerName = arrayListOf(
            "John Doe",
            "Jane Smith",
            "Mike Johnson",
        )
        val PendingQuantity = arrayListOf(
            "Received",
            "notReceived",
            "Pending",
        )
        val PendingfoodImage = arrayListOf(
            R.drawable.menu_photo,
            R.drawable.menu_photo__1_,
            R.drawable.photo_menu__1_
        )
        binding.BackButton.setOnClickListener{
            finish()
        }
        val adapter = PendingOrderAdapter(customerName,PendingQuantity,PendingfoodImage, this)
        binding.PendingRecyclerView.adapter = adapter
        binding.PendingRecyclerView.layoutManager  = LinearLayoutManager(this)

    }
}