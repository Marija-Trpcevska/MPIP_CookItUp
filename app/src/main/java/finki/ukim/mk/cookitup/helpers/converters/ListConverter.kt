package finki.ukim.mk.cookitup.helpers.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ListConverter {
    @TypeConverter
    fun fromListToString(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToList(value: String): List<String> {
        return try {
            val listStringType: Type = object : TypeToken<List<String?>?>() {}.type
            Gson().fromJson(value,listStringType)

        } catch (e: Exception) {
            listOf()
        }
    }
}