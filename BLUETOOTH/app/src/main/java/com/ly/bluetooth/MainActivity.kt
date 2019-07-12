package com.ly.bluetooth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.bluetooth.BluetoothAdapter
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.bluetooth.BluetoothDevice
import android.content.IntentFilter
import android.widget.ArrayAdapter
import android.content.Intent
import android.view.View
import android.widget.AdapterView


/**
 * 本文要点
 */
class MainActivity : AppCompatActivity(), MyReceiver.BTCallBack {


    private var resultitem: String? = null
    private var adapter: BluetoothAdapter? = null
    private val REQUEST_ENABLE: Int = 1
    private var mAdapter: ArrayAdapter<String>? = null
    var deviceList = HashSet<BluetoothDevice>() //储存设备方便建立连接
    var myReceiver = MyReceiver()
    var permission = arrayOf(
        android.Manifest.permission.BLUETOOTH,
        android.Manifest.permission.BLUETOOTH_ADMIN,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBTEnviroment()

        bt_send!!.setOnClickListener {
            sendMsg(edit_text.text.toString())
        }

        connect!!.setOnClickListener {
            var bd : BluetoothDevice? = null
            if (deviceList.size == 0) return@setOnClickListener
            for (s:BluetoothDevice in deviceList){
               if(s.name != null && s.name.equals(resultitem)) {
                   bd =s
                   break
               }
            }
           ConnectThread(bd,adapter).start()
        }
    }

    private fun initBTEnviroment() {
        if (ActivityCompat.checkSelfPermission(this, permission[0]) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, permission[1]) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, permission[2]) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, permission[3]) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, permission, 1)
        }
        myReceiver.registerLisenerBTname(this)
        mAdapter = ArrayAdapter<String>(this, R.layout.simple_spinner_item)
        mAdapter!!.setDropDownViewResource(R.layout.item_spinner__down_common)
        sp.adapter = mAdapter

        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(myReceiver, filter)
        adapter = BluetoothAdapter.getDefaultAdapter()
        if (!adapter!!.isEnabled()) {
            //弹出对话框提示用户是后打开
            val enabler = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enabler, REQUEST_ENABLE)
            //不做提示，直接打开，不建议用下面的方法，有的手机会有问题。
            // mBluetoothAdapter.enable();
        }
        sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {

                resultitem = sp.getItemAtPosition(position) as String
                Log.d("lylog", "result = " + resultitem)
            }

        }
    }

    private fun sendMsg(msg: String) {

        Log.d("lylog", "startDiscovery")
    }

    override fun btNameCallBack(device: BluetoothDevice) {
        deviceList.add(device)
        mAdapter!!.add(device.name)
        mAdapter!!.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        adapter!!.startDiscovery()
    }
}
