package com.example.rentalfinder.repository

import com.example.rentalfinder.model.PropertyModel

interface PropertyRepo {

    fun addProperty(
        model: PropertyModel,
        callback: (Boolean, String)-> Unit
    )
    fun editProperty(
        model: PropertyModel,
        callback: (Boolean, String) -> Unit
    )

    fun deleteProperty(
        propertyId: String,
        callback: (Boolean, String) -> Unit
    )

    fun getPropertyById(
        propertyId: String,
        callback: (Boolean, String, PropertyModel?) -> Unit
    )

    fun getAllProperties(
        callback: (Boolean, String, List<PropertyModel>?) -> Unit
    )

    fun getPropertiesByCategory(
        categoryId: String,
        callback: (Boolean, String, List<PropertyModel>?) -> Unit
    )

}