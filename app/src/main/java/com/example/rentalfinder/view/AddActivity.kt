package com.example.rentalfinder.view

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.rentalfinder.utils.ImageUtils
import com.example.rentalfinder.R
import com.example.rentalfinder.model.PropertyModel
import com.example.rentalfinder.repository.CommonRepoImpl
import com.example.rentalfinder.repository.PropertyRepoImpl
import com.example.rentalfinder.ui.theme.ForestGreen
import com.example.rentalfinder.ui.theme.Gold
import com.example.rentalfinder.ui.theme.MintGreen
import com.example.rentalfinder.ui.theme.OffWhite
import com.example.rentalfinder.view.components.AmenitiesChips
import com.example.rentalfinder.view.components.CommonDropdown
import com.example.rentalfinder.view.components.DescriptionField
import com.example.rentalfinder.view.components.FormField
import com.example.rentalfinder.view.components.NumberField
import com.example.rentalfinder.viewmodel.CommonViewModel
import com.example.rentalfinder.viewmodel.PropertyViewModel

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


    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var totalArea by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Select Category") }
    var description by remember { mutableStateOf("") }

    var selectedCity by remember { mutableStateOf("Select City") }
    var selectedLocation by remember { mutableStateOf("Select Location") }
    var streetAddress by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }

    var selectedAmenities by remember { mutableStateOf(setOf<String>()) }

    var selectedLease by remember { mutableStateOf("Select Lease") }
    var selectedFurniture by remember { mutableStateOf("Select ") }
    var selectedTenant by remember { mutableStateOf("Select Tenant") }
    var yearBuilt by remember { mutableStateOf("") }
    var levels by remember { mutableStateOf("") }
    var bedroom by rememberSaveable { mutableStateOf("") }
    var bathroom by rememberSaveable { mutableStateOf("") }
    var kitchen by rememberSaveable { mutableStateOf("") }

    val commonRepo = remember { CommonRepoImpl() }
    val productRepo = remember { PropertyRepoImpl() }

    val commonViewModel = remember { CommonViewModel(commonRepo) }
    val propertyViewModel = remember { PropertyViewModel(productRepo) }

    val context = LocalContext.current


    Scaffold(containerColor = MintGreen) { innerPadding->
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
            item {
                Column (modifier = Modifier.padding(30.dp)){
                    Text("Basic Property Details",style = TextStyle(
                            fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = ForestGreen,
                        textAlign = TextAlign.Center
                    ));

                    //Title
                    FormField(
                        label = "Property Title",
                        value = title,
                        onValueChange = { title = it },
                        placeholder = "e.g. 2BHK Apartment in Baneshwor"
                    )

                    //Category
                    CommonDropdown(
                        selectedItem = selectedCategory,
                        label = "Category",
                        items = listOf("Apartment", "Pokhara", "Lalitpur"),
                        onItemSelected = { selectedCategory = it }
                    )


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        //Price
                        NumberField(
                            label = "Price/month (NPR)",
                            value = price,
                            onValueChange = { price = it },
                            maxDigits = 7, // adjust as needed
                            modifier = Modifier.weight(1f)
                        )

                        //Area
                        NumberField(
                            label = "Total Area (sq.m)",
                            value = totalArea,
                            onValueChange = { totalArea = it },
                            maxDigits = 5,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    DescriptionField(
                        label = "Description",
                        value = description,
                        onValueChange = { description = it }
                    )

                }
            }
            item {
                Column(modifier = Modifier.padding(30.dp)) {
                    Text("Location Details",style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = ForestGreen,
                        textAlign = TextAlign.Center
                    ));

                    //City
                    CommonDropdown(
                        selectedItem = selectedCity,
                        label = "City",
                        items = listOf("Kathmandu", "Pokhara", "Lalitpur"),
                        onItemSelected = { selectedCity = it }
                    )

                    //Location
                    CommonDropdown(
                        selectedItem = selectedLocation,
                        label = "Location",
                        items = listOf("Baneshwor", "Sankhamul", "Kalanki", "Patan","Suryabinayak"),
                        onItemSelected = { selectedLocation = it }
                    )

                    //Street Address
                    FormField(
                        label = "Street Address",
                        value = streetAddress,
                        onValueChange = { streetAddress = it },
                        placeholder = "e.g. Near Everest Bank, Shankhamul Marg"
                    )

                    //ZIP Code
                    NumberField(
                        label = "ZIP Code",
                        value = zipCode,
                        onValueChange = { zipCode = it },
                        maxDigits = 5
                    )
                }
            }
            item {
                Column(modifier = Modifier.padding(30.dp)
                ) {
                    Text("Amenities Section", style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = ForestGreen,
                        textAlign = TextAlign.Center
                    ));

                    AmenitiesChips(
                        label = "Amenities",
                        amenities = listOf(
                            "Wi-Fi",
                            "Parking",
                            "Lift",
                            "CCTV",
                            "Water Supply"
                        ),
                        selectedAmenities = selectedAmenities,
                        onSelectionChange = { selectedAmenities = it }
                    )
                }
            }

            item {
                Column(modifier = Modifier.padding(30.dp)
                ){
                    Text("Lease and Furnishing Details", style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = ForestGreen,
                        textAlign = TextAlign.Center
                    ));
                    //Lease
                    CommonDropdown(
                        selectedItem = selectedLease,
                        label = "Lease Type",
                        items = listOf("Short-term", "Long-term"),
                        onItemSelected = { selectedLease = it }
                    )
                    //Furnishing Status
                    CommonDropdown(
                        selectedItem = selectedFurniture,
                        label = "Furnishing Status",
                        items = listOf("Furnished", "Unfurnished", "Lalitpur"),
                        onItemSelected = { selectedFurniture = it }
                    )
                    //Tenant Type
                    CommonDropdown(
                        selectedItem = selectedTenant,
                        label = "Tenant Type",
                        items = listOf("Family", "Female-only", "Workers"),
                        onItemSelected = { selectedTenant = it }
                    )

                    //Year Built
                    NumberField(
                        label = "Year Built (B.S.)",
                        value = yearBuilt,
                        onValueChange = { yearBuilt = it },
                        maxDigits = 4
                    )

                    //Number of Levels
                    NumberField(
                        label = "Number of Levels",
                        value = levels,
                        onValueChange = { levels = it },
                        modifier = Modifier.fillMaxWidth()
                    );

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        //Bedroom
                        NumberField(
                            label = "Bedroom",
                            value = bedroom,
                            onValueChange = { bedroom = it },
                            modifier = Modifier.weight(1f)
                        );

                        //Bathroom
                        NumberField(
                            label = "Bathroom",
                            value = bathroom,
                            onValueChange = { bathroom = it },
                            modifier = Modifier.weight(1f)
                        );

                        //Kitchen
                        NumberField(
                            label = "Kitchen",
                            value = kitchen,
                            onValueChange = { kitchen = it },
                            modifier = Modifier.weight(1f)
                        );
                    }
                    Button(onClick = {
                        val pPrice = price.toDoubleOrNull()
                        val pArea = totalArea.toDoubleOrNull()
                        val pZip = zipCode.toIntOrNull()

                        val pYearBuilt = yearBuilt.toIntOrNull()
                        val pLevels = levels.toIntOrNull()
                        val pBedrooms = bedroom.toIntOrNull()
                        val pBathrooms = bathroom.toIntOrNull()
                        val pKitchens = kitchen.toIntOrNull()

                        if (
                            pPrice == null || pArea == null || pZip == null ||
                            pYearBuilt == null || pLevels == null ||
                            pBedrooms == null || pBathrooms == null || pKitchens == null
                        ) {
                            Toast.makeText(context, "Please enter valid numeric values", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        if (selectedImageUri == null){
                            Toast.makeText(context, "Please select a property image", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        commonViewModel.uploadImage(context, selectedImageUri){
                            imageUrl->
                            if(imageUrl == null){
                                Toast.makeText(context, "Image upload failed", Toast.LENGTH_SHORT).show()
                                return@uploadImage
                            }

                            val propertyModel = PropertyModel(
                                title = title.trim(),
                                price = pPrice,
                                totalArea = pArea,
                                description = description.trim(),

                                categoryId = selectedCategory,

                                city = selectedCity,
                                location = selectedLocation,
                                streetAddress = streetAddress.trim(),
                                zipCode = pZip,

                                amenities = selectedAmenities.toList(),

                                leaseType = selectedLease,
                                furnitureType = selectedFurniture,
                                tenantType = selectedTenant,

                                yearBuilt = pYearBuilt,
                                levels = pLevels,
                                bedrooms = pBedrooms,
                                bathrooms = pBathrooms,
                                kitchens = pKitchens,
                                imageUrl = imageUrl
                            )
                            propertyViewModel.addProperty(propertyModel){
                                success, message ->
                                if (success){
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                    },
                        modifier = Modifier.fillMaxWidth()
                            .padding(15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Gold,
                            contentColor = OffWhite)
                    ){
                        Text("Submit",
                            style = TextStyle(fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold))
                    }

                }

            }
        }
    }

}