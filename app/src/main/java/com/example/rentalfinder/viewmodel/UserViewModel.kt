package com.example.rentalfinder.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rentalfinder.model.UserModel
import com.example.rentalfinder.repository.UserRepo
import com.google.firebase.auth.FirebaseAuth

class UserViewModel(val repo: UserRepo) : ViewModel() {

    fun login(
        email: String, password: String,
        callback: (Boolean, String)-> Unit
    ){
        repo.login(email,password,callback)
    }

    fun register(
        email: String, password: String,
        callback: (Boolean, String, String) -> Unit    //authentication
    ){
        repo.register(email, password, callback)
    }

    fun addUserToDatabase(
        userId: String, model: UserModel,
        callback: (Boolean, String) -> Unit
    ){
        repo.addUserToDatabase(userId, model, callback)
    }

    fun forgetPassword(
        email: String,
        callback: (Boolean, String) -> Unit
    ){
        repo.forgetPassword(email,callback)
    }

    fun editProfile(model: UserModel, callback: (Boolean, String) -> Unit) {

        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        repo.editProfile(uid, model, callback)
    }

    private val _users = MutableLiveData<UserModel?>()  //real time data trackking
    val users : MutableLiveData<UserModel?>  //_usersko getter
        get() = _users

    fun getUserById(
        userId: String
    ){
        repo.getUserById(userId){
                success, msg, data->
            if (success){
                _users.postValue(data)
            }
        }
    }

    private val _allUsers = MutableLiveData<List<UserModel>?>()
    val allusers : MutableLiveData<List<UserModel>?>
        get() = _allUsers

    fun getAllUserById(){
        repo.getAllUserById{
                success, msg, data->
            if (success){
                _allUsers.postValue(data)
            }
        }
    }

}