package fr.isen.guillot.androidsmartdevice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
class ScanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val topbartext = colorResource(id = R.color.vert)
            val topbarbackgroundColor = colorResource(id = R.color.bleu_fonce)
            val topscannerBLE = colorResource(id = R.color.bleu_fonce_scan_activity)
            MaterialTheme(
                typography = AppTypography
            ) {
                Box(
                    modifier = Modifier
                        .width(500.dp)
                        .height(180.dp)
                        .background(topscannerBLE)
                )
                Box(
                    modifier = Modifier
                        .offset(x = 50.dp, y = 70.dp)
                        .padding(horizontal = 102.dp)
                        .height(100.dp)
                        .background(topscannerBLE),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.play),
                        contentDescription = "Icone play"
                    )
                    /*Image(
                        painter = painterResource(id = R.drawable.pause),
                        contentDescription = "Icone pause"
                    )*/
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

