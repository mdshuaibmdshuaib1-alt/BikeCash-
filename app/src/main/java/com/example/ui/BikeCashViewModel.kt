package com.example.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.data.BikeSubmission
import com.example.data.BikeRepository
import com.example.data.PriceEstimate

data class BikeFormState(
    val brand: String = "",
    val model: String = "",
    val registrationYear: String = "",
    val registrationState: String = "",
    val kilometers: String = "",
    val condition: String = "",
    val fullName: String = "",
    val mobileNumber: String = "",
    val photoUris: List<Uri> = emptyList()
)

class BikeCashViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BikeFormState())
    val uiState: StateFlow<BikeFormState> = _uiState.asStateFlow()

    fun updateBikeDetails(brand: String, model: String, year: String, state: String) {
        _uiState.value = _uiState.value.copy(
            brand = brand,
            model = model,
            registrationYear = year,
            registrationState = state
        )
    }

    fun updateKilometersAndCondition(kilometers: String, condition: String) {
        _uiState.value = _uiState.value.copy(
            kilometers = kilometers,
            condition = condition
        )
    }

    fun getEstimate(submission: BikeSubmission): PriceEstimate {
        val service = com.example.data.PriceEstimationService()
        return service.estimatePrice(submission)
    }

    fun submitSubmission(submission: BikeSubmission, repository: BikeRepository, onResult: (String?, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val id = repository.submitBike(submission)
                onResult(id, null)
            } catch (e: Exception) {
                onResult(null, e.message)
            }
        }
    }

    fun updateContactDetails(name: String, mobile: String) {
        _uiState.value = _uiState.value.copy(
            fullName = name,
            mobileNumber = mobile
        )
    }

    fun addPhoto(uri: Uri) {
        _uiState.value = _uiState.value.copy(photoUris = _uiState.value.photoUris + uri)
    }
}
