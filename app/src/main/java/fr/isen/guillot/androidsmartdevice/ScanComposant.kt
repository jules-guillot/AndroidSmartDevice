package fr.isen.guillot.androidsmartdevice

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomScaffoldWithTopAppBar(
    titleText: String,
    titleColor: Color,
    backgroundColor: Color,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = titleText,
                        color = titleColor,
                        style = MaterialTheme.typography.displaySmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor
                )
            )
        },
        content = content
    )
}

@SuppressLint("MissingPermission")
@Composable
fun ScanDevice(
    scanInteraction: ScanInteraction,
) {
    val context = LocalContext.current
    if (scanInteraction.isScanning) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(scanInteraction.devices) { scanResult ->
                DeviceItem(
                    scanResult = scanResult,
                    onItemClick = { device ->
                        val intent = Intent(context, DeviceActivity::class.java).apply {
                            putExtra("deviceName", device.name ?: "Unknown Device")
                            putExtra("deviceAddress", device.address ?: "Unknown Address")
                            putExtra("deviceRSSI", scanResult.rssi)
                        }
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}


@SuppressLint("MissingPermission")
@Composable
fun DeviceItem(scanResult: ScanResult, onItemClick: (android.bluetooth.BluetoothDevice) -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onItemClick(scanResult.device)
                val intent = Intent(context, DeviceActivity::class.java)
                context.startActivity(intent)
            })
            .padding(vertical = 8.dp)
    ) {
        if (scanResult.device.name != "null") {
            val context = LocalContext.current
            val rssiImage = when (scanResult.rssi) {
                in -50..0 -> "fort"
                in -80..-50 -> "moyen"
                in -100..-80 -> "faible"
                else -> "out_of_range"
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f).padding(8.dp)) {
                    Text(
                        text = "Name: ${scanResult.device.name}",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Address: ${scanResult.device.address}",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "RSSI: ${scanResult.rssi}",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                Column(modifier = Modifier.weight(1f).padding(8.dp)) {
                    Image(painter = painterResource(id = context.resources.getIdentifier(rssiImage, "drawable", context.packageName)),
                        contentDescription = "RSSI Image",
                        modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}


@Composable
fun ScanComposant(
    command: String,
    backgroundColor: Color,
    progressIndicatorColor: Color,
    isFirstToggle: MutableState<Boolean>,
    scanInteraction: ScanInteraction
) {
    val firstPhotoVisible = remember { mutableStateOf(command == "Pause") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        val imageResource = if (firstPhotoVisible.value) R.drawable.pause else R.drawable.play

        Image(
            painter = painterResource(id = imageResource),
            contentDescription = if (firstPhotoVisible.value) "Pause" else "Play",
            modifier = Modifier
                .padding(horizontal = 150.dp)
                .height(100.dp)
                .background(backgroundColor)
                .clickable {
                    if (isFirstToggle.value) {  // Check si c'est la première fois
                        isFirstToggle.value = false
                        scanInteraction.playAction()
                    }
                    firstPhotoVisible.value = !firstPhotoVisible.value
                }
        )
        if (firstPhotoVisible.value) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp, vertical = 70.dp),
                color = progressIndicatorColor
            )
        }
    }
}

class ScanInteraction(
    var isScanning: Boolean,
    val devices: MutableList<ScanResult>,
    val playAction: () -> Unit,
    val onDeviceClick: (BluetoothDevice) -> Unit
)