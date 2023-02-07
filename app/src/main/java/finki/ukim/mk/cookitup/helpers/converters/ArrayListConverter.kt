package finki.ukim.mk.cookitup.helpers.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ArrayListConverter {

    @TypeConverter
    fun fromArrayListToString(value: ArrayList<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToArrayList(value: String): ArrayList<String> {
        return try {
            val arrayListStringType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
            Gson().fromJson(value,arrayListStringType)

        } catch (e: Exception) {
            arrayListOf()
        }
    }
}

