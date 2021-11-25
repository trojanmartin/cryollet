
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
import sk.fei.beskydky.cryollet.models.User
import sk.fei.beskydky.cryollet.models.UserDao
import java.io.IOException
import android.util.Log


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var userDao: UserDao
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
        userDao = db.userDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetUser() {
        val user = User(
                        username = "martinek333",
                        password = "suchahora123",
                        pin = "666",
                        balance = 0.0,
                        walletId = "extradlhysilnyhash",
                        firstName = "Martyn",
                        sureName = "TrojanN")

        userDao.insert(user)
        val actUser = userDao.getFirstUser()
        actUser?.pin?.let { Log.d("TAG", it) }
        assertEquals(actUser?.pin, "666")
    }
}

