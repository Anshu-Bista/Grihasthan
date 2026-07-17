package com.example.rentalfinder.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.rentalfinder.repository.CommonRepoImpl
import com.example.rentalfinder.repository.UserRepoImpl
import com.example.rentalfinder.viewmodel.UserViewModel
import com.example.rentalfinder.utils.ImageUtils
import com.example.rentalfinder.ui.theme.*
import com.example.rentalfinder.R
import com.example.rentalfinder.view.components.FormField
import com.example.rentalfinder.viewmodel.CommonViewModel

@Composable
fun ProfileScreen() {

    val context = LocalContext.current

    val repo = remember { UserRepoImpl() }
    val viewModel = remember { UserViewModel(repo) }

    val commonRepo = remember { CommonRepoImpl() }
    val commonViewModel = remember { CommonViewModel(commonRepo) }

    val userId = com.google.firebase.auth.FirebaseAuth
        .getInstance()
        .currentUser?.uid ?: ""

    // ⭐ Image picker
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val activity = context as ComponentActivity

    val imageUtils = remember {
        ImageUtils(activity, activity)
    }

    LaunchedEffect(Unit) {
        imageUtils.registerLaunchers {
            selectedImageUri = it
            Log.d("PROFILE_IMG", "Selected Image URI: $it")
        }
    }

    // ⭐ Profile fields
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var dob by remember { mutableStateOf("") }

    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            viewModel.getUserById(userId)
        }
    }

    val user by viewModel.users.observeAsState()

    LaunchedEffect(user) {
        user?.let {
            firstName = it.firstName
            lastName = it.lastName
            email = it.email
            gender = it.gender.ifEmpty { "Male" }
            dob = it.dob
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        user?.let { u ->

            // ⭐ Header
            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(Gold, RoundedCornerShape(100.dp))
                            .clickable { imageUtils.launchImagePicker() },
                        contentAlignment = Alignment.Center
                    ) {

                        val imageModel =
                            when {
                                selectedImageUri != null -> selectedImageUri
                                u.imageUrl.isNotEmpty() -> u.imageUrl
                                else -> null
                            }

                        AsyncImage(
                            model = imageModel,
                            contentDescription = "Profile Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            error = painterResource(R.drawable.baseline_person_24),
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = email,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreen
                    )
                }
            }

            // ⭐ Profile Form
            item {

                Column(
                    modifier = Modifier.padding(30.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        "Profile Information",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreen
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        FormField(
                            label = "First Name",
                            value = firstName,
                            onValueChange = { firstName = it },
                            modifier = Modifier.weight(1f)
                        )

                        FormField(
                            label = "Last Name",
                            value = lastName,
                            onValueChange = { lastName = it },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    FormField(
                        label = "Email",
                        value = email,
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("Gender", fontWeight = FontWeight.Bold)

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        listOf("Male", "Female", "Other").forEach { g ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = gender == g,
                                    onClick = { gender = g }
                                )
                                Text(g)
                            }
                        }
                    }

                    Text("Date of Birth", fontWeight = FontWeight.Bold)

                    Button(
                        onClick = {
                            val picker = android.app.DatePickerDialog(context)

                            picker.setOnDateSetListener { _, y, m, d ->
                                dob = "$d/${m + 1}/$y"
                            }

                            picker.show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Gold)
                    ) {
                        Text(if (dob.isEmpty()) "Select DOB" else dob)
                    }

                    // ⭐ Save Button
                    Button(
                        onClick = {

                            fun saveProfile(imageUrl: String){

                                val updatedUser = u.copy(
                                    firstName = firstName,
                                    lastName = lastName,
                                    email = email,
                                    gender = gender,
                                    dob = dob,
                                    imageUrl = imageUrl
                                )

                                viewModel.editProfile(updatedUser){ success, msg ->
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    if(success){
                                        // ⭐ Refresh UI data
                                        viewModel.getUserById(userId)
                                    }
                                }
                            }

                            // ⭐ Image upload using CommonViewModel (NOT ImageUtils)
                            if(selectedImageUri != null){

                                commonViewModel.uploadImage(context, selectedImageUri!!){ uploadedUrl ->

                                    if(uploadedUrl != null){
                                        saveProfile(uploadedUrl)
                                    }else{
                                        Toast.makeText(context, "Image upload failed", Toast.LENGTH_SHORT).show()
                                    }

                                }

                            }else{
                                // ⭐ Keep existing image
                                saveProfile(u.imageUrl)
                            }

                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Gold)
                    ){
                        Text("Save Profile")
                    }
                }
            }

            // ⭐ Logout
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
                    colors = ButtonDefaults.buttonColors(containerColor = Gold)
                ) {
                    Text("Logout")
                }
            }
        }
    }
}