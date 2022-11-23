package com.example.ugd3_d_0659

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.shashank.sony.fancytoastlib.FancyToast

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val message = intent.getStringExtra("toastMessage")
        FancyToast.makeText(context!!,message,FancyToast.LENGTH_LONG,FancyToast.DEFAULT,false).show();
//        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }
}