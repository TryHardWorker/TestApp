package com.mandrykevich.testapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mandrykevich.testapp.R
import room.VacancyEntity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class VacancyAdapter(private val vacancies: List<VacancyEntity>) : RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>() {

    class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vacancyTitle: TextView = itemView.findViewById(R.id.tv_vacancy_name)
        val vacancyPeopleCount: TextView = itemView.findViewById(R.id.tv_peoplecount)
        val vacancySalary: TextView = itemView.findViewById(R.id.tv_vacancy_salary)
        val vacancyCity: TextView = itemView.findViewById(R.id.tv_vacancy_city)
        val vacancyCompany: TextView = itemView.findViewById(R.id.tv_vacancy_company)
        val vacancyExp: TextView = itemView.findViewById(R.id.tv_vacancy_exp)
        val vacancyUrvac: TextView = itemView.findViewById(R.id.tv_vacancy_postdate)

        fun bind(vacancy: VacancyEntity) {

            vacancyTitle.text = vacancy.title
            vacancySalary.text = vacancy.salary.short
            vacancyCity.text = vacancy.address.town
            vacancyCompany.text = vacancy.company
            vacancyExp.text = vacancy.experience.previewText
            vacancyUrvac.text = transformDate(vacancy.publishedDate)




            if (vacancy.salary.short.isNullOrEmpty()) {
                vacancySalary.visibility = View.GONE
            } else {
                vacancySalary.text = vacancy.salary.short
                vacancySalary.visibility = View.VISIBLE
            }

            val lookingNumber = vacancy.lookingNumber ?: 0
            val peopleText = when {
                lookingNumber % 10 == 1 && lookingNumber % 100 != 11 -> "человек"
                lookingNumber % 10 in 2..4 && lookingNumber % 100 !in 12..14 -> "человека"
                else -> "человек"
            }

            if (lookingNumber > 0) {
                vacancyPeopleCount.text = "Сейчас просматривают $lookingNumber $peopleText"
                vacancyPeopleCount.visibility = View.VISIBLE
            } else {
                vacancyPeopleCount.visibility = View.GONE
            }
        }

        fun transformDate(dateString: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("d MMMM", Locale("ru"))

            try {
                val date = inputFormat.parse(dateString)
                return outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy, parent, false)
        return VacancyViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
    }

    override fun getItemCount(): Int {
        return vacancies.size
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