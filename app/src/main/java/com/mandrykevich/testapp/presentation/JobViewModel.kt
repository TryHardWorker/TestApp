package com.mandrykevich.testapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import room.JobRepository
import room.OfferEntity
import room.VacancyEntity

class JobViewModel(private val repository: JobRepository) : ViewModel() {

    private val _isDataLoading = MutableLiveData<Boolean>()
    val isDataLoading: LiveData<Boolean> get() = _isDataLoading

    private val _dataLoaded = MutableLiveData<Boolean>()
    val dataLoaded: LiveData<Boolean> get() = _dataLoaded

    fun refreshOffersAndVacancies() {
        viewModelScope.launch {
            _isDataLoading.value = true
            try {
                repository.refreshOffersAndVacancies()
                _dataLoaded.value = true
            } catch (e: Exception) {
                _dataLoaded.value = false
            } finally {
                _isDataLoading.value = false
            }
        }
    }

    fun insertOffers(offers: List<OfferEntity>) {
        viewModelScope.launch {
            repository.insertOffers(offers)
        }
    }

    fun insertVacancies(vacancies: List<VacancyEntity>) {
        viewModelScope.launch {
            repository.insertVacancies(vacancies)
        }
    }

    suspend fun getOffers() = repository.getOffers()
    suspend fun getVacancies() = repository.getVacancies()
}