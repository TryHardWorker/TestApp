package com.mandrykevich.testapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mandrykevich.testapp.R


class MainFragment : Fragment() {

    private lateinit var offersRecyclerView: RecyclerView
    private lateinit var vacanciesRecyclerView: RecyclerView
    private lateinit var viewModel: JobViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        offersRecyclerView = view.findViewById(R.id.rv_horizontal)
        vacanciesRecyclerView = view.findViewById(R.id.rv_vertical)

        viewModel = ViewModelProvider(requireActivity()).get(JobViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}