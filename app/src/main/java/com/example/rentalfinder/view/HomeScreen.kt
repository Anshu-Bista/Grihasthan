package com.example.rentalfinder.view

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import com.example.rentalfinder.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rentalfinder.repository.PropertyRepoImpl
import com.example.rentalfinder.ui.theme.ForestGreen
import com.example.rentalfinder.ui.theme.Gold
import com.example.rentalfinder.view.components.PropertyCard
import com.example.rentalfinder.viewmodel.PropertyViewModel

@Composable
fun HomeScreen(){
    val context = LocalContext.current
    val propertyViewModel = remember { PropertyViewModel(PropertyRepoImpl()) }

    val properties by propertyViewModel.allproperties.observeAsState(emptyList())

    //Fetch data when screen loads
    LaunchedEffect(Unit) {
        propertyViewModel.getAllProperties()
    }

    var search by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize()
    ) {
        item {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text(
                    text = "Welcome to Grihasthan",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = ForestGreen,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Your trusted rental marketplace",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.padding(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Box(
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.surfaceVariant,
                                        shape = CircleShape
                                    )
                                    .padding(14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_search_24),
                                    tint = Gold,
                                    contentDescription = null,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            Text("Browse")
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Box(
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.surfaceVariant,
                                        shape = CircleShape
                                    )
                                    .padding(14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_add_24),
                                    tint = Gold,
                                    contentDescription = null,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            Text("Add")
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Box(
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.surfaceVariant,
                                        shape = CircleShape

                                    )
                                    .padding(14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_person_24),
                                    tint = Gold,
                                    contentDescription = null,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            Text("Connect")
                        }
                    }
                }
            }
        }

        item{
            Text(
                text = "Featured Listings",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = ForestGreen,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 10.dp),
            )
        }

        // Empty State
        if (properties.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No properties available",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        items(properties) { property ->
            PropertyCard(
                property = property,
                onClick = { prop ->
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("propertyId", prop.propertyId)
                    context.startActivity(intent)
                },

                onEdit = { prop ->
                    val intent = Intent(context, AddActivity::class.java)
                    intent.putExtra("propertyId", prop.propertyId)
                    context.startActivity(intent)
                },

                onDelete = { prop ->
                    propertyViewModel.deleteProperty(prop.propertyId) { success, msg ->
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}
@Composable
fun FeatureRow(icon: Int, text: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.padding(end = 10.dp)
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}