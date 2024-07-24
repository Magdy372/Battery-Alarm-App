package com.magdy.batteryalarmapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.BatteryManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.app.NotificationCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var batteryLevel by remember { mutableStateOf(100) }

            // Register receiver to listen for battery changes
            val batteryReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                    batteryLevel = level
                }
            }

            val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            registerReceiver(batteryReceiver, intentFilter)

            // Display battery status
            BatteryStatusDisplay(batteryLevel)
        }
    }
}

@Composable
fun BatteryStatusDisplay(batteryLevel: Int, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // Show different images based on battery level
    val image = if(batteryLevel >= 20)
        painterResource(id = R.drawable.battery_full)
    else
        painterResource(id = R.drawable.battery_low)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {

        Image(painter = image, contentDescription = "Battery")
    }

}
