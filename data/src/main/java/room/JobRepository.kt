package room

import android.util.Log
import api.api.ApiInterface
import api.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JobRepository(private val jobDao: JobDao, private val apiInterface: ApiInterface) {

    suspend fun refreshOffersAndVacancies() {
        withContext(Dispatchers.IO) {
            try {
                val res = apiInterface.getResponse()

                val offers = res.offers.map { offer ->
                    OfferEntity(
                        id = offer.id ?: "unknown_id",
                        title = offer.title,
                        link = offer.link,
                        buttonText = offer.button?.text
                    )
                }

                val vacancies = res.vacancies.map { vacancy ->
                    VacancyEntity(
                        id = vacancy.id ?: "unknown_id",
                        title = vacancy.title,
                        company = vacancy.company,
                        lookingNumber = vacancy.lookingNumber,
                        address = vacancy.address,
                        experience = vacancy.experience,
                        publishedDate = vacancy.publishedDate,
                        isFavorite = vacancy.isFavorite,
                        salary = vacancy.salary,
                        schedules = vacancy.schedules,
                        appliedNumber = vacancy.appliedNumber,
                        description = vacancy.description,
                        responsibilities = vacancy.responsibilities,
                        questions = vacancy.questions
                    )
                }

                insertOffers(offers)
                insertVacancies(vacancies)

            } catch (e: Exception) {
                Log.e("JobRepository", "Error fetching data", e)
            }
        }
    }

    suspend fun insertOffers(offers: List<OfferEntity>) {
        jobDao.insertOffers(offers)
    }

    suspend fun insertVacancies(vacancies: List<VacancyEntity>) {
        jobDao.insertVacancies(vacancies)
    }

    suspend fun getOffers(): List<OfferEntity> {
        return jobDao.getOffers()
    }

    suspend fun getVacancies(): List<VacancyEntity> {
        return jobDao.getVacancies()
    }
}