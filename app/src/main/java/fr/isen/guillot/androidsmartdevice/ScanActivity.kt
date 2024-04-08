package fr.isen.guillot.androidsmartdevice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class ScanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(typography = AppTypography) {
                ScanScreen() // Affichez l'UI de scan ici
            }
        }
    }
}

@Composable
fun ScanScreen() {
    Text(text = "Interface de scan BLE")
}
