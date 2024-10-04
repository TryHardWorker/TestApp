package com.mandrykevich.testapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.api.RetrofitClient
import com.mandrykevich.testapp.R
import com.mandrykevich.testapp.presentation.adapters.OfferAdapter
import com.mandrykevich.testapp.presentation.adapters.VacancyAdapter
import kotlinx.coroutines.launch
import room.AppDatabase
import room.JobRepository


class MainFragment : Fragment() {

    private val apiService = RetrofitClient.getApiService()
    private lateinit var offersRecyclerView: RecyclerView
    private lateinit var vacanciesRecyclerView: RecyclerView
    private lateinit var offersAdapter: OfferAdapter
    private lateinit var vacanciesAdapter: VacancyAdapter
    private lateinit var jobRepository: JobRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jobRepository = JobRepository(AppDatabase.getDatabase(requireContext()).jobDao(), apiService)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        offersRecyclerView = view.findViewById(R.id.rv_horizontal)
        vacanciesRecyclerView = view.findViewById(R.id.rv_vertical)

        offersRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        vacanciesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        loadOffersAndVacancies()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private fun loadOffersAndVacancies() {
        viewLifecycleOwner.lifecycleScope.launch {
            val offers = jobRepository.getOffers()
            offersAdapter = OfferAdapter(offers)
            offersRecyclerView.adapter = offersAdapter

            val vacancies = jobRepository.getVacancies()
            vacanciesAdapter = VacancyAdapter(vacancies)
            vacanciesRecyclerView.adapter = vacanciesAdapter
        }
    }
}