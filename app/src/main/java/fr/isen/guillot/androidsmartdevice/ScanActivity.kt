package fr.isen.guillot.androidsmartdevice

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
@OptIn(ExperimentalMaterial3Api::class)
class ScanActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var firstPhotoVisible by remember { mutableStateOf(true) }
            val topbartext = colorResource(id = R.color.vert)
            val topbarbackgroundColor = colorResource(id = R.color.bleu_fonce)
            val topscannerBLE = colorResource(id = R.color.bleu_fonce_scan_activity)
            val lignedechargement = colorResource(id = R.color.white)

            val devices = remember { mutableStateListOf<BluetoothDevice>() }

            LaunchedEffect(Unit) {
                devices.add(BluetoothDevice("La tv", "00:11:22:33:44:55", R.drawable.fort))
                devices.add(BluetoothDevice("La brosse à dent", "00:12:22:33:44:55", R.drawable.faible))
                devices.add(BluetoothDevice("Le sèche cheveux", "00:15:22:33:44:55", R.drawable.moyen))
                devices.add(BluetoothDevice("La PS6", "00:11:22:33:47:55", R.drawable.faible))
                devices.add(BluetoothDevice("L'écran plat de la cuisine", "01:11:22:33:44:55", R.drawable.moyen))
                }

            MaterialTheme(
                typography = AppTypography
            ) {
                Box(
                    modifier = Modifier
                        .width(500.dp)
                        .height(180.dp)
                        .background(topscannerBLE)
                ) {
                    if (firstPhotoVisible) {
                        Image(
                            painter = painterResource(R.drawable.play),
                            contentDescription = null,
                            modifier = Modifier
                                .offset(x = 0.dp, y = 70.dp)
                                .padding(horizontal = 150.dp)
                                .height(100.dp)
                                .background(topscannerBLE)
                                .clickable(onClick = { firstPhotoVisible = false })
                        )
                    } else {
                        Column {
                            Image(
                                painter = painterResource(R.drawable.pause),
                                contentDescription = null,
                                modifier = Modifier
                                    .offset(x = 0.dp, y = 70.dp)
                                    .padding(horizontal = 150.dp)
                                    .height(100.dp)
                                    .background(topscannerBLE)
                                    .clickable(onClick = { firstPhotoVisible = true })
                            )
                            LinearProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 0.dp, vertical = 72.dp),
                                color = lignedechargement
                            )
                        }
                    }
                }
                if (firstPhotoVisible) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 10.dp, vertical = 200.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon),
                            contentDescription = "Icone App"
                        )
                    }
                }
                else {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 175.dp)
                            .size(width = 1000.dp, height = 15000.dp)
                    ) {
                        LazyColumn {
                            items(devices) { device ->
                                DeviceItem(device)
                            }
                        }
                    }
                }
                TopAppBar(
                    title = {
                        Text(
                            text = "   Scanner Bluetooth",
                            color = topbartext,
                            style = MaterialTheme.typography.displayLarge
                        ) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = topbarbackgroundColor
                    )
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                }
            }
        }
    }
}
data class BluetoothDevice(val name: String, val macAddress: String, val imageResourceId: Int)

@Composable
fun DeviceItem(device: BluetoothDevice) {
    Row(
        modifier = Modifier
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = device.imageResourceId),
            contentDescription = "Bluetooth Device",
            modifier = Modifier
                .size(75.dp)
        )
        Column {
            Text(text = "   ", style = MaterialTheme.typography.bodySmall)
            Text(text = "   " + device.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = "           " + device.macAddress, style = MaterialTheme.typography.bodySmall)
        }
    }
}

