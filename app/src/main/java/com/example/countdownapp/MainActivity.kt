package com.example.countdownapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.countdownapp.ui.navigation.CountDownNavHost
import com.example.countdownapp.ui.theme.CountdownAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountdownAppTheme {
                CountDownNavHost()
            }
        }
    }
}