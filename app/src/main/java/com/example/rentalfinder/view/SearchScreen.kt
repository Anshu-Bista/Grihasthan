package com.example.rentalfinder.view


import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rentalfinder.model.PropertyModel
import com.example.rentalfinder.repository.PropertyRepoImpl
import com.example.rentalfinder.ui.theme.ForestGreen
import com.example.rentalfinder.ui.theme.Gold
import com.example.rentalfinder.ui.theme.OffWhite
import com.example.rentalfinder.view.components.PropertyCard
import com.example.rentalfinder.viewmodel.PropertyViewModel
import com.example.rentalfinder.view.components.PropertyCard

@Composable
fun SearchScreen() {

    val context = LocalContext.current

    val repo = remember { PropertyRepoImpl() }
    val viewModel = remember { PropertyViewModel(repo) }

    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf(
        "All",
        "Apartment",
        "House",
        "Room",
        "Office"
    )

    // ⭐ Observe Lists
    val allProperties by viewModel.allproperties.observeAsState(emptyList())
    val categoryProperties by viewModel.categoryProperties.observeAsState(emptyList())

    // ⭐ Load All Properties Initially
    LaunchedEffect(Unit) {
        viewModel.getAllProperties()
    }

    // ⭐ Fetch category data when category changes
    LaunchedEffect(selectedCategory) {

        if (selectedCategory == "All") {
            viewModel.getAllProperties()
        } else {
            viewModel.getPropertiesByCategory(selectedCategory)
        }
    }

    // ⭐ Decide which list to show
    val displayList = if (selectedCategory == "All")
        allProperties
    else
        categoryProperties

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // ⭐ Title
        Text(
            "Categories",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = ForestGreen
        )

        Spacer(Modifier.height(12.dp))

        // ⭐ Category Cards
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(categories) { category ->

                val isSelected = selectedCategory == category

                Card(
                    modifier = Modifier.clickable {
                        selectedCategory = category
                    },
                    colors = CardDefaults.cardColors(
                        containerColor =
                            if (isSelected) Gold else OffWhite
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {

                    Text(
                        text = category,
                        modifier = Modifier.padding(
                            horizontal = 20.dp,
                            vertical = 12.dp
                        ),
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) OffWhite else ForestGreen
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // ⭐ Property List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            items(displayList) { property ->

                PropertyCard(
                    property = property,
                    onClick = {
                        val intent = Intent(
                            context,
                            DetailActivity::class.java
                        )
                        intent.putExtra("propertyId", property.propertyId)
                        context.startActivity(intent)
                    },
                    onEdit = {},
                    onDelete = {}
                )
            }
        }
    }
}