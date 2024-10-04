package com.mandrykevich.testapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mandrykevich.testapp.R
import room.VacancyEntity

class VacancyAdapter : ListAdapter<VacancyEntity, VacancyAdapter.VacancyViewHolder>(VacancyDiffCallback()) {

    class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(vacancy: VacancyEntity) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy, parent, false)
        return VacancyViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val vacancy = getItem(position)
        holder.bind(vacancy)
    }
}

class VacancyDiffCallback : DiffUtil.ItemCallback<VacancyEntity>() {
    override fun areItemsTheSame(oldItem: VacancyEntity, newItem: VacancyEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: VacancyEntity, newItem: VacancyEntity): Boolean {
        return oldItem == newItem
    }
}