
package sk.fei.beskydky.cryollet

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import sk.fei.beskydky.cryollet.database.AppDatabase
import sk.fei.beskydky.cryollet.data.model.User
import java.io.IOException
import sk.fei.beskydky.cryollet.database.AppDatabaseDao


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var appDatabaseDao: AppDatabaseDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        appDatabaseDao = db.appDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetUser() {
        val user = User(publicKey = "SUCHAHORAIDE", secretKey = "AUTAFUDBALZENY", pin = "666")

        appDatabaseDao.insertUser(user)
        val actUser = appDatabaseDao.getFirstUser()

        assertEquals(actUser?.secretKey, "AUTAFUDBALZENY")
    }
}

