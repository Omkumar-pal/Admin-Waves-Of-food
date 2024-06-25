package com.example.adminwaveoffood.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminwaveoffood.Model.AllMenu
import com.example.adminwaveoffood.databinding.ItemItemBinding
import com.google.firebase.database.DatabaseReference

class MenuAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    databaseReference: DatabaseReference
) : RecyclerView.Adapter<MenuAdapter.AddItemViewHolder>() {
    private val itemQuantities = IntArray(menuList.size) { 1 }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding)
    }

    override fun getItemCount(): Int = menuList.size
    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class AddItemViewHolder(private val binding: ItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val menuItem = menuList[position]
                val uriString =  menuItem.foodImage
                val uri = Uri.parse(uriString)
                val quantity = itemQuantities[position]
                FoodItemName.text = menuItem.foodName
                FoodItemPrice.text = menuItem.foodPrice
                Glide.with(context).load(uri).into(FoodItemImage)
                quantityButton.text = quantity.toString()

                MinusButton.setOnClickListener {
                    decreaseQuantity(position)
                }
                PlusButton2.setOnClickListener {
                    increaseQuantity(position)
                }
                TrashButton.setOnClickListener {
                    deleteQuantity(position)
                }
            }
        }


        private fun deleteQuantity(position: Int) {
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuList.size)
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.quantityButton.text = itemQuantities[position].toString()
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.quantityButton.text = itemQuantities[position].toString()
            }

        }
    }
}