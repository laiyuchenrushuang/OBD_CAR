package com.ly.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.util.*


/**
 * Created by ly on 2019/7/12 15:30
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class ConnectThread(
    mmDevice: BluetoothDevice?,
    adpter: BluetoothAdapter?
) : Thread() {

    private var mBluetoothAdapter: BluetoothAdapter? = null

    private val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private val mmSocket: BluetoothSocket?
    private var mContext: Context? = null

    init {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        var tmp: BluetoothSocket? = null

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = mmDevice!!.createRfcommSocketToServiceRecord(MY_UUID)
        } catch (e: IOException) {
        }

        mmSocket = tmp
        mBluetoothAdapter = adpter
    }

    override fun run() {

        // Cancel discovery because it will slow down the connection
        mBluetoothAdapter!!.cancelDiscovery()
        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            if (this.mmSocket!!.isConnected() == true) {
                cancel()
            }
            System.out.print("连接成功")
            Log.d("lylog"," 连接成功 ")
        } catch (connectException: IOException) {
            System.out.print("连接失败")
            Log.d("lylog"," 连接失败 ")
            return
        }
    }

    private fun showToast(s: String) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show()
    }

    /** Will cancel an in-progress connection, and close the socket  */
    fun cancel() {
        try {
            mmSocket!!.close()
        } catch (e: IOException) {
        }

    }
}