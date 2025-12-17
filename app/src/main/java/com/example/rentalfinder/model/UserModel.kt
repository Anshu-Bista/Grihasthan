package com.example.rentalfinder.model

data class UserModel(
    val id : String = "",
    val firstName : String = "",
    val lastName : String = "",
    val email : String = "",
    val gender : String = "",
    val dob : String = "",
){
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "id" to id,
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "gender" to gender,
            "dob" to dob,
        )
    }
}