package com.divyansh.wanderpilot.firestore

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // -------------------------------------------------
    // Save Simple Trip
    // -------------------------------------------------
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

                Log.d("Firestore", "Simple Trip Saved")

                onResult(true, "Trip Saved")

            }
            .addOnFailureListener { e ->

                Log.e("Firestore", "Save Trip Failed", e)

                onResult(false, e.message ?: "Firestore Error")

            }
    }

    // -------------------------------------------------
    // Save Complete Trip Plan
    // -------------------------------------------------
    fun saveTripPlan(

        destination: String,

        startDate: String,

        endDate: String,

        travelers: Int,

        budget: String,

        transport: String,

        accommodation: String,

        notes: String,

        onResult: (Boolean, String) -> Unit

    ) {

        val user = auth.currentUser

        if (user == null) {

            Log.e("TripPlan", "User is null")

            onResult(false, "User not logged in")

            return

        }

        val tripPlan = hashMapOf(

            "destination" to destination,

            "startDate" to startDate,

            "endDate" to endDate,

            "travelers" to travelers,

            "budget" to budget,

            "transport" to transport,

            "accommodation" to accommodation,

            "notes" to notes,

            "timestamp" to System.currentTimeMillis()

        )

        firestore.collection("users")
            .document(user.uid)
            .collection("tripPlans")
            .add(tripPlan)
            .addOnSuccessListener {

                Log.d(
                    "TripPlan",
                    "Trip Plan Saved Successfully"
                )

                onResult(
                    true,
                    "Trip Planned Successfully"
                )

            }
            .addOnFailureListener { e ->

                Log.e(
                    "TripPlan",
                    "Trip Plan Save Failed",
                    e
                )

                onResult(
                    false,
                    e.message ?: "Firestore Error"
                )

            }

    }

    // -------------------------------------------------
    // Get Saved Trips
    // -------------------------------------------------
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

                    trips.add(

                        Trip(

                            id = document.id,

                            destination =
                                document.getString("destination")
                                    ?: ""

                        )

                    )

                }

                Log.d(
                    "Firestore",
                    "Saved Trips Loaded : ${trips.size}"
                )

                onResult(trips)

            }
            .addOnFailureListener { e ->

                Log.e(
                    "Firestore",
                    "Failed to load saved trips",
                    e
                )

                onResult(emptyList())

            }

    }

    // -------------------------------------------------
    // Delete Saved Trip
    // -------------------------------------------------
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

                Log.d(
                    "Firestore",
                    "Saved Trip Deleted"
                )

                onResult(true)

            }
            .addOnFailureListener { e ->

                Log.e(
                    "Firestore",
                    "Delete Failed",
                    e
                )

                onResult(false)

            }

    }

    // -------------------------------------------------
    // Get Complete Trip Plans
    // -------------------------------------------------
    fun getTripPlans(
        onResult: (List<TripPlan>) -> Unit
    ) {

        val user = auth.currentUser

        if (user == null) {

            onResult(emptyList())
            return

        }

        firestore.collection("users")
            .document(user.uid)
            .collection("tripPlans")
            .get()
            .addOnSuccessListener { documents ->

                val plans = mutableListOf<TripPlan>()

                for (document in documents) {

                    plans.add(

                        TripPlan(

                            id = document.id,

                            destination =
                                document.getString("destination") ?: "",

                            startDate =
                                document.getString("startDate") ?: "",

                            endDate =
                                document.getString("endDate") ?: "",

                            travelers =
                                document.getLong("travelers")
                                    ?.toInt() ?: 1,

                            budget =
                                document.getString("budget") ?: "",

                            transport =
                                document.getString("transport") ?: "",

                            accommodation =
                                document.getString("accommodation") ?: "",

                            notes =
                                document.getString("notes") ?: "",

                            timestamp =
                                document.getLong("timestamp") ?: 0L

                        )

                    )

                }

                Log.d(
                    "TripPlan",
                    "Loaded ${plans.size} Trip Plans"
                )

                onResult(plans)

            }
            .addOnFailureListener { e ->

                Log.e(
                    "TripPlan",
                    "Failed to Load Trip Plans",
                    e
                )

                onResult(emptyList())

            }

    }

    // -------------------------------------------------
    // Delete Trip Plan
    // -------------------------------------------------
    fun deleteTripPlan(
        tripPlanId: String,
        onResult: (Boolean) -> Unit
    ) {

        val user = auth.currentUser

        if (user == null) {

            onResult(false)
            return

        }

        firestore.collection("users")
            .document(user.uid)
            .collection("tripPlans")
            .document(tripPlanId)
            .delete()
            .addOnSuccessListener {

                Log.d(
                    "TripPlan",
                    "Trip Plan Deleted"
                )

                onResult(true)

            }
            .addOnFailureListener { e ->

                Log.e(
                    "TripPlan",
                    "Delete Failed",
                    e
                )

                onResult(false)

            }

    }

}