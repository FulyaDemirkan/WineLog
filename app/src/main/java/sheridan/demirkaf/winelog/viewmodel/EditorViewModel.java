package sheridan.demirkaf.winelog.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import sheridan.demirkaf.winelog.beans.Wine;
import sheridan.demirkaf.winelog.database.AppRepository;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<Wine> mLiveWine = new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executer = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(int wineId) {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                Wine wine = mRepository.getWineById(wineId);
                mLiveWine.postValue(wine);
            }
        });
    }
}
