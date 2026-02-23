package com.example.rentalfinder.view

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
        item {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp,)
            ) {

                Text(
                    text = "🏡 Welcome to Grihasthan",
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Your trusted rental marketplace",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.padding(12.dp))

                FeatureRow(
                    icon = R.drawable.baseline_search_24,
                    text = "Search verified rental properties easily"
                )

                FeatureRow(
                    icon = R.drawable.baseline_home_24,
                    text = "List your property and reach renters"
                )

                FeatureRow(
                    icon = R.drawable.baseline_person_24,
                    text = "Connect renters and property owners directly"
                )
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