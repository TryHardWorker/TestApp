package com.mandrykevich.testapp.presentation.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mandrykevich.testapp.R
import room.OfferEntity

class OfferAdapter(private val offers: List<OfferEntity>) : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {

    class OfferViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.filter_head)
        val button: TextView = view.findViewById(R.id.filter_toview)
        val imageView: ImageView? = view.findViewById(R.id.filer_iv)
        val imageBG: CardView = view.findViewById(R.id.cv_icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_offer, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = offers[position]
        holder.titleTextView.text = offer.title

        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(offer.link))
            holder.itemView.context.startActivity(intent)
        }

        val resourceId = holder.itemView.context.resources.getIdentifier("ic_${offer.id}", "drawable", holder.itemView.context.packageName)
        if (resourceId != 0) {
            holder.imageView?.setImageResource(resourceId)
            holder.imageView?.visibility = View.VISIBLE
            holder.imageBG.setBackgroundResource(R.drawable.backgroun_icon)
        } else {
            holder.imageView?.setImageResource(0)
            holder.imageBG.setBackgroundResource(R.drawable.backgroud_icon_empty)
        }

        if (offer.buttonText != null) {
            holder.button.text = offer.buttonText
            holder.button.visibility = View.VISIBLE

            holder.button.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(offer.link))
                holder.itemView.context.startActivity(intent)
            }
        } else {
            holder.button.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = offers.size
}

class OfferDiffCallback : DiffUtil.ItemCallback<OfferEntity>() {
    override fun areItemsTheSame(oldItem: OfferEntity, newItem: OfferEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OfferEntity, newItem: OfferEntity): Boolean {
        return oldItem == newItem
    }
}