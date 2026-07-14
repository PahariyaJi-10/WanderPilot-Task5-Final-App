package com.divyansh.wanderpilot.firestore

data class TripPlan(

    val id: String = "",

    val destination: String = "",

    val startDate: String = "",

    val endDate: String = "",

    val travelers: Int = 1,

    val budget: String = "",

    val transport: String = "",

    val accommodation: String = "",

    val notes: String = ""

)