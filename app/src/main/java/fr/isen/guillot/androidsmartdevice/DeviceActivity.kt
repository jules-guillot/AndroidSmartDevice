package fr.isen.guillot.androidsmartdevice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class DeviceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deviceName = intent.getStringExtra("deviceName") ?: "Unknown Device"
        val deviceAddress = intent.getStringExtra("deviceAddress") ?: "Unknown Address"
        val deviceRSSI = intent.getIntExtra("deviceRSSI", 0)
        val signalImageRes = intent.getIntExtra("signalImageRes", 0)


        setContent {
            val backgroundColor = colorResource(id = R.color.bleu_fonce)
            val titleColor = colorResource(id = R.color.vert)
            val buttonTextColor = colorResource(id = R.color.bleu_clair)
            MaterialTheme {
                CustomScaffoldWithTopAppBar(
                    titleText = "Device Details",
                    titleColor = titleColor,
                    backgroundColor = backgroundColor
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding).fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = signalImageRes),
                            contentDescription = "Signal strength icon",
                            modifier = Modifier
                                .height(120.dp)
                                .padding(16.dp)
                                .fillMaxWidth()
                        )
                        Text(
                            text = "Name: $deviceName",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "Address: $deviceAddress",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "RSSI: $deviceRSSI dBm",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(
                            onClick = {
                                // Connect logic here
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor)
                        ) {
                            Text(
                                text = "Connect to Device",
                                color = buttonTextColor,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }

    }
}
