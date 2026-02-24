package com.example.rentalfinder.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.rentalfinder.repository.PropertyRepoImpl
import com.example.rentalfinder.ui.theme.Gold
import com.example.rentalfinder.ui.theme.MintGreen
import com.example.rentalfinder.view.ui.theme.RentalFinderTheme
import com.example.rentalfinder.viewmodel.PropertyViewModel
import coil3.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.request.crossfade
import com.example.rentalfinder.R
import com.example.rentalfinder.ui.theme.ForestGreen
import com.example.rentalfinder.ui.theme.OffWhite

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val propertyId = intent.getStringExtra("propertyId")?:""

        enableEdgeToEdge()
        setContent {
            DetailBody(propertyId)
        }
    }
}

@Composable
fun DetailBody(propertyId: String) {

    val viewModel = remember {
        PropertyViewModel(PropertyRepoImpl())
    }

    val property by viewModel.properties.observeAsState()

    // Fetch property once
    LaunchedEffect(propertyId) {
        Log.d("DETAIL_PAGE", "PropertyID = $propertyId")
        viewModel.getPropertyById(propertyId)
    }

    Scaffold(
        containerColor = MintGreen
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            if (property == null) {

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else {
                val p = property!!
                // IMAGE
                item {
                    val context = LocalContext.current

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                    ) {

                        // ⭐ PROPERTY IMAGE
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(p.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Property Image",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            error = painterResource(R.drawable.property),
                        )

                        // ⭐ BACK BUTTON (Top Left)
                        IconButton(
                            onClick = {
                                context.startActivity(
                                    Intent(context, DashboardActivity::class.java)
                                )
                            },
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                                contentDescription = "Menu", tint = ForestGreen
                            )
                        }
                    }
                }
                // Title
                item {
                    Text(
                        text = p.title,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                // Price
                item {
                    Text(
                        text = "Rs. ${p.price} / month",
                        color = Gold,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                // Description
                item {
                    Text(
                        text = p.description,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 15.sp
                    )
                }

                // Key Info
                item {

                    Column (modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            "Key Information",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(10.dp))

                        Text("City : ${p.city}")
                        Text("Area : ${p.area}")
                        Text("Street : ${p.streetAddress}")
                        Text("Zip Code : ${p.zipCode}")
                        Text("Year Built : ${p.yearBuilt}")
                        Text("Levels : ${p.levels}")
                        Text("Tenant Type : ${p.tenantType}")
                        Text("Lease Type : ${p.leaseType}")
                        Text("Furniture : ${p.furnitureType}")
                    }
                }

                // Amenities
                if (p.amenities.isNotEmpty()) {

                    item {
                        Text(
                            "Amenities",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    items(p.amenities) { amenity ->
                        Text(
                            text = "• $amenity",
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                        )
                    }
                }

            }

            // Loading / Empty State
            if (property == null) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
