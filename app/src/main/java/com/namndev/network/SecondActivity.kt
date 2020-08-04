package com.namndev.network

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.namndev.network.monitoring.ConnectivityEvent
import com.namndev.network.monitoring.NetworkConnectivityListener
import kotlinx.android.synthetic.main.activity_main.*

class SecondActivity : AppCompatActivity(),
    NetworkConnectivityListener {


    override fun networkConnectivityChanged(event: ConnectivityEvent) {
        if (event.isConnected) {
            showSnackBar(
                textView,
                "The network is back !"
            )
            wifi_off_icon.visibility = View.GONE
        } else {
            showSnackBar(
                textView,
                "There is no more network"
            )
            wifi_off_icon.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        textView.text = "This view is hooked to network changed using NetworkConnectivityListener"

        wifi_off_icon.visibility = if (!isConnected) View.VISIBLE else View.GONE


        fab.setImageResource(android.R.drawable.ic_media_rew)
        fab.setOnClickListener {
            onBackPressed()
        }
    }

}
