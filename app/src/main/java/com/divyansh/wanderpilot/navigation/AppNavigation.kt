package com.divyansh.wanderpilot.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.divyansh.wanderpilot.firestore.FirestoreRepository
import com.divyansh.wanderpilot.ui.components.BottomBar
import com.divyansh.wanderpilot.ui.destination.DestinationDetailsScreen
import com.divyansh.wanderpilot.ui.home.HomeScreen
import com.divyansh.wanderpilot.ui.login.FirebaseLoginScreen
import com.divyansh.wanderpilot.ui.planner.TripPlannerScreen
import com.divyansh.wanderpilot.ui.profile.ProfileScreen
import com.divyansh.wanderpilot.ui.saved.SavedTripsScreen
import com.divyansh.wanderpilot.ui.signup.SignupScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val repository = FirestoreRepository()

    val currentRoute =
        navController.currentBackStackEntryAsState().value
            ?.destination?.route

    Scaffold(

        bottomBar = {

            if (
                currentRoute == "home" ||
                currentRoute == "savedTrips" ||
                currentRoute == "profile"
            ) {

                BottomBar(

                    currentRoute = currentRoute ?: "home",

                    onHome = {

                        navController.navigate("home") {
                            launchSingleTop = true
                        }

                    },

                    onSaved = {

                        navController.navigate("savedTrips") {
                            launchSingleTop = true
                        }

                    },

                    onProfile = {

                        navController.navigate("profile") {
                            launchSingleTop = true
                        }

                    }

                )

            }

        }

    ) { padding ->

        NavHost(

            modifier = Modifier.padding(padding),

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

                    onLogoutClick = {

                        FirebaseAuth.getInstance().signOut()

                        navController.navigate("login") {

                            popUpTo(0) {
                                inclusive = true
                            }

                        }

                    }

                )

            }

            // ---------------- Saved Trips ----------------

            composable("savedTrips") {

                SavedTripsScreen()

            }

            // ---------------- Profile ----------------

            composable("profile") {

                ProfileScreen(

                    onLogoutClick = {

                        FirebaseAuth.getInstance().signOut()

                        navController.navigate("login") {

                            popUpTo(0) {
                                inclusive = true
                            }

                        }

                    }

                )

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
                    backStackEntry.arguments?.getString("city")
                        ?: "Goa"

                DestinationDetailsScreen(

                    city = city,

                    onPlanTripClick = {

                        navController.navigate("planner/$it")

                    }

                )

            }

            // ---------------- Trip Planner ----------------

            composable(

                route = "planner/{city}",

                arguments = listOf(

                    navArgument("city") {

                        type = NavType.StringType

                    }

                )

            ) { backStackEntry ->

                val city =
                    backStackEntry.arguments?.getString("city")
                        ?: "Goa"

                TripPlannerScreen(

                    destination = city,

                    onSaveTrip = {
                            destination,
                            startDate,
                            endDate,
                            travelers,
                            budget,
                            transport,
                            accommodation,
                            notes ->

                        repository.saveTripPlan(

                            destination = destination,

                            startDate = startDate,

                            endDate = endDate,

                            travelers = travelers,

                            budget = budget,

                            transport = transport,

                            accommodation = accommodation,

                            notes = notes

                        ) { success, message ->

                            if (success) {

                                navController.popBackStack()

                            } else {

                                println(message)

                            }

                        }

                    }

                )

            }

        }

    }

}