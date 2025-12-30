package com.example.rentalfinder.view

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.rentalfinder.utils.ImageUtils
import com.example.rentalfinder.view.ui.theme.RentalFinderTheme

import com.example.rentalfinder.R

class AddActivity : ComponentActivity() {
    lateinit var imageUtils: ImageUtils
    var selectedImageUri by mutableStateOf<Uri?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        imageUtils = ImageUtils(this, this)
        imageUtils.registerLaunchers { uri ->
            selectedImageUri = uri
        }
        setContent {
           AddBody(selectedImageUri = selectedImageUri,
               onPickImage = { imageUtils.launchImagePicker() }
           )
        }
    }
}

@Composable
fun AddBody(
    selectedImageUri: Uri?,
    onPickImage: () -> Unit
){
    Scaffold { innerPadding->
        LazyColumn(modifier = Modifier.fillMaxSize()
            .padding(innerPadding)
        ) {
           item {
               Box(
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(200.dp)
                       .clickable(
                           indication = null,
                           interactionSource = remember { MutableInteractionSource() }
                       ) {
                           onPickImage()
                       }
                       .padding(10.dp)
               ) {
                   if (selectedImageUri != null) {
                       AsyncImage(
                           model = selectedImageUri,
                           contentDescription = "Selected Image",
                           modifier = Modifier.fillMaxSize(),
                           contentScale = ContentScale.Crop
                       )
                   } else {
                       Image(
                           painter = painterResource(R.drawable.baseline_camera_alt_24),
                           contentDescription = null,
                           modifier = Modifier.fillMaxSize(),
                           contentScale = ContentScale.Crop
                       )
                   }
               }
           }
        }
    }

}