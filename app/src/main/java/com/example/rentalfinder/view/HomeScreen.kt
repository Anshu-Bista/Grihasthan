package com.example.rentalfinder.view

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rentalfinder.repository.PropertyRepoImpl
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
            .padding(10.dp)
            .fillMaxSize()
    ) {

        // Search Bar
        item {
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                placeholder = { Text("Search here |") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Location Row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_location_on_24),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 10.dp)
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text("Location", fontSize = 18.sp)
                    Text("St. no. 8, Karangi, Khi")
                }
            }
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
