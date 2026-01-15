package com.example.rentalfinder.view


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.rentalfinder.repository.PropertyRepoImpl
import com.example.rentalfinder.viewmodel.PropertyViewModel

@Composable
fun SearchScreen(){
    val propertyViewModel = remember { PropertyViewModel(PropertyRepoImpl())}

    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var totalArea by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf("Select City") }
    var selectedLocation by remember { mutableStateOf("Select Location") }
    var bedroom by rememberSaveable { mutableStateOf("") }
    var bathroom by rememberSaveable { mutableStateOf("") }
    var kitchen by rememberSaveable { mutableStateOf("") }
    var image by rememberSaveable { mutableStateOf("") }

    val data = propertyViewModel.properties.observeAsState(initial = null)
    LaunchedEffect(data.value) {
        propertyViewModel.getAllProperties()
        data.value?.let { property->
            title = property.title
            price = property.price.toString()
            totalArea = property.totalArea.toString()
            selectedLocation = property.location
            selectedCity = property.city
            bedroom = property.bedrooms.toString()
            bathroom = property.bathrooms.toString()
            kitchen = property.kitchens.toString()
            image = property.imageUrl
        }
    }

    val properties = propertyViewModel.allproperties.observeAsState(initial = null)

    Column { Text("Hello") }
}