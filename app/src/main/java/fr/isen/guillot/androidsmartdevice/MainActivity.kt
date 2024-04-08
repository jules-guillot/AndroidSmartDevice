package fr.isen.guillot.androidsmartdevice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val backgroundColor = colorResource(id = R.color.bleu_cyan)
            MaterialTheme(
                typography = AppTypography
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = backgroundColor)
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val topbarbackgroundColor = colorResource(id = R.color.bleu_fonce)
    val boutontext = colorResource(id = R.color.bleu_clair)
    val topbartext = colorResource(id = R.color.vert)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Android Smart Device",
                        color = topbartext,
                        style = MaterialTheme.typography.displayLarge

                    ) },
                    colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topbarbackgroundColor
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.icon), contentDescription = "Icone App")
            Spacer(modifier = Modifier.height(90.dp))
            Button(
                onClick = {
                    val intent = Intent(context, ScanActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
                    .height(70.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(topbarbackgroundColor)
            ) {
                Text(
                    text = "Scanner les appareils bluetooth à proximité",
                    color = boutontext,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }
    }
}
