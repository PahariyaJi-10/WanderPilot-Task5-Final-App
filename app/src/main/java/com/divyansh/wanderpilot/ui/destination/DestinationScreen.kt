package com.divyansh.wanderpilot.ui.destination

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.divyansh.wanderpilot.viewmodel.WeatherViewModel

@Composable
fun DestinationDetailsScreen(
    city: String,
    onPlanTripClick: (String) -> Unit
) {

    val context = LocalContext.current

    val weatherViewModel: WeatherViewModel = viewModel()

    val temperature by weatherViewModel.temperature.collectAsState()
    val location by weatherViewModel.location.collectAsState()

    LaunchedEffect(city) {
        weatherViewModel.getWeather(city)
    }

    // -----------------------------
    // Best Time to Visit
    // -----------------------------

    val bestTime = when (city.trim().lowercase()) {

        "goa" -> "November - February"

        "manali" -> "October - June"

        "jaipur" -> "October - March"

        "kerala" -> "September - March"

        "mumbai" -> "October - February"

        else -> "Depends on local climate and season"

    }

    // -----------------------------
    // Estimated Budget
    // -----------------------------

    val budget = when (city.trim().lowercase()) {

        "goa" -> "₹12,000 - ₹25,000"

        "manali" -> "₹10,000 - ₹20,000"

        "jaipur" -> "₹8,000 - ₹18,000"

        "kerala" -> "₹15,000 - ₹30,000"

        "mumbai" -> "₹10,000 - ₹22,000"

        else -> "Varies based on your travel preferences"

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "📍 ${if (location.isNotEmpty()) location else city}",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Discover beautiful places, explore local attractions, and plan your next journey with WanderPilot."
        )

        Spacer(modifier = Modifier.height(20.dp))

        // -----------------------------
        // Weather
        // -----------------------------

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Current Weather",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = temperature,
                    fontSize = 22.sp
                )

            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        // -----------------------------
        // Best Time
        // -----------------------------

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Best Time to Visit",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(bestTime)

            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        // -----------------------------
        // Budget
        // -----------------------------

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Estimated Budget",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(budget)

            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        // -----------------------------
        // Travel Tips
        // -----------------------------

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Travel Tips",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("• Check the latest weather forecast before travelling.")
                Text("• Carry valid ID and travel documents.")
                Text("• Book hotels and transport in advance.")
                Text("• Explore local food, culture and attractions.")
                Text("• Keep emergency contacts handy.")

            }

        }

        Spacer(modifier = Modifier.height(32.dp))

        // -----------------------------
        // Plan Trip
        // -----------------------------

        Button(
            onClick = {
                onPlanTripClick(city)
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("📅 Plan Trip")

        }

        Spacer(modifier = Modifier.height(12.dp))

        // -----------------------------
        // Google Maps
        // -----------------------------

        OutlinedButton(
            onClick = {

                val gmmIntentUri =
                    Uri.parse("geo:0,0?q=$city")

                val mapIntent =
                    Intent(Intent.ACTION_VIEW, gmmIntentUri)

                mapIntent.setPackage("com.google.android.apps.maps")

                if (mapIntent.resolveActivity(context.packageManager) != null) {

                    context.startActivity(mapIntent)

                } else {

                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                            "https://www.google.com/maps/search/?api=1&query=$city"
                        )
                    )

                    context.startActivity(browserIntent)

                }

            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("🗺 Open in Google Maps")

        }

    }

}