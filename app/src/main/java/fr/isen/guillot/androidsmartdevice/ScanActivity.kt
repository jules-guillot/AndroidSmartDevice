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
import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
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

            MaterialTheme {
                CustomScaffoldWithTopAppBar(
                    titleText = "          Scanner BLE",
                    titleColor = topbartext,
                    backgroundColor = background
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        ScanComposant(
                            command = "Play",
                            backgroundColor = background,
                            progressIndicatorColor = background,
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
class ScanInteraction(private val context: Context) {
    var isScanning = mutableStateOf(false)
    var hasError = mutableStateOf(false)

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as? android.bluetooth.BluetoothManager
        bluetoothManager?.adapter
    }

    fun initialize() {
        if (bluetoothAdapter?.isEnabled != true) {
            hasError.value = true // Bluetooth n'est pas disponible ou désactivé
        }
    }


    fun verifyBluetoothStatus(): Boolean {
        return bluetoothAdapter?.isEnabled ?: false
    }

    fun requestPermissions(activity: ComponentActivity, onPermissionResult: (Boolean) -> Unit) {
        val permissionRequest = activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            onPermissionResult(permissions.all { it.value })
        }

        permissionRequest.launch(getAllPermissionsForBLE())
    }

    private fun getAllPermissionsForBLE(): Array<String> {
        var allPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            allPermissions += arrayOf(
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_ADMIN
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            allPermissions += Manifest.permission.ACCESS_BACKGROUND_LOCATION
        }
        return allPermissions
    }

    fun startScanWithPermissionCheck(activity: ComponentActivity) {
        requestPermissions(activity) { hasPermissions ->
            if (hasPermissions) {
                startScanning()
            } else {
                hasError.value = true // Handle permission denied
            }
        }
    }

    private fun startScanning() {
        if (verifyBluetoothStatus()) {
            // Commencer le scan Bluetooth ici
            isScanning.value = true
            // Implémentez votre logique de scan
        } else {
            hasError.value = true // Handle Bluetooth is turned off
        }
    }
}
