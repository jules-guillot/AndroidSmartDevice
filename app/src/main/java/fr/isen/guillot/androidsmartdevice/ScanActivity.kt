package fr.isen.guillot.androidsmartdevice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.colorResource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

class ScanActivity : ComponentActivity() {
    private var isFirstToggle: MutableState<Boolean>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val background = colorResource(id = R.color.bleu_fonce)
            val topbartext = colorResource(id = R.color.vert)
            val devices = remember { mutableStateListOf<BluetoothDevice>() }
            isFirstToggle = remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                devices.add(BluetoothDevice("La tv", "00:11:22:33:44:55", R.drawable.fort))
                devices.add(BluetoothDevice("La brosse à dent", "00:12:22:33:44:55", R.drawable.faible))
                devices.add(BluetoothDevice("Le sèche cheveux", "00:15:22:33:44:55", R.drawable.moyen))
                devices.add(BluetoothDevice("La PS6", "00:11:22:33:47:55", R.drawable.faible))
                devices.add(BluetoothDevice("L'écran plat de la cuisine", "01:11:22:33:44:55", R.drawable.moyen))
                devices.add(BluetoothDevice("Thermomix", "00:11:22:33:44:58", R.drawable.fort))
                devices.add(BluetoothDevice("vélo électrique", "00:12:22:33:44:58", R.drawable.faible))
                devices.add(BluetoothDevice("L'écran plat de la cuisine2", "01:11:22:33:44:55", R.drawable.moyen))
                devices.add(BluetoothDevice("Thermomix2", "00:11:22:33:44:58", R.drawable.fort))
                devices.add(BluetoothDevice("vélo électrique2", "00:12:22:33:44:58", R.drawable.faible))
            }

            MaterialTheme {
                CustomScaffoldWithTopAppBar(
                    titleText = "          Scanner BLE",
                    titleColor = topbartext,
                    backgroundColor = background
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        TogglePlayPause(
                            command = "Play",
                            backgroundColor = background,
                            progressIndicatorColor = background,
                            devices = devices,
                            isFirstToggle = isFirstToggle!!
                        )
                        if (isFirstToggle!!.value==false) {
                            LazyColumn {
                                items(devices) { device ->
                                    DeviceItem(device)
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}