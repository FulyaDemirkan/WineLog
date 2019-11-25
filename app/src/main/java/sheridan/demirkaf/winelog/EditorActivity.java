package sheridan.demirkaf.winelog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import butterknife.BindView;
import butterknife.ButterKnife;
import sheridan.demirkaf.winelog.beans.Wine;
import sheridan.demirkaf.winelog.utility.Constants;
import sheridan.demirkaf.winelog.viewmodel.DatePickerFragment;
import sheridan.demirkaf.winelog.viewmodel.EditorViewModel;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.chip.ChipGroup;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class EditorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerFragment.DateSetListener {

    // <a href="https://www.freepik.com/free-photos-vectors/background">Background vector created by macrovector - www.freepik.com</a>
    // <a href="https://www.freepik.com/free-photos-vectors/hand">Hand vector created by freepik - www.freepik.com</a>

    private static final String TAG = "EditorActivityDebug";

    private EditorViewModel mViewModel;

    @BindView(R.id.txtWineName)
    TextView mWineName;

    @BindView(R.id.txtYear)
    TextView mYear;

    String mCategory;
    String mType;

    String mWineryName;

    Date mDate;
    ImageButton mImgBtnDate;

    @BindView(R.id.txt_date_of_visit)
    TextView mTxtDateOfVisit;

    @BindView(R.id.chipGrpStyle)
    ChipGroup mStyle;

    @BindView(R.id.seekbar_oak)
    SeekBar mOak;

    @BindView(R.id.seekbar_flavour)
    SeekBar mFlavour;

    @BindView(R.id.rating)
    RatingBar mRating;

    @BindView(R.id.txtNotes)
    TextView mNotes;

    private boolean mNewEntry, mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mTxtDateOfVisit = findViewById(R.id.txt_date_of_visit);

        initViewModel();

        initCategorySpinner();
        initTypeSpinner();

        initDatePicker();
        String apiKey = getString(R.string.api_key);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);
        
        initAutocompleteFragment();
    }

    private void initDatePicker() {
        mImgBtnDate = findViewById(R.id.btn_date);
        mImgBtnDate.setOnClickListener(v -> {
            DialogFragment fragment = DatePickerFragment.getInstance(mDate);
            fragment.show(getSupportFragmentManager(), Constants.DATE_PICKER_FRAGMENT);
        });
    }

    private void initCategorySpinner() {
        Spinner categorySpinner = findViewById(R.id.spinner_search_category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(this);

        if (mCategory != null) {
            int spinnerPosition = adapter.getPosition(mCategory);
            categorySpinner.setSelection(spinnerPosition);
        }
    }

    private void initTypeSpinner() {
        Spinner typeSpinner = findViewById(R.id.spinner_search_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(this);

        if (mType != null) {
            int spinnerPosition = adapter.getPosition(mType);
            typeSpinner.setSelection(spinnerPosition);
        }
    }

    private void initAutocompleteFragment() {
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                mWineryName = place.getName();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);
        mViewModel.mLiveWine.observe(this, wine -> {
            if(wine != null && !mEditing) {
                mWineName.setText(wine.getName());
                mYear.setText(wine.getYear());
                mCategory = wine.getCategory();
                mType = wine.getType();
                // mWineryName;
                switch (wine.getStyle()) {
                    case "Light-Bodied &amp; Fruity":
                        mStyle.check(R.id.chipStyleLight);
                        break;
                    case "Medium-Bodied &amp; Fruity":
                        mStyle.check(R.id.chipStyleMedium);
                        break;
                    case "Full-Bodied &amp; Smooth":
                        mStyle.check(R.id.chipStyleFullSmooth);
                        break;
                    case "Full-Bodied &amp; Firm":
                        mStyle.check(R.id.chipStyleFullFirm);
                        break;
                }
                mOak.setProgress(wine.getOak());
                mFlavour.setProgress(wine.getFlavourIntensity());
                mRating.setRating(wine.getRating());
                mNotes.setText(wine.getNotes());
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null)
        {
            Log.i(TAG, "new entry");
            setTitle(R.string.new_entry);
            mNewEntry = true;
        }
        else
        {
            setTitle(R.string.edit_wine);
            int wineId = extras.getInt(Constants.WINE_ID_KEY);
            mViewModel.loadData(wineId);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();

        if(parent.getId() == R.id.spinner_search_category) {
            /*
            if (selectedItem.equals("White Wine")) {
                getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.primaryLightColor));
            }*/
            mCategory = selectedItem;
        }
        else
            mType = selectedItem;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(Constants.EDITING_KEY, true);
        outState.putSerializable(Constants.DATE_KEY, mDate);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        calendar.set(year, month, day, hour, minute);
        mDate = calendar.getTime();
        mTxtDateOfVisit.setText(DateFormat.getLongDateFormat(this).format(mDate));
    }
}
