package com.example.rentalfinder.repository

import com.example.rentalfinder.model.PropertyModel
import com.example.rentalfinder.model.toMap
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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

    override fun editProperty(
        model: PropertyModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(model.propertyId)
            .updateChildren(model.toMap())
            .addOnSuccessListener {
                callback(true, "Property updated")
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Update failed")
            }
    }

    override fun deleteProperty(
        propertyId: String,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(propertyId)
            .removeValue()
            .addOnSuccessListener {
                callback(true, "Property deleted")
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Delete failed")
            }
    }

    override fun getPropertyById(
        propertyId: String,
        callback: (Boolean, String, PropertyModel?) -> Unit
    ) {
        ref.child(propertyId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    var data = snapshot.getValue(PropertyModel::class.java)
                    if(data!= null){
                        callback(true, "Property fetched", data)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, error.message, null)
            }

        })
    }

    override fun getAllProperties(callback: (Boolean, String, List<PropertyModel>?) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    var allProperties = mutableListOf<PropertyModel>()
                    for (data in snapshot.children){
                        var property = data.getValue(PropertyModel::class.java)
                        if (property!=null){
                            allProperties.add(property)
                        }
                    }
                    callback(true,"Property fetched", allProperties)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, error.message, emptyList())
            }

        })
    }

    override fun getPropertiesByCategory(
        categoryId: String,
        callback: (Boolean, String, List<PropertyModel>?) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}