package com.example.rentalfinder.viewmodel

import androidx.lifecycle.MutableLiveData
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

    fun editProperty(
        model: PropertyModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.editProperty(model, callback)
    }

    fun deleteProperty(
        propertyId: String,
        callback: (Boolean, String) -> Unit
    ) {
        repo.deleteProperty(propertyId, callback)
    }

    private val _properties = MutableLiveData<PropertyModel?>()
    val properties : MutableLiveData<PropertyModel?>
        get() = _properties

    private val _allproperties = MutableLiveData<List<PropertyModel>?>()
    val allproperties: MutableLiveData<List<PropertyModel>?>
        get() = _allproperties

    fun getPropertyById(
        propertyId: String
    ) {
        repo.getPropertyById(propertyId){
                success,message,data->
            if(success){
                _properties.postValue(data)
            }
        }
    }

    fun getAllProperties() {
        repo.getAllProperties(){
                success,message,data->
            if(success){
                _allproperties.postValue(data)
            }
        }
    }

    fun getPropertiesByCategory(
        categoryId: String,
        callback: (Boolean, String, List<PropertyModel>?) -> Unit
    ) {
        repo.getPropertiesByCategory(categoryId, callback)
    }
}
