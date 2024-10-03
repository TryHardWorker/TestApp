package api.api

data class Offer(
    val id: String,
    val title: String,
    val link: String,
    val button: Button? = null
)

data class Button(
    val text: String
)

data class Address(
    val town: String,
    val street: String,
    val house: String
)

data class Experience(
    val previewText: String,
    val text: String
)

data class Salary(
    val short: String? = null,
    val full: String
)

data class Vacancy(
    val id: String,
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

data class Response(
    val offers: List<Offer>,
    val vacancies: List<Vacancy>
)