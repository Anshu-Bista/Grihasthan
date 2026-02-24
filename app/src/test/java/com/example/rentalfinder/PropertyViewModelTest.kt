package com.example.rentalfinder.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rentalfinder.model.PropertyModel
import com.example.rentalfinder.repository.PropertyRepo
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

class PropertyViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun addProperty_success_test() {

        val repo = mock<PropertyRepo>()
        val viewModel = PropertyViewModel(repo)

        val property = PropertyModel(
            propertyId = "1",
            title = "Test Property",
            price = 1000.00
        )

        doAnswer {
            val callback =
                it.getArgument<(Boolean, String) -> Unit>(1)

            callback(true, "Property added")
            null
        }.`when`(repo).addProperty(eq(property), any())

        var resultMessage = ""
        var resultSuccess = false

        viewModel.addProperty(property) { success, msg ->
            resultSuccess = success
            resultMessage = msg
        }

        assertTrue(resultSuccess)
        assertEquals("Property added", resultMessage)

        verify(repo).addProperty(eq(property), any())
    }

    @Test
    fun deleteProperty_success_refresh_list() {

        val repo = mock<PropertyRepo>()
        val viewModel = PropertyViewModel(repo)

        doAnswer {
            val callback =
                it.getArgument<(Boolean, String) -> Unit>(1)

            callback(true, "Deleted")
            null
        }.`when`(repo).deleteProperty(eq("1"), any())

        viewModel.deleteProperty("1") { success, msg ->
            assertTrue(success)
        }

        verify(repo).deleteProperty(eq("1"), any())
    }
}