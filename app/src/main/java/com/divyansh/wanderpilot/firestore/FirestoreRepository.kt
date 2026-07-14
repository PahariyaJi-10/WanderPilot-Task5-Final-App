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
            Log.e("Firestore", "Current user is null")
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

                Log.d("Firestore", "Trip Saved Successfully")

                onResult(true, "Trip Saved")
            }
            .addOnFailureListener { e ->

                Log.e("Firestore", e.message ?: "Firestore Error")

                onResult(false, e.message ?: "Firestore Error")
            }
    }

    // -----------------------------
    // Get Saved Trips
    // -----------------------------
    fun getSavedTrips(
        onResult: (List<String>) -> Unit
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

                val trips = mutableListOf<String>()

                for (document in documents) {

                    val destination =
                        document.getString("destination")

                    if (destination != null) {
                        trips.add(destination)
                    }
                }

                onResult(trips)
            }
            .addOnFailureListener {

                Log.e(
                    "Firestore",
                    it.message ?: "Error loading trips"
                )

                onResult(emptyList())
            }
    }
}