package com.example.rentalfinder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.rentalfinder.model.UserModel
import com.example.rentalfinder.repository.UserRepo
import com.example.rentalfinder.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*
import junit.framework.TestCase.*

class UserViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repo: UserRepo
    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        repo = mock()
        viewModel = UserViewModel(repo)
    }

    // ⭐ LOGIN TEST
    @Test
    fun login_success_test() {

        doAnswer {
            val callback = it.getArgument<(Boolean, String) -> Unit>(2)
            callback(true, "Login success")
            null
        }.whenever(repo).login(
            any(),
            any(),
            any()
        )

        var successResult = false
        var messageResult = ""

        viewModel.login("test@gmail.com","123456"){ success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Login success", messageResult)

        verify(repo).login(any(), any(), any())
    }

    // ⭐ REGISTER TEST
    @Test
    fun register_success_test() {

        doAnswer {
            val callback = it.getArgument<(Boolean, String, String)->Unit>(2)
            callback(true, "Registered", "user123")
            null
        }.whenever(repo).register(
            any(),
            any(),
            any()
        )

        var result = false

        viewModel.register(
            "test@gmail.com",
            "123456"
        ){ success, _, _ ->
            result = success
        }

        assertTrue(result)
    }

    // ⭐ GET USER TEST (LiveData Testing)
    @Test
    fun getUserById_success_test() {

        val user = UserModel("1","test@gmail.com")

        doAnswer {
            val callback = it.getArgument<(Boolean,String,UserModel?) -> Unit>(1)
            callback(true,"Success",user)
            null
        }.whenever(repo).getUserById(
            any(),
            any()
        )

        val observer = mock<Observer<UserModel?>>()

        viewModel.users.observeForever(observer)

        viewModel.getUserById("1")

        verify(observer).onChanged(user)
    }
}