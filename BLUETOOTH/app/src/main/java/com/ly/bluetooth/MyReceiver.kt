package com.ly.bluetooth

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils

/**
 * Created by ly on 2019/7/12 11:31
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class MyReceiver : BroadcastReceiver() {
    private var btcallback: MyReceiver.BTCallBack? = null
    private var listStatic = ArrayList<String>()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (BluetoothDevice.ACTION_FOUND.equals(intent!!.action)) {
            var device: BluetoothDevice = intent!!.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            if (device != null && !TextUtils.isEmpty(device.name)) {
                if (btcallback != null && !listStatic.contains(device.name)) {
                    listStatic.add(device.name)
                    btcallback!!.btNameCallBack(device)
                }
            }

        }
    }

    interface BTCallBack {
        fun btNameCallBack(name: BluetoothDevice)
    }

    fun registerLisenerBTname(listener: BTCallBack) {
        this.btcallback = listener
    }
}