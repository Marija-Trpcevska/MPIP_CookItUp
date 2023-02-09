package finki.ukim.mk.cookitup.domain.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import finki.ukim.mk.cookitup.domain.add.model.RecipeCamera
import finki.ukim.mk.cookitup.domain.add.model.RecipeWritten
import finki.ukim.mk.cookitup.domain.add.room.RecipeCameraDao
import finki.ukim.mk.cookitup.domain.add.room.RecipeWrittenDao
import finki.ukim.mk.cookitup.domain.search.model.RecipeApi
import finki.ukim.mk.cookitup.domain.search.room.RecipeApiDao
import finki.ukim.mk.cookitup.helpers.converters.ArrayListConverter
import finki.ukim.mk.cookitup.helpers.converters.ListConverter

@Database(entities = [RecipeApi::class, RecipeWritten::class, RecipeCamera::class], version = 3, exportSchema = true)
@TypeConverters(ArrayListConverter::class, ListConverter::class)
abstract class AppDatabase :RoomDatabase(){
    abstract fun recipeApiDao(): RecipeApiDao
    abstract fun recipeWrittenDao(): RecipeWrittenDao
    abstract fun recipeCameraDao(): RecipeCameraDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `written` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `label` TEXT NOT NULL, `image` TEXT NOT NULL, `ingredientLines` TEXT NOT NULL, `instructions` TEXT NOT NULL, `mealType` TEXT NOT NULL)")
            }
        }

        private val migration_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL( "CREATE TABLE IF NOT EXISTS `camera` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `label` TEXT NOT NULL, `image` TEXT NOT NULL, `notes` TEXT NOT NULL, `mealType` TEXT NOT NULL)")
            }
        }

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context,
                    AppDatabase::class.java,
                    "cookitup_database")
                    .addMigrations(migration_1_2)
                    .addMigrations(migration_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}