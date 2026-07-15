package com.divyansh.wanderpilot.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.divyansh.wanderpilot.firestore.FirestoreRepository
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(
    onExploreClick: (String) -> Unit,
    onLogoutClick: () -> Unit
) {

    var searchText by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val firestoreRepository = remember { FirestoreRepository() }
    val currentUser = FirebaseAuth.getInstance().currentUser

    val destinations = listOf(
        "Goa",
        "Manali",
        "Jaipur",
        "Kerala",
        "Mumbai"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {

            Text(
                text = "WanderPilot",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Explore the World 🌍",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = currentUser?.email ?: "No User Logged In",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onLogoutClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Search Any Destination",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Enter City Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {

                    if (searchText.isNotBlank()) {

                        onExploreClick(searchText.trim())

                    } else {

                        message = "Please enter a city name."

                    }

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "⭐ Popular Destinations",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (message.isNotEmpty()) {

                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

            }

        }

        items(destinations) { destination ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = destination,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = when (destination) {

                            "Goa" ->
                                "Beautiful beaches and vibrant nightlife."

                            "Manali" ->
                                "Snow-covered mountains and adventure sports."

                            "Jaipur" ->
                                "Royal heritage, forts and palaces."

                            "Kerala" ->
                                "Backwaters, greenery and relaxation."

                            else ->
                                "City life, beaches and entertainment."

                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {

                            onExploreClick(destination)

                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Explore")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = {

                            message = "Saving..."

                            firestoreRepository.saveTrip(
                                destination
                            ) { _, result ->

                                message = result

                            }

                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save Trip")
                    }

                }

            }

        }

    }

}