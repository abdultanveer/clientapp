package com.example.clientapp

import android.content.ComponentName
import android.content.ContentValues
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.clientapp.databinding.ActivityMainBinding
import com.example.motorolademos.IAddInterface


var TAG = "MainActivityClientapp"

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var iRemoteService: IAddInterface? = null
    private var serverMessenger: Messenger? = null
    private var clientMessenger: Messenger? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnShowDetails.setOnClickListener { showDetails() }
        binding.btnCalendar.setOnClickListener { openMotoCalendar() }
        binding.btnAdd.setOnClickListener { bindAddService() }
        binding.btnSendmessage.setOnClickListener { bindAddService() }
        binding.btnInsert.setOnClickListener { insertContentProvider() }
        binding.btnRetreive.setOnClickListener { loadDataContentprovider() }
    }

    private fun showDetails() {
        val CONTENT_URI = Uri.parse("content://com.demo.user.provider/users")
       var cursor = contentResolver.query(CONTENT_URI,null,null,null,null)
       cursor?.moveToFirst()
        var idIndex = cursor?.getColumnIndex("id")
        var nameIndex = cursor?.getColumnIndex("name")
        binding.tvSum.text = cursor?.getString(nameIndex!!)
    }


    private fun bindAddService() {
        clientMessenger = Messenger(handler)

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
        override fun onServiceConnected(p0: ComponentName?, msgBinder: IBinder?) {
            Log.i("clientActivity", "client activity  connected to service")
            serverMessenger = Messenger(msgBinder)
            sendMessageToServer()

        }


        override fun onServiceDisconnected(p0: ComponentName?) {
            TODO("Not yet implemented")
        }

    }

    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            // Update UI with remote process info
            val bundle = msg.data
            binding.tvSum.text = bundle.getInt("sum").toString()
        }
    }
    private fun sendMessageToServer() {
        val message = Message.obtain(handler)
        val bundle = Bundle()
        bundle.putString("clientmsg","client says hello")
        message.data = bundle
        message.replyTo = clientMessenger // we offer our Messenger object for communication to be two-way
        try {
            serverMessenger?.send(message)
        } catch (e: RemoteException) {
            e.printStackTrace()
        } finally {
            message.recycle()
        }
    }

    private fun loadDataContentprovider() {
        val usersUri = Uri.parse("content://com.moto.usersdb")
        var cursor = contentResolver.query(usersUri,null,null,null,null,)
        cursor?.moveToFirst()
        var nameIndex = cursor?.getColumnIndex("name")
        var name =  cursor?.getString(nameIndex!!)
        binding.tvSum.text = name
    }

    private fun insertContentProvider() {
        val usersUri = Uri.parse("content://com.moto.usersdb")
        val values = ContentValues()
        values.put("name",binding.etName.text.toString())
        contentResolver.insert(usersUri,values)

    }
}