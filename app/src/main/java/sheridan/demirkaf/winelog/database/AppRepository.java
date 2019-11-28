package sheridan.demirkaf.winelog.database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import sheridan.demirkaf.winelog.beans.Wine;
import sheridan.demirkaf.winelog.utility.SampleData;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<Wine>> mWines;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mWines = getAllWines();
    }

    public void addSampleData() {
        executor.execute(() -> mDb.wineDAO().insertAll(SampleData.getWines()));
    }

    public Wine getWineById(int wineId) {
        return mDb.wineDAO().getWineById(wineId);
    }

    private LiveData<List<Wine>> getAllWines() {
        return mDb.wineDAO().getAllWines();
    }

    public void deleteAllWines() {
        executor.execute(() -> mDb.wineDAO().deleteAll());
    }

    public void insertWine(Wine wine) {
        executor.execute(() -> mDb.wineDAO().insertWine(wine));
    }

    public void deleteWine(Wine wine) {
        executor.execute(() -> mDb.wineDAO().deleteWine(wine));
    }
}
