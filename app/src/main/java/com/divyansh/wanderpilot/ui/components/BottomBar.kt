package com.divyansh.wanderpilot.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun BottomBar(
    currentRoute: String,
    onHome: () -> Unit,
    onSaved: () -> Unit,
    onProfile: () -> Unit
) {

    NavigationBar {

        NavigationBarItem(

            selected = currentRoute == "home",

            onClick = onHome,

            icon = {

                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home"
                )

            },

            label = {

                Text("Home")

            }

        )

        NavigationBarItem(

            selected = currentRoute == "savedTrips",

            onClick = onSaved,

            icon = {

                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Saved"
                )

            },

            label = {

                Text("Saved")

            }

        )

        NavigationBarItem(

            selected = currentRoute == "profile",

            onClick = onProfile,

            icon = {

                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile"
                )

            },

            label = {

                Text("Profile")

            }

        )

    }

}