package sheridan.demirkaf.winelog;

import android.content.Context;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import sheridan.demirkaf.winelog.beans.Wine;
import sheridan.demirkaf.winelog.database.AppDatabase;
import sheridan.demirkaf.winelog.database.WineDAO;
import sheridan.demirkaf.winelog.utility.SampleData;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private WineDAO mDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        mDb = Room.inMemoryDatabaseBuilder(context,
                AppDatabase.class).build();
        mDao = mDb.wineDAO();
        Log.i(TAG, "createDb");
    }

    @After
    public void closeDb() {
        mDb.close();
        Log.i(TAG, "closeDb");
    }

    @Test
    public void createAndRetrieveTasks() {
        mDao.insertAll(SampleData.getWines());
        int count = mDao.getCount();
        Log.i(TAG, "createAndRetrieveWines: count=" + count);
        assertEquals(SampleData.getWines().size(), count);
    }

    @Test
    public void compareStrings() {
        mDao.insertAll(SampleData.getWines());
        Wine original = SampleData.getWines().get(0);
        Wine fromDb = mDao.getWineById(1);
        assertEquals(original.getNotes(), fromDb.getNotes());
        assertEquals(1, fromDb.getId());
    }
}
