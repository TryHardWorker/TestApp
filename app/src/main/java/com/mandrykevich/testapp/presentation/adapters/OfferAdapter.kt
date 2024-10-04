package com.mandrykevich.testapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mandrykevich.testapp.R
import room.OfferEntity

class OfferAdapter : ListAdapter<OfferEntity, OfferAdapter.OfferViewHolder>(OfferDiffCallback()) {

    class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(offer: OfferEntity) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_offer, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = getItem(position)
        holder.bind(offer)
    }
}


class OfferDiffCallback : DiffUtil.ItemCallback<OfferEntity>() {
    override fun areItemsTheSame(oldItem: OfferEntity, newItem: OfferEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OfferEntity, newItem: OfferEntity): Boolean {
        return oldItem == newItem
    }
}