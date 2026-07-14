package com.divyansh.wanderpilot.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.divyansh.wanderpilot.ui.destination.DestinationDetailsScreen
import com.divyansh.wanderpilot.ui.home.HomeScreen
import com.divyansh.wanderpilot.ui.login.FirebaseLoginScreen
import com.divyansh.wanderpilot.ui.saved.SavedTripsScreen
import com.divyansh.wanderpilot.ui.signup.SignupScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // ---------------- Login ----------------

        composable("login") {

            FirebaseLoginScreen(

                onLoginSuccess = {

                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }

                },

                onSignupClick = {

                    navController.navigate("signup")

                }
            )
        }

        // ---------------- Signup ----------------

        composable("signup") {

            SignupScreen(

                onSignupSuccess = {

                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }

                },

                onLoginClick = {

                    navController.popBackStack()

                }
            )
        }

        // ---------------- Home ----------------

        composable("home") {

            HomeScreen(

                onExploreClick = { city ->

                    navController.navigate("destination/$city")

                },

                onSavedTripsClick = {

                    navController.navigate("savedTrips")

                },

                onLogoutClick = {

                    FirebaseAuth.getInstance().signOut()

                    navController.navigate("login") {

                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        }

        // ---------------- Saved Trips ----------------

        composable("savedTrips") {

            SavedTripsScreen()

        }

        // ---------------- Destination ----------------

        composable(
            route = "destination/{city}",
            arguments = listOf(
                navArgument("city") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val city =
                backStackEntry.arguments?.getString("city") ?: "Goa"

            DestinationDetailsScreen(
                city = city
            )
        }
    }
}