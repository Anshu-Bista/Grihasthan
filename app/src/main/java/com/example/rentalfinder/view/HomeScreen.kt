package com.example.rentalfinder.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import com.example.rentalfinder.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(){

    var search by remember { mutableStateOf("") }

    LazyColumn(modifier = Modifier.padding(10.dp)
        .fillMaxSize()) {
        item {
            Column {
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
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_location_on_24),
                    contentDescription = null, modifier = Modifier.padding(end = 10.dp)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Location", style = TextStyle(
                            fontSize = 18.sp
                        )
                    )
                    Text("St. no. 8, Karangi, Khi")
                }
//                Icon(
//                    painter = painterResource(R.drawable.baseline_arrow_right_24),
//                    contentDescription = null
//                )
            }
        }
    }
}

@Preview
@Composable
fun HomePreview(){

}