package com.ly.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
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
            Log.d("lylog", " 连接成功 ")
        } catch (connectException: IOException) {
            Log.d("lylog", " 连接失败 ")
            return
        }

        if (mmSocket != null) {
            ConnectedThreadgo(mmSocket)
        }
    }

    /** Will cancel an in-progress connection, and close the socket  */
    fun cancel() {
        try {
            mmSocket!!.close()
        } catch (e: IOException) {
        }

    }
}

class ConnectedThreadgo(private var mmSocket: BluetoothSocket):Thread() {
    private val mmInStream: InputStream?
    private val mmOutStream: OutputStream?

    init {
        var tmpIn: InputStream? = null
        var tmpOut: OutputStream? = null

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = mmSocket.inputStream
            tmpOut = mmSocket.outputStream
        } catch (e: IOException) {
        }

        mmInStream = tmpIn
        mmOutStream = tmpOut
    }

    override fun run() {
        val buffer = ByteArray(1024)  // buffer store for the stream
        var bytes: Int // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream!!.read(buffer)
                // Send the obtained bytes to the UI Activity
//                mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
//                    .sendToTarget()
            } catch (e: IOException) {
                break
            }

        }
    }

    /* Call this from the main Activity to send data to the remote device */
    fun write(bytes: ByteArray) {
        try {
            mmOutStream!!.write(bytes)
        } catch (e: IOException) {
        }

    }

    /* Call this from the main Activity to shutdown the connection */
    fun cancel() {
        try {
            mmSocket.close()
        } catch (e: IOException) {
        }

    }
}
