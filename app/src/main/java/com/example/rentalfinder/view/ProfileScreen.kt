package com.example.rentalfinder.view

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rentalfinder.repository.UserRepoImpl
import com.example.rentalfinder.ui.theme.ForestGreen
import com.example.rentalfinder.ui.theme.Gold
import com.example.rentalfinder.ui.theme.OffWhite
import com.example.rentalfinder.viewmodel.UserViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.example.rentalfinder.view.components.FormField

@Composable
fun ProfileScreen() {

    val context = LocalContext.current

    val repo = remember { UserRepoImpl() }
    val viewModel = remember { UserViewModel(repo) }

    val userId = com.google.firebase.auth.FirebaseAuth
        .getInstance()
        .currentUser?.uid ?: ""

    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            viewModel.getUserById(userId)
        }
    }

    val user by viewModel.users.observeAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        if (user == null) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Loading profile...", color = ForestGreen)
                }
            }
        }

        user?.let { u ->

            // ⭐ Profile Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .background(
                                Gold,
                                RoundedCornerShape(100.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        val firstChar =
                            u.email.firstOrNull()?.uppercaseChar()?.toString() ?: "U"

                        Text(
                            text = firstChar,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = OffWhite
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = u.email,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreen
                    )
                }
            }

            // ⭐ Profile Info Section
            item {

                Column(
                    modifier = Modifier.padding(30.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        "Profile Information",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = ForestGreen
                    )

                    FormField(
                        label = "Email",
                        value = u.email,
                        onValueChange = {},
                        placeholder = "",
                    )

                    FormField(
                        label = "Gender",
                        value = u.gender.ifEmpty { "Not provided" },
                        onValueChange = {},
                    )

                    FormField(
                        label = "Date of Birth",
                        value = u.dob.ifEmpty { "Not provided" },
                        onValueChange = {},
                    )
                }
            }

            // ⭐ Logout Button
            item {

                Button(
                    onClick = {
                        com.google.firebase.auth.FirebaseAuth
                            .getInstance()
                            .signOut()

                        context.startActivity(
                            Intent(context, LoginActivity::class.java)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Gold,
                        contentColor = OffWhite
                    )
                ) {
                    Text("Logout")
                }
            }
        }
    }
}