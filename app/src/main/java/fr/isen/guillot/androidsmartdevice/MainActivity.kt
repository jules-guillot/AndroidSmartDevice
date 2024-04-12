package fr.isen.guillot.androidsmartdevice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.res.colorResource

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 39.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 25.sp,
        letterSpacing = 0.sp
    )
)
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
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
