package com.divyansh.wanderpilot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.divyansh.wanderpilot.ui.destination.DestinationDetailsScreen
import com.divyansh.wanderpilot.ui.home.HomeScreen
import com.divyansh.wanderpilot.ui.theme.WanderPilotTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WanderPilotTheme {

                var showDetails by remember {
                    mutableStateOf(false)
                }

                if (showDetails) {
                    DestinationDetailsScreen()
                } else {
                    HomeScreen(
                        onExploreClick = {
                            showDetails = true
                        }
                    )
                }
            }
        }
    }
}