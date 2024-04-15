package fr.isen.guillot.androidsmartdevice

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList

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

@Composable
fun TogglePlayPause(
    command: String,
    backgroundColor: Color,
    progressIndicatorColor: Color,
    devices: SnapshotStateList<BluetoothDevice>,
    isFirstToggle: MutableState<Boolean>
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
                        isFirstToggle.value = false  // Mettre à false après le premier clic
                        // Logique supplémentaire si nécessaire
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