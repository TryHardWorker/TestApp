package com.mandrykevich.testapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.api.RetrofitClient
import com.mandrykevich.testapp.R
import com.mandrykevich.testapp.presentation.adapters.FavoriteAdapter
import kotlinx.coroutines.launch
import room.AppDatabase
import room.JobRepository

class FavoriteFragment : Fragment() {

    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoriteRecyclerView: RecyclerView
    private lateinit var jobRepository: JobRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jobRepository = JobRepository(AppDatabase.getDatabase(requireContext()).jobDao(), RetrofitClient.getApiService())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        favoriteRecyclerView = view.findViewById(R.id.rv_isfavorite)
        if (favoriteRecyclerView != null) {
            favoriteRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            loadFavorite()
        } else {

        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }

    private fun loadFavorite() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val favoriteVacancies = jobRepository.getVacancies()
                favoriteAdapter = FavoriteAdapter()
                favoriteRecyclerView.adapter = favoriteAdapter
                favoriteAdapter.submitList(favoriteVacancies)
            } catch (e: Exception) {

            }
        }
    }
}