package com.divyansh.wanderpilot.ui.saved

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.divyansh.wanderpilot.firestore.FirestoreRepository
import com.divyansh.wanderpilot.firestore.TripPlan

@Composable
fun SavedTripsScreen() {

    val repository = remember { FirestoreRepository() }

    var tripPlans by remember {
        mutableStateOf<List<TripPlan>>(emptyList())
    }

    fun loadTripPlans() {

        repository.getTripPlans {

            tripPlans = it

        }

    }

    LaunchedEffect(Unit) {

        loadTripPlans()

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "❤️ My Trip Plans",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (tripPlans.isEmpty()) {

            Text(
                text = "No Trip Plans Yet."
            )

        } else {

            LazyColumn {

                items(tripPlans) { trip ->

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

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null
                                )

                                Spacer(
                                    modifier = Modifier.width(12.dp)
                                )

                                Text(
                                    text = trip.destination,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )

                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text("📅 ${trip.startDate} → ${trip.endDate}")

                            Spacer(modifier = Modifier.height(4.dp))

                            Text("👥 Travelers: ${trip.travelers}")

                            Spacer(modifier = Modifier.height(4.dp))

                            Text("💰 Budget: ${trip.budget}")

                            Spacer(modifier = Modifier.height(4.dp))

                            Text("🚆 Transport: ${trip.transport}")

                            Spacer(modifier = Modifier.height(4.dp))

                            Text("🏨 Accommodation: ${trip.accommodation}")

                            Spacer(modifier = Modifier.height(4.dp))

                            Text("📝 Notes:")

                            Text(
                                text = trip.notes,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {

                                    repository.deleteTripPlan(
                                        trip.id
                                    ) { success ->

                                        if (success) {

                                            loadTripPlans()

                                        }

                                    }

                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null
                                )

                                Spacer(
                                    modifier = Modifier.width(8.dp)
                                )

                                Text("Delete Trip")

                            }

                        }

                    }

                }

            }

        }

    }

}