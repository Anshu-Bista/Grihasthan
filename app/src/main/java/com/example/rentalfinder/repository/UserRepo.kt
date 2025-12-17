package com.example.rentalfinder.repository

import com.example.rentalfinder.model.UserModel

interface UserRepo {
    fun login(email: String, password: String,
              callback: (Boolean, String)-> Unit)

    fun register(email: String, password: String,
                 callback: (Boolean, String, String) -> Unit)   //authentication

    fun addUserToDatabase(userId: String, model: UserModel,
                          callback: (Boolean, String) -> Unit)

    fun forgetPassword(email: String,
                       callback: (Boolean, String) -> Unit)

    fun deleteAccount(userId: String,
                      callback: (Boolean, String) -> Unit)

    fun editProfile(userId: String, model: UserModel,
                    callback: (Boolean, String) -> Unit)

    fun getUserById(userId: String,
                    callback: (Boolean, String, UserModel?) -> Unit)

    fun getAllUserById(callback: (Boolean, String, List<UserModel>?) -> Unit)

}