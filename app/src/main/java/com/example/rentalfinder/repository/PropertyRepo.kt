package com.example.rentalfinder.repository

import com.example.rentalfinder.model.PropertyModel

interface PropertyRepo {

    fun addProperty(
        model: PropertyModel,
        callback: (Boolean, String)-> Unit
    )

    fun editProduct(
        model: PropertyModel,
        callback: (Boolean, String) -> Unit)

    fun deleteProduct(
        productId: String,
        callback: (Boolean, String) -> Unit)

    fun getProductById(
        productId: String,
        callback: (Boolean, String, PropertyModel?) -> Unit)

    fun getAllProducts(
        callback: (Boolean, String, List<PropertyModel>?) -> Unit)

    fun getProductByCategory(
        categoryId: String,
        callback: (Boolean, String, List<PropertyModel>?) -> Unit)

}