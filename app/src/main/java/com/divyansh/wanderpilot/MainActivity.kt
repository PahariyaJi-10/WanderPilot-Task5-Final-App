package com.divyansh.wanderpilot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.divyansh.wanderpilot.ui.destination.DestinationDetailsScreen
import com.divyansh.wanderpilot.ui.home.HomeScreen
import com.divyansh.wanderpilot.ui.login.FirebaseLoginScreen
import com.divyansh.wanderpilot.ui.signup.SignupScreen
import com.divyansh.wanderpilot.ui.theme.WanderPilotTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            WanderPilotTheme {

                var currentScreen by remember {
                    mutableStateOf("login")
                }

                when (currentScreen) {

                    "login" -> {
                        FirebaseLoginScreen(
                            onLoginSuccess = {
                                currentScreen = "home"
                            },
                            onSignupClick = {
                                currentScreen = "signup"
                            }
                        )
                    }

                    "signup" -> {
                        SignupScreen(
                            onSignupSuccess = {
                                currentScreen = "home"
                            },
                            onLoginClick = {
                                currentScreen = "login"
                            }
                        )
                    }

                    "home" -> {
                        HomeScreen(
                            onExploreClick = {
                                currentScreen = "destination"
                            }
                        )
                    }

                    "destination" -> {
                        DestinationDetailsScreen()
                    }
                }
            }
        }
    }
}