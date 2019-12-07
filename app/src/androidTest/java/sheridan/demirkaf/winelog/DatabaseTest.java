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
    public void createDbAndAddSampleData() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        mDb = Room.inMemoryDatabaseBuilder(context,
                AppDatabase.class).build();
        mDao = mDb.wineDAO();
        Log.i(TAG, "createDbAndAddSampleData: created db");
        mDao.insertAll(SampleData.getWines());
        Log.i(TAG, "createDbAndAddSampleData: added sample data");
    }

    @After
    public void deleteSamplesAndCloseDb() {
        for (Wine wine : SampleData.getWines()) {
            mDao.deleteWine(wine);
        }
        Log.i(TAG, "deleteSamplesAndCloseDb: all sample wines deleted");
        mDb.close();
        Log.i(TAG, "db closed");
    }

    @Test
    public void createAndRetrieveWines() {
        int count = mDao.getCount();
        Log.i(TAG, "createAndRetrieveWines: count=" + count);
        assertEquals(SampleData.getWines().size(), count);
    }

    @Test
    public void compareStrings() {
        Wine original = SampleData.getWines().get(0);
        Wine fromDb = mDao.getWineById(1);
        assertEquals(original.getNotes(), fromDb.getNotes());
        assertEquals(1, fromDb.getId());
        Log.i(TAG, "compareStrings: what was stored in db matches object");
    }
    
    @Test
    public void updateWine() {
        String newNote = "This is an unit test note";
        Wine wine = mDao.getWineByName("Wine1");
        wine.setNotes(newNote);
        mDao.insertWine(wine);
        Wine updatedWine = mDao.getWineByName("Wine1");
        assertEquals(updatedWine.getNotes(), newNote);
        Log.i(TAG, "updateWine: wine was updated");
    }

    @Test
    public void deleteWine() {
        Wine wineToBeDeleted = mDao.getWineByName("Wine1");
        mDao.deleteWine(wineToBeDeleted);
        Wine wineDeleted = mDao.getWineByName("Wine1");
        assertEquals(wineDeleted, null);
        Log.i(TAG, "deleteWine: deleted wine");
    }
}
