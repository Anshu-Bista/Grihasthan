package com.example.rentalfinder.repository

import com.example.rentalfinder.model.PropertyModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PropertyRepoImpl: PropertyRepo {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    val ref: DatabaseReference = database.getReference("properties")

    override fun addProperty(
        model: PropertyModel,
        callback: (Boolean, String) -> Unit
    ) {
        var id = ref.push().key.toString()
        model.propertyId = id
        ref.child(id).setValue(model).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true, "Property added successfully")
            }else{
                callback(false,"${it.exception?.message}")
            }
        }

    }

    override fun editProduct(
        model: PropertyModel,
        callback: (Boolean, String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteProduct(
        productId: String,
        callback: (Boolean, String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getProductById(
        productId: String,
        callback: (Boolean, String, PropertyModel?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getAllProducts(callback: (Boolean, String, List<PropertyModel>?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getProductByCategory(
        categoryId: String,
        callback: (Boolean, String, List<PropertyModel>?) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}