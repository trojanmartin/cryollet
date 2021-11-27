package sk.fei.beskydky.cryollet.database.appDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import sk.fei.beskydky.cryollet.data.model.User
import sk.fei.beskydky.cryollet.data.model.Wallet

@Database(entities = [User::class, Wallet::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDatabaseDao(): AppDatabaseDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var appDatabaseDao = database.appDatabaseDao()

                    // Delete all content here.
                    appDatabaseDao.clearAllUsers()

                    // Add sample words.
                    var user = User(pin="666")
                    appDatabaseDao.insertUser(user)
                }
            }
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}