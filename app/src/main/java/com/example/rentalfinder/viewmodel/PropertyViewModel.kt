package com.example.rentalfinder.viewmodel

import androidx.lifecycle.ViewModel
import com.example.rentalfinder.model.PropertyModel
import com.example.rentalfinder.repository.PropertyRepo

class PropertyViewModel(val repo: PropertyRepo): ViewModel() {
    fun addProperty(
        model: PropertyModel,
        callback: (Boolean, String)-> Unit
    ){
        repo.addProperty(model,callback)
    }

    fun editProduct(
        model: PropertyModel,
        callback: (Boolean, String) -> Unit){

    }

    fun deleteProduct(
        productId: String,
        callback: (Boolean, String) -> Unit
    ){

    }

    fun getProductById(
        productId: String,
        callback: (Boolean, String, PropertyModel?) -> Unit
    ){}

    fun getAllProducts(
        callback: (Boolean, String, List<PropertyModel>?) -> Unit
    ){}

    fun getProductByCategory(
        categoryId: String,
        callback: (Boolean, String, List<PropertyModel>?) -> Unit
    ){}

}