package room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOffers(offers: List<OfferEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancies(vacancies: List<VacancyEntity>)

    @Query("SELECT * FROM offers")
    suspend fun getOffers(): List<OfferEntity>

    @Query("SELECT * FROM vacancies")
    suspend fun getVacancies(): List<VacancyEntity>
}