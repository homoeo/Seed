package com.example.seed

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import java.io.IOException
import java.util.*

class ledControl : AppCompatActivity() {
    // Button btnOn, btnOff, btnDis;

    internal lateinit var Discnt:Button
    lateinit var sd: Button
    lateinit var mf : Button
    lateinit var left: Button
    lateinit var right: Button
    lateinit var mb: Button
    lateinit var stop: Button
    lateinit var automatic: Button
    lateinit var dibbler: Button
    private var ConnectSuccess = true
     var address: String? = null
      var progress: ProgressDialog? = null
      var myBluetooth: BluetoothAdapter? = null
companion object{
    var btSocket: BluetoothSocket? = null
}

    private var isBtConnected = false
    //SPP UUID. Look for it
    internal val myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_led_control)
        sd = findViewById(R.id.seedrop) as Button
        mf = findViewById(R.id.moveforward) as Button
        left = findViewById(R.id.left) as Button
        right = findViewById(R.id.right) as Button
        mb = findViewById(R.id.moveforward) as Button
        stop = findViewById(R.id.stop) as Button
        automatic = findViewById(R.id.automatic) as Button
        dibbler = findViewById(R.id.dibbler) as Button
        sd.setOnClickListener {
            btSocket!!.getOutputStream().write(2)
        }
        mf.setOnClickListener {
            btSocket!!.getOutputStream().write(3)
        }
        left.setOnClickListener {
            btSocket!!.getOutputStream().write(8)
        }
        right.setOnClickListener {
            btSocket!!.getOutputStream().write(9)
        }
        mb.setOnClickListener {
            btSocket!!.getOutputStream().write(5)
        }
        stop.setOnClickListener {
            btSocket!!.getOutputStream().write(4)
        }
        automatic.setOnClickListener {
            btSocket!!.getOutputStream().write(10)
        }
        dibbler.setOnClickListener {
            btSocket!!.getOutputStream().write(1)
        }

        val newint = intent
        address = newint.getStringExtra(DeviceList().EXTRA_ADDRESS); //r


        Discnt = findViewById(R.id.discnt) as Button
        if (btSocket == null || !isBtConnected) {
            myBluetooth =
                BluetoothAdapter.getDefaultAdapter()//get the mobile bluetooth device
            val dispositivo =
                myBluetooth?.getRemoteDevice(address)//connects to the device's address and checks if it's available
            btSocket =
                dispositivo?.createInsecureRfcommSocketToServiceRecord(myUUID)//create a RFCOMM (SPP) connection
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
            btSocket?.connect()//start connection
        }
        if (!ConnectSuccess) {
            msg("Connection Failed. Is it a SPP Bluetooth? Try again.")
            finish()
        } else {
            msg("Connected.")
            isBtConnected = true
        }
        progress?.dismiss()


        //commands to be sent to bluetooth


        Discnt.setOnClickListener {
            Disconnect() //close connection
        }
    }
    private fun Disconnect() {
        if (btSocket != null)
        //If the btSocket is busy
        {
            try {
                btSocket!!.close() //close connection
            } catch (e: IOException) {
                msg("Error")
            }

        }
        finish() //return to the first layout

    }

    private fun turnOffLed() {
        if (btSocket != null) {
            try {
                btSocket!!.getOutputStream().write("0".toByteArray())
            } catch (e: IOException) {
                msg("Error")
            }

        }
    }

    private fun turnOnLed() {
        if (btSocket != null) {
            try {
                btSocket!!.getOutputStream().write("5".toByteArray())
            } catch (e: IOException) {
                msg("Error")
            }

        }
    }

    // fast way to call Toast
    private fun msg(s: String) {
        Toast.makeText(applicationContext, s, Toast.LENGTH_LONG).show()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_led_control, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }




}
