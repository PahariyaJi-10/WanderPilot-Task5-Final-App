package com.divyansh.wanderpilot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.divyansh.wanderpilot.navigation.AppNavigation
import com.divyansh.wanderpilot.ui.theme.WanderPilotTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WanderPilotTheme {
                AppNavigation()
            }
        }
    }
}
