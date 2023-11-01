package com.example.clientapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.clientapp.databinding.ActivityMainBinding
import com.example.motorolademos.IAddInterface

var TAG = "MainActivityClientapp"

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var iRemoteService: IAddInterface? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalendar.setOnClickListener { openMotoCalendar() }
        binding.btnAdd.setOnClickListener { bindAddService() }
    }

    private fun bindAddService() {
        Log.i(TAG, "bindAddService method")
        val intent = Intent("ineed.addition.moto")
       // intent.setPackage("com.example.motorolademos")
        //intent.setClassName(this,"com.example.motorolademos.AdditionService")

        val pack = IAddInterface::class.java.`package`
        intent.setPackage(pack.name)
        bindService(intent,connection, BIND_AUTO_CREATE)
    }

    private fun openMotoCalendar() {
        var calendarIntent = Intent("ineed.calendar.motorola")
        calendarIntent.setPackage("com.example.motorolademos")
        startActivity(calendarIntent)
    }


    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, aBinder: IBinder?) {
            Log.i("clientActivity", "client activity  connected to service")
            iRemoteService = IAddInterface.Stub.asInterface(aBinder)
            var sum = iRemoteService?.add(30, 40)
            binding.tvSum.text = sum.toString()

        }


        override fun onServiceDisconnected(p0: ComponentName?) {
            TODO("Not yet implemented")
        }

    }
}