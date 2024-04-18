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

        setContent {
            val topbartext = colorResource(id = R.color.vert)
            val background = colorResource(id = R.color.bleu_fonce)
            val context = LocalContext.current
            val boutontext = colorResource(id = R.color.bleu_clair)
            MaterialTheme {
                CustomScaffoldWithTopAppBar(
                    titleText = "  Android Smart Device",
                    titleColor = topbartext,
                    backgroundColor = background
                ) { innerPadding ->
                    // Ici, vous pouvez ajouter le contenu de votre page en utilisant innerPadding pour respecter les paddings du Scaffold
                    // Exemple avec une simple liste:
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Image(painter = painterResource(id = R.drawable.icon), contentDescription = "Icone App")
                        Button(
                            onClick = {
                                val intent = Intent(context, ScanActivity::class.java)
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 115.dp)
                                .height(70.dp),
                            shape = MaterialTheme.shapes.medium,
                            colors = ButtonDefaults.buttonColors(background)
                        ) {
                            Text(
                                text = "Scanner",
                                color = boutontext,
                                style = MaterialTheme.typography.displaySmall,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
