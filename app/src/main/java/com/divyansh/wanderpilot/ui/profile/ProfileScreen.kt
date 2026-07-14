package com.divyansh.wanderpilot.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(
    onLogoutClick: () -> Unit
) {

    val user = FirebaseAuth.getInstance().currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier.size(90.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Profile",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = user?.email ?: "No User"
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onLogoutClick,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Logout")

        }

    }

}