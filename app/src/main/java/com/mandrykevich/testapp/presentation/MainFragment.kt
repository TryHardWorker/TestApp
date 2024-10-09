package com.mandrykevich.testapp.presentation
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
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
import room.OfferEntity
import room.VacancyEntity


class MainFragment : Fragment() {

    private val apiService = RetrofitClient.getApiService()
    private lateinit var offersRecyclerView: RecyclerView
    private lateinit var vacanciesRecyclerView: RecyclerView
    private lateinit var offersAdapter: OfferAdapter
    private lateinit var vacanciesAdapter: VacancyAdapter
    private lateinit var jobRepository: JobRepository
    private lateinit var showMoreButton: Button
    private lateinit var vacanciesAmount: TextView
    private lateinit var vacanciesFilter: TextView
    private lateinit var vacanciesForYou: TextView


    private lateinit var allOffers: List<OfferEntity>
    private lateinit var allVacancies: List<VacancyEntity>

    private val visibleVacancyCount = 3

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
        showMoreButton = view.findViewById(R.id.btn_showall)
        vacanciesAmount = view.findViewById(R.id.tv_vac_amount)
        vacanciesFilter = view.findViewById(R.id.tv_vac)
        vacanciesForYou = view.findViewById(R.id.tv_urvac)

        offersRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        vacanciesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        loadOffersAndVacancies()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showMoreButton.setOnClickListener {
            showAllVacancies()
            vacanciesAmount.visibility = View.VISIBLE
            vacanciesFilter.visibility = View.VISIBLE
            offersRecyclerView.visibility = View.GONE
            vacanciesForYou.visibility = View.GONE
            val layoutParams = vacanciesRecyclerView.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.topMargin = 64
            vacanciesRecyclerView.layoutParams = layoutParams
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private fun loadOffersAndVacancies() {
        viewLifecycleOwner.lifecycleScope.launch {
            allOffers = jobRepository.getOffers()
            allVacancies = jobRepository.getVacancies()

            offersAdapter = OfferAdapter(allOffers)
            offersRecyclerView.adapter = offersAdapter

            val visibleVacancies = allVacancies.take(visibleVacancyCount)
            vacanciesAdapter = VacancyAdapter()
            vacanciesAdapter.submitList(visibleVacancies)
            vacanciesRecyclerView.adapter = vacanciesAdapter

            val remainingVacancies = allVacancies.size - visibleVacancyCount
            val vacanciesText = when {
                remainingVacancies % 10 == 1 && remainingVacancies % 100 != 11 -> "вакансия"
                remainingVacancies % 10 in 2..4 && remainingVacancies % 100 !in 12..14 -> "вакансии"
                else -> "вакансий"
            }
            vacanciesAmount.text = "${allVacancies.size} $vacanciesText"
            showMoreButton.text = "Ещё $remainingVacancies $vacanciesText"
            showMoreButton.visibility = if (allVacancies.size > visibleVacancyCount) View.VISIBLE else View.GONE
        }
    }

    private fun showAllVacancies() {
        vacanciesAdapter.submitList(allVacancies)
        showMoreButton.visibility = View.GONE
    }
}