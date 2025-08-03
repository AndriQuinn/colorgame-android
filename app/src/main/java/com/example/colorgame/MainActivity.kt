package com.example.colorgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.colorgame.ui.screen.home.HomeScreen
import com.example.colorgame.ui.theme.ColorGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ColorGameTheme {
                ColorGameApp()
            }
        }
    }
}

@Composable
fun ColorGameApp(modifier: Modifier = Modifier) {
    HomeScreen()
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "Color game preview")
@Composable
fun GreetingPreview() {
    ColorGameTheme {
        ColorGameApp()
    }
}