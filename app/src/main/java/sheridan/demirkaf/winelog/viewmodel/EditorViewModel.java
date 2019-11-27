package sheridan.demirkaf.winelog.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import sheridan.demirkaf.winelog.beans.Wine;
import sheridan.demirkaf.winelog.database.AppRepository;

public class EditorViewModel extends AndroidViewModel {
    private static final String TAG = "EditorViewModelDebug";

    public MutableLiveData<Wine> mLiveWine = new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executer = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(int wineId) {
        executer.execute(() -> {
            Wine wine = mRepository.getWineById(wineId);
            mLiveWine.postValue(wine);
        });
    }

    public void saveWine(Wine wine) {
        Wine wineToEdit = mLiveWine.getValue();

        Log.i(TAG, "wineToEdit " + wineToEdit);
        Log.i(TAG, "wineToEdit.getId " + wineToEdit.getId());

        if(wineToEdit == null) {
            wineToEdit = wine;
            Log.i(TAG, "wineToEdit.getId - null " + wineToEdit.getId());
        } else {
            wineToEdit.setName(wine.getName());
            wineToEdit.setYear(wine.getYear());
            wineToEdit.setCategory(wine.getCategory());
            wineToEdit.setType(wine.getType());
            wineToEdit.setWineryName(wine.getWineryName());
            wineToEdit.setDateOfVisit(wine.getDateOfVisit());
            wineToEdit.setStyle(wine.getStyle());
            wineToEdit.setOak(wine.getOak());
            wineToEdit.setFlavourIntensity(wine.getFlavourIntensity());
            wineToEdit.setRating(wine.getRating());
            wineToEdit.setNotes(wine.getNotes());
            wineToEdit.setBase64Image(wine.getBase64Image());
        }
        mRepository.insertWine(wineToEdit);
    }

    public void deleteWine() { mRepository.deleteWine(mLiveWine.getValue());
    }
}
