package com.example.myapplication


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.FragmentStCardDesignBinding
import com.example.myapplication.databinding.MajorInformationsBinding


class MajorInformations : AppCompatActivity() {
    val binding by lazy { MajorInformationsBinding.inflate(layoutInflater) }
    val sBinding by lazy{ FragmentStCardDesignBinding.inflate(layoutInflater)}
    var major:String = "하하"

    override fun onCreate(savedInstaceState: Bundle?) {
        super.onCreate(savedInstaceState)
        setContentView(binding.root)
        Log.d("messi", "majorinfo : "+major)
    }
}



