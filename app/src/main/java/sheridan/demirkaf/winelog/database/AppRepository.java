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
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.wineDAO().insertAll(SampleData.getWines());
            }
        });
    }

    public Wine getWineById(int wineId) {
        return mDb.wineDAO().getWineById(wineId);
    }

    public LiveData<List<Wine>> getAllWines() {
        return mDb.wineDAO().getAllWines();
    }

    public void deleteAllWines() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.wineDAO().deleteAll();
            }
        });
    }
}
