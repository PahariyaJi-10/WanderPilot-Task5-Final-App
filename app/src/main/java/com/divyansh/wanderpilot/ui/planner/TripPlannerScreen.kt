package com.divyansh.wanderpilot.ui.planner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TripPlannerScreen(

    destination: String,

    onSaveTrip: (
        String,
        String,
        String,
        Int,
        String,
        String,
        String,
        String
    ) -> Unit

) {

    var startDate by remember {

        mutableStateOf("")

    }

    var endDate by remember {

        mutableStateOf("")

    }

    var travelers by remember {

        mutableStateOf("1")

    }

    var budget by remember {

        mutableStateOf("")

    }

    var transport by remember {

        mutableStateOf("Flight")

    }

    var accommodation by remember {

        mutableStateOf("Hotel")

    }

    var notes by remember {

        mutableStateOf("")

    }

    Column(

        modifier = Modifier

            .fillMaxSize()

            .verticalScroll(
                rememberScrollState()
            )

            .padding(20.dp)

    ) {

        Text(

            text = "🗺 Trip Planner",

            fontSize = 30.sp,

            fontWeight = FontWeight.Bold

        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(

            value = destination,

            onValueChange = {},

            enabled = false,

            label = {

                Text("Destination")

            },

            modifier = Modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(

            value = startDate,

            onValueChange = {

                startDate = it

            },

            label = {

                Text("Start Date")

            },

            placeholder = {

                Text("20 Jul 2026")

            },

            modifier = Modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(

            value = endDate,

            onValueChange = {

                endDate = it

            },

            label = {

                Text("End Date")

            },

            placeholder = {

                Text("25 Jul 2026")

            },

            modifier = Modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(

            value = travelers,

            onValueChange = {

                travelers = it

            },

            label = {

                Text("Number of Travelers")

            },

            modifier = Modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(

            value = budget,

            onValueChange = {

                budget = it

            },

            label = {

                Text("Budget (₹)")

            },

            modifier = Modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(

            "Transport",

            fontWeight = FontWeight.Bold

        )

        listOf(
            "Flight",
            "Train",
            "Bus",
            "Car"
        ).forEach {

            Row {

                RadioButton(

                    selected = transport == it,

                    onClick = {

                        transport = it

                    }

                )

                Text(it)

            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(

            "Accommodation",

            fontWeight = FontWeight.Bold

        )

        listOf(
            "Hotel",
            "Hostel",
            "Resort",
            "Homestay"
        ).forEach {

            Row {

                RadioButton(

                    selected = accommodation == it,

                    onClick = {

                        accommodation = it

                    }

                )

                Text(it)

            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(

            value = notes,

            onValueChange = {

                notes = it

            },

            label = {

                Text("Notes")

            },

            modifier = Modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(

            modifier = Modifier.fillMaxWidth(),

            onClick = {

                onSaveTrip(

                    destination,

                    startDate,

                    endDate,

                    travelers.toIntOrNull() ?: 1,

                    budget,

                    transport,

                    accommodation,

                    notes

                )

            }

        ) {

            Text(

                "Save Trip"

            )

        }

    }

}