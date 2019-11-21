package sheridan.demirkaf.winelog.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import sheridan.demirkaf.winelog.beans.Wine;
import sheridan.demirkaf.winelog.database.AppRepository;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<Wine> mLiveWine = new MutableLiveData<>();
    private AppRepository mRepository;

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }
}
