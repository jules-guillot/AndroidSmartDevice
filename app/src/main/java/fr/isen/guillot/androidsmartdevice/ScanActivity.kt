package fr.isen.guillot.androidsmartdevice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.colorResource
import androidx.compose.foundation.layout.padding
import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat

//Champ
data class Device(
    val name: String,
    val address: String,
    val rssi: Int
)

class ScanActivity : ComponentActivity() {
    private var isFirstToggle: MutableState<Boolean>? = null

    //Champ
    private lateinit var scanInteraction: ScanInteraction
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions: Map<String, Boolean> ->
            if(permissions.entries.all { it.value } ) {
                scanLeDevice(scanInteraction.isScanning)
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val background = colorResource(id = R.color.bleu_fonce)
            val topbartext = colorResource(id = R.color.vert)
            isFirstToggle = remember { mutableStateOf(true) }

            //Champ
            var scanningState by remember { mutableStateOf(false) }
            scanInteraction = ScanInteraction(
                isScanning = scanningState,
                devices = remember { mutableStateListOf() },
                playAction = {
                    scanningState = !scanningState
                    scanLeDeviceWithPermission(scanningState)

                },
            ) {}

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
                            isFirstToggle = isFirstToggle!!,
                            scanInteraction = scanInteraction
                        )
                        if (!isFirstToggle!!.value) {
                            ScanDevice(scanInteraction = scanInteraction)
                        }
                    }
                }
            }
        }
    }
    //Champ
    private val leScanCallback = object : ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            Log.w(this@ScanActivity.localClassName, "${result.device}")
            if(!result.device.name.isNullOrEmpty())
                addDeviceToList(result)
        }
    }

    private val permissionsList = getAllPermissionsForBLE()

    private fun isAllPermissionGranted() = permissionsList.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(permissionsList)
    }


    private fun addDeviceToList(result: ScanResult) {
        val index = scanInteraction.devices.indexOfFirst { it.device.address == result.device.address }
        if (index != -1) {
            scanInteraction.devices[index] = result
        } else {
            scanInteraction.devices.add(result)
        }
    }

    private fun scanLeDeviceWithPermission(enable: Boolean) {
        if (isAllPermissionGranted()) {
            scanLeDevice(enable)
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun scanLeDevice(enable: Boolean) {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        bluetoothAdapter?.bluetoothLeScanner?.apply {
            if (enable) {
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    scanInteraction.isScanning = false
                    stopScan(leScanCallback)
                }, SCAN_PERIOD)
                scanInteraction.isScanning = true
                startScan(leScanCallback)
            } else {
                scanInteraction.isScanning = false
                stopScan(leScanCallback)
            }
        }
    }

    private fun getAllPermissionsForBLE(): Array<String> {
        var allPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_ADMIN
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            allPermissions = allPermissions.plus(
                arrayOf(
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_ADMIN
                )
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            allPermissions = allPermissions.plus(
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            )
        }
        return allPermissions
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val REQUEST_ENABLE_BT = 1001
        private const val PERMISSION_REQUEST_CODE = 1002
        const val DEVICE_PARAM: String = "device"
        private const val SCAN_PERIOD: Long = 10000
    }
}