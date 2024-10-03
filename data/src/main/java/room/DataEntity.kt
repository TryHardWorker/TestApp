package room

import androidx.room.Entity
import androidx.room.PrimaryKey
import api.api.Address
import api.api.Experience
import api.api.Salary

@Entity(tableName = "offers")
data class OfferEntity(
    @PrimaryKey val id: String,
    val title: String,
    val link: String,
    val buttonText: String?
)

@Entity(tableName = "vacancies")
data class VacancyEntity(
    @PrimaryKey val id: String,
    val lookingNumber: Int? = null,
    val title: String,
    val address: Address,
    val company: String,
    val experience: Experience,
    val publishedDate: String,
    val isFavorite: Boolean,
    val salary: Salary,
    val schedules: List<String>,
    val appliedNumber: Int? = null,
    val description: String? = null,
    val responsibilities: String? = null,
    val questions: List<String>? = null
)