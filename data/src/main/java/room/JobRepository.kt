package room

class JobRepository(private val jobDao: JobDao) {
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