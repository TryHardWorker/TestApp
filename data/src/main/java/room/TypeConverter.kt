package room

import androidx.room.TypeConverter
import api.api.Address
import api.api.Experience
import api.api.Salary
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromAddress(address: Address): String {
        return Gson().toJson(address)
    }

    @TypeConverter
    fun toAddress(addressString: String): Address {
        val type = object : TypeToken<Address>() {}.type
        return Gson().fromJson(addressString, type)
    }

    @TypeConverter
    fun fromExperience(experience: Experience): String {
        return Gson().toJson(experience)
    }

    @TypeConverter
    fun toExperience(experienceString: String): Experience {
        val type = object : TypeToken<Experience>() {}.type
        return Gson().fromJson(experienceString, type)
    }

    @TypeConverter
    fun fromSalary(salary: Salary): String {
        return Gson().toJson(salary)
    }

    @TypeConverter
    fun toSalary(salaryString: String): Salary {
        val type = object : TypeToken<Salary>() {}.type
        return Gson().fromJson(salaryString, type)
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toStringList(listString: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(listString, type)
    }
}