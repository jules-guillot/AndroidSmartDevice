package fr.isen.guillot.androidsmartdevice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.colorResource
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier

class ScanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val background = colorResource(id = R.color.bleu_fonce)
            val Play = "Play"
            val topbartext = colorResource(id = R.color.vert)
            MaterialTheme {
                CustomScaffoldWithTopAppBar(
                    titleText = "          Scanner BLE",
                    titleColor = topbartext,
                    backgroundColor = background
                ) { innerPadding ->
                    // Ici, vous pouvez ajouter le contenu de votre page en utilisant innerPadding pour respecter les paddings du Scaffold
                    // Exemple avec une simple liste:
                    Column(modifier = Modifier.padding(innerPadding)) {
                        TogglePlayPause(
                            Play,
                            background,
                            background
                        )
                    }
                }

                //Spacer(Modifier.height(40.dp))


            }
        }
    }
}
