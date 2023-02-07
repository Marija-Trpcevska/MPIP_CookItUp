package finki.ukim.mk.cookitup.domain.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import finki.ukim.mk.cookitup.domain.search.model.RecipeApi
import finki.ukim.mk.cookitup.domain.search.room.RecipeApiDao
import finki.ukim.mk.cookitup.helpers.converters.ArrayListConverter
import finki.ukim.mk.cookitup.helpers.converters.ListConverter

//mozebi ke treba da se dodefinira export schema za Firebase
//[RecipeApi::class,RecipeWritten::class,RecipeCamera::class
@Database(entities = [RecipeApi::class], version = 1, exportSchema = false)
@TypeConverters(ArrayListConverter::class, ListConverter::class)
abstract class AppDatabase :RoomDatabase(){
    abstract fun recipeApiDao(): RecipeApiDao
//    abstract fun recipeWrittenDao(): RecipeWrittenDao
//    abstract fun recipeCameraDao(): RecipeCameraDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context,
                    AppDatabase::class.java,
                    "cookitup_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}