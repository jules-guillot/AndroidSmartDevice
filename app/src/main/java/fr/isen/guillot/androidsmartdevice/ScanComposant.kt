package fr.isen.guillot.androidsmartdevice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomScaffoldWithTopAppBar(
    titleText: String,
    titleColor: Color,
    backgroundColor: Color,
    content: @Composable (PaddingValues) -> Unit // Cette lambda permet d'injecter du contenu dans le Scaffold
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
fun TogglePlayPause(command: String, backgroundColor: Color, progressIndicatorColor: Color) {
    // Remember state for toggle and if it should show the LinearProgressIndicator
    val firstPhotoVisible = remember { mutableStateOf(command == "Pause") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        // Determine the image resource based on the toggle state
        val imageResource = if (firstPhotoVisible.value) R.drawable.pause else R.drawable.play

        Image(
            painter = painterResource(id = imageResource),
            contentDescription = if (firstPhotoVisible.value) "Pause" else "Play",
            modifier = Modifier
                .offset(x = 0.dp, y = 0.dp)
                .padding(horizontal = 150.dp)
                .height(100.dp)
                .background(backgroundColor)
                .clickable {
                    // Toggle the state when image is clicked
                    firstPhotoVisible.value = !firstPhotoVisible.value
                }
        )

        // Show the LinearProgressIndicator when the state is "Pause"
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