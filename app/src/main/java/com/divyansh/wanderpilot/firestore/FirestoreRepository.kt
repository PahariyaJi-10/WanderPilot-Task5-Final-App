package com.divyansh.wanderpilot.firestore

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

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
                onResult(false, it.localizedMessage ?: "Error")
            }
    }
}