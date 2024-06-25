package com.example.adminwaveoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.adminwaveoffood.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {
    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.BackButton.setOnClickListener{
            finish()
        }
        binding.name.isEnabled = false
        binding.Address.isEnabled = false
        binding.email.isEnabled = false
        binding.Phone.isEnabled = false
        binding.Password.isEnabled = false
        var isEnalble = false
        binding.EditButton.setOnClickListener{
            isEnalble = ! isEnalble
            binding.name.isEnabled = isEnalble
            binding.Phone.isEnabled = isEnalble
            binding.Address.isEnabled = isEnalble
            binding.email.isEnabled = isEnalble
            binding.Password.isEnabled = isEnalble
            if (isEnalble){
                binding.name.requestFocus()
            }

        }
    }
}