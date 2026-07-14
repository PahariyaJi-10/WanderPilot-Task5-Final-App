package com.divyansh.wanderpilot.firestore

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // -----------------------------
    // Save Trip
    // -----------------------------
    fun saveTrip(
        destination: String,
        onResult: (Boolean, String) -> Unit
    ) {

        val user = auth.currentUser

        if (user == null) {
            onResult(false, "User not logged in")
            return
        }

        val trip = hashMapOf(
            "destination" to destination,
            "timestamp" to System.currentTimeMillis()
        )

        firestore.collection("users")
            .document(user.uid)
            .collection("savedTrips")
            .add(trip)
            .addOnSuccessListener {

                onResult(true, "Trip Saved")

            }
            .addOnFailureListener {

                onResult(false, it.message ?: "Firestore Error")

            }
    }

    // -----------------------------
    // Get Saved Trips
    // -----------------------------
    fun getSavedTrips(
        onResult: (List<Trip>) -> Unit
    ) {

        val user = auth.currentUser

        if (user == null) {
            onResult(emptyList())
            return
        }

        firestore.collection("users")
            .document(user.uid)
            .collection("savedTrips")
            .get()
            .addOnSuccessListener { documents ->

                val trips = mutableListOf<Trip>()

                for (document in documents) {

                    val destination =
                        document.getString("destination") ?: ""

                    trips.add(

                        Trip(
                            id = document.id,
                            destination = destination
                        )

                    )
                }

                onResult(trips)

            }
            .addOnFailureListener {

                Log.e(
                    "Firestore",
                    it.message ?: "Error"
                )

                onResult(emptyList())

            }
    }

    // -----------------------------
    // Delete Trip
    // -----------------------------
    fun deleteTrip(
        tripId: String,
        onResult: (Boolean) -> Unit
    ) {

        val user = auth.currentUser

        if (user == null) {

            onResult(false)
            return

        }

        firestore.collection("users")
            .document(user.uid)
            .collection("savedTrips")
            .document(tripId)
            .delete()
            .addOnSuccessListener {

                onResult(true)

            }
            .addOnFailureListener {

                onResult(false)

            }
    }
}