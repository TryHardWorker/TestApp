package com.mandrykevich.testapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import room.JobRepository
import room.OfferEntity
import room.VacancyEntity

class JobViewModel(private val repository: JobRepository) : ViewModel() {
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