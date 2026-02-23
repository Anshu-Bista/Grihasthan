package com.example.rentalfinder.view


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import com.example.rentalfinder.repository.PropertyRepoImpl
import com.example.rentalfinder.view.components.PropertyCard
import com.example.rentalfinder.viewmodel.PropertyViewModel
import com.example.rentalfinder.view.components.PropertyCard
@Composable
fun SearchScreen(){
    Column { Text("Search") }
//    val propertyViewModel = remember { PropertyViewModel(PropertyRepoImpl())}
//
//    LaunchedEffect(Unit) {
//        propertyViewModel.getAllProperties()
//    }
//
//    val properties = propertyViewModel.allproperties.observeAsState(initial = emptyList())
//
//    if (properties.value.isEmpty()) {
//        Text(
//            text = "No properties found",
//            modifier = Modifier.padding(16.dp),
//            style = MaterialTheme.typography.bodyMedium
//        )
//    } else {
//        LazyColumn(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            androidx.compose.foundation.lazy.items(properties) { property ->
//                PropertyCard(property = property)
//            }
//        }
//    }
}