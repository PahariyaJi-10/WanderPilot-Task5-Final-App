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
import com.divyansh.wanderpilot.firestore.Trip

@Composable
fun SavedTripsScreen() {

    val repository = remember { FirestoreRepository() }

    var trips by remember {
        mutableStateOf<List<Trip>>(emptyList())
    }

    fun loadTrips() {
        repository.getSavedTrips {
            trips = it
        }
    }

    LaunchedEffect(Unit) {
        loadTrips()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "❤️ Saved Trips",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (trips.isEmpty()) {

            Text(
                text = "No Saved Trips Yet."
            )

        } else {

            LazyColumn {

                items(trips) { trip ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null
                            )

                            Spacer(
                                modifier = Modifier.width(12.dp)
                            )

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = trip.destination,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    text = "Saved Destination",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            IconButton(
                                onClick = {

                                    repository.deleteTrip(trip.id) { success ->

                                        if (success) {
                                            loadTrips()
                                        }

                                    }

                                }
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Trip"
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}