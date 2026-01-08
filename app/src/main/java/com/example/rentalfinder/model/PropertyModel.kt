package com.example.rentalfinder.model

class PropertyModel(
    // Basic info
    var propertyId: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val totalArea: Double = 0.0,
    val description: String = "",

    // Category
    val categoryId: String = "",

    // Location
    val city: String = "",
    val location: String = "",
    val streetAddress: String = "",
    val zipCode: Int = 0,

    // Amenities
    val amenities: List<String> = emptyList(),

    // Property details
    val leaseType: String = "",
    val furnitureType: String = "",
    val tenantType: String = "",
    val yearBuilt: Int = 0,
    val levels: Int = 0,
    val bedrooms: Int = 0,
    val bathrooms: Int = 0,
    val kitchens: Int = 0,

    // Images
    var imageUrl :String = "",
    ) {


    fun PropertyModel.toMap(): Map<String, Any?> {
        return mapOf(
            // Basic info
            "propertyId" to propertyId,
            "title" to title,
            "price" to price,
            "totalArea" to totalArea,
            "description" to description,

            // Category
            "categoryId" to categoryId,

            // Location (nested map is BEST practice)
            "location" to mapOf(
                "city" to city,
                "area" to location,
                "streetAddress" to streetAddress,
                "zipCode" to zipCode
            ),

            // Amenities
            "amenities" to amenities,

            // Property details
            "leaseType" to leaseType,
            "furnitureType" to furnitureType,
            "tenantType" to tenantType,
            "yearBuilt" to yearBuilt,
            "levels" to levels,
            "bedrooms" to bedrooms,
            "bathrooms" to bathrooms,
            "kitchens" to kitchens,

            // Images
            "imageUrl" to imageUrl
        )
    }


}