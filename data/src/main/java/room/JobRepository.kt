package room

import android.util.Log
import api.api.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JobRepository(private val jobDao: JobDao, private val apiInterface: ApiInterface) {

    // Метод для обновления предложений и вакансий из API
    suspend fun refreshOffersAndVacancies() {
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.getResponse()

                // Преобразование ответа API в список объектов OfferEntity
                val offers = response.offers.map { offer ->
                    OfferEntity(
                        id = offer.id ?: "unknown_id",
                        title = offer.title,
                        link = offer.link,
                        buttonText = offer.button?.text ?: ""
                    )
                }

                // Преобразование ответа API в список объектов VacancyEntity
                val vacancies = response.vacancies.map { vacancy ->
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
        withContext(Dispatchers.IO) {
            jobDao.insertOffers(offers)
        }
    }

    suspend fun insertVacancies(vacancies: List<VacancyEntity>) {
        withContext(Dispatchers.IO) {
            jobDao.insertVacancies(vacancies)
        }
    }

    suspend fun getOffers(): List<OfferEntity> {
        return withContext(Dispatchers.IO) {
            jobDao.getOffers()
        }
    }

    suspend fun getVacancies(): List<VacancyEntity> {
        return withContext(Dispatchers.IO) {
            jobDao.getVacancies()
        }
    }
}