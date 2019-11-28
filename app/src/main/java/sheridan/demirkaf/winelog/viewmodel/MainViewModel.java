package sheridan.demirkaf.winelog.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import sheridan.demirkaf.winelog.beans.Wine;
import sheridan.demirkaf.winelog.database.AppRepository;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<Wine>> mWines;
    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mWines = mRepository.mWines;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllWines() {
        mRepository.deleteAllWines();
    }
}
