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
import sheridan.demirkaf.winelog.viewmodel.AboutFragment;
import sheridan.demirkaf.winelog.viewmodel.ConfirmFragment;
import sheridan.demirkaf.winelog.viewmodel.DatePickerFragment;
import sheridan.demirkaf.winelog.viewmodel.EditorViewModel;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class EditorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerFragment.DateSetListener, ConfirmFragment.ConfirmListener {

    // <a href="https://www.freepik.com/free-photos-vectors/hand">Hand vector created by freepik - www.freepik.com</a>

    private static final String TAG = "EditorActivityDebug";

    private EditorViewModel mEditorViewModel;
    private TextView mTxtMainFlavours;
    private TextInputLayout mTxtLayoutMainFlavours;

    private String mCategory;
    private String mType;
    private String mWineryName;
    private Date mDateOfVisit;
    private String mBase64Image;

    private boolean mNewEntry, mEditing;
    private ArrayList<String> mMainFlavours;

    @BindView(R.id.txtWineName)
    TextView mWineName;

    @BindView(R.id.txtYear)
    TextView mYear;

    @BindView(R.id.txt_date_of_visit)
    TextView mTxtDateOfVisit;

    @BindView(R.id.chipGrpStyle)
    ChipGroup mChpGrpStyle;

    @BindView(R.id.seekbar_oak)
    SeekBar mOak;

    @BindView(R.id.seekbar_flavour)
    SeekBar mFlavourIntensity;

    @BindView(R.id.chipGrpMainFlavours)
    ChipGroup mChpGrpMainFlavours;

    @BindView(R.id.rating)
    RatingBar mRating;

    @BindView(R.id.txtNotes)
    TextView mTxtNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String apiKey = getString(R.string.api_key);

        ButterKnife.bind(this);

        if(savedInstanceState != null)
        {
            mEditing = savedInstanceState.getBoolean(Constants.EDITING_KEY);
            mDateOfVisit = (Date) savedInstanceState.getSerializable(Constants.DATE_KEY);
        }
        else
        {
            mDateOfVisit = new Date();
        }

        FloatingActionButton mFabDone = findViewById(R.id.fabDone);
        mFabDone.setOnClickListener(view -> saveWine());

        mMainFlavours = new ArrayList<String>();
        mTxtLayoutMainFlavours = findViewById(R.id.txtLayoutMainFlavours);
        mTxtMainFlavours = findViewById(R.id.txtMainFlavours);

        mTxtDateOfVisit = findViewById(R.id.txt_date_of_visit);
        mTxtDateOfVisit.setText(DateFormat.getLongDateFormat(this).format(mDateOfVisit));

        initDatePicker();

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);
        
        initAutocompleteFragment();
        initViewModel();
        initCategorySpinner();
        initTypeSpinner();
        initChipGroupMaterials();
    }

    private void initViewModel() {
        mEditorViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);
        mEditorViewModel.mLiveWine.observe(this, new Observer<Wine>() {
            @Override
            public void onChanged(Wine wine) {
                Log.i(TAG, "wine " + wine.toString());
                if (wine != null && !mEditing) {
                    mWineName.setText(wine.getName());
                    mYear.setText(wine.getYear());
                    mCategory = wine.getCategory();
                    mType = wine.getType();
                    mWineryName = wine.getWineryName();

                    mDateOfVisit = wine.getDateOfVisit();
                    mTxtDateOfVisit.setText(DateFormat.getLongDateFormat(EditorActivity.this).format(wine.getDateOfVisit()));

                    switch (wine.getStyle()) {
                        case "Light-Bodied &amp; Fruity":
                            mChpGrpStyle.check(R.id.chipStyleLight);
                            break;
                        case "Medium-Bodied &amp; Fruity":
                            mChpGrpStyle.check(R.id.chipStyleMedium);
                            break;
                        case "Full-Bodied &amp; Smooth":
                            mChpGrpStyle.check(R.id.chipStyleFullSmooth);
                            break;
                        case "Full-Bodied &amp; Firm":
                            mChpGrpStyle.check(R.id.chipStyleFullFirm);
                            break;
                    }

                    mOak.setProgress(wine.getOak());
                    mFlavourIntensity.setProgress(wine.getFlavourIntensity());

                    Log.i(TAG, "mainflavours " + mMainFlavours);
                    Log.i(TAG, "wine.mainflavours " + wine.getMainFlavours());

                    mMainFlavours = wine.getMainFlavours();
                    for (String flavour: mMainFlavours) {
                        addChip(flavour);
                    }

                    mRating.setRating(wine.getRating());
                    mTxtNotes.setText(wine.getNotes());
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null)
        {
            setTitle(R.string.new_entry);
            mNewEntry = true;
        }
        else
        {
            setTitle(R.string.edit_wine);
            int wineId = extras.getInt(Constants.WINE_ID_KEY);
            mEditorViewModel.loadData(wineId);
        }
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

    private void initDatePicker() {
        ImageButton mImgBtnDate = findViewById(R.id.btn_date);
        mImgBtnDate.setOnClickListener(v -> {
            DialogFragment fragment = DatePickerFragment.getInstance(mDateOfVisit);
            fragment.show(getSupportFragmentManager(), Constants.DATE_PICKER_FRAGMENT);
        });
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

    private void initChipGroupMaterials() {
        mTxtLayoutMainFlavours.setEndIconOnClickListener(v -> {
            String flavour =  mTxtMainFlavours.getText() != null ? mTxtMainFlavours.getText().toString() : "";

            if(!flavour.equals("")) {
                if(mMainFlavours.indexOf(flavour) == -1) {
                    mTxtMainFlavours.setText("");
                    addChip(flavour);
                    mMainFlavours.add(flavour);
                } else {
                    mTxtMainFlavours.setError("This material is already added.");
                }
            }
        });
    }

    private Chip getChip(String text) {
        final Chip chip = new Chip(this);
        chip.setChipDrawable(ChipDrawable.createFromResource(this, R.xml.chip));
        int paddingDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
        chip.setChipBackgroundColorResource(R.color.secondaryLightColor);
        chip.setText(text);

        // remove chip
        chip.setOnCloseIconClickListener(v -> removeChip(chip));

        // edit chip
        chip.setOnClickListener(v -> editChip(chip));

        return chip;
    }

    private void addChip(String material) {
        Chip chip = getChip(material);
        mChpGrpMainFlavours.addView(chip);
    }

    private void editChip(Chip chip) {
        String chipText = ((ChipDrawable) chip.getChipDrawable()).getText().toString();
        mTxtMainFlavours.setText(chipText);
        mChpGrpMainFlavours.removeView(chip);
        mMainFlavours.remove(chipText);
    }

    private void removeChip(Chip chip) {
        int index = mMainFlavours.indexOf(((ChipDrawable) chip.getChipDrawable()).getText().toString());
        mMainFlavours.remove(index);
        mChpGrpMainFlavours.removeView(chip);
    }

    private void saveWine() {
        if(validateRequiredFields()) {
            Wine wine = new Wine();

            wine.setName(mWineName.getText().toString());
            wine.setYear(mYear.getText().toString());
            wine.setCategory(mCategory);
            wine.setType(mType);
            wine.setWineryName(mWineryName);
            wine.setDateOfVisit(mDateOfVisit);

            Chip chip = (mChpGrpStyle.findViewById(mChpGrpStyle.getCheckedChipId()));
            String style = chip != null ? ((ChipDrawable) chip.getChipDrawable()).getText().toString() : "";
            wine.setStyle(style);

            wine.setOak(mOak.getProgress());
            wine.setFlavourIntensity(mFlavourIntensity.getProgress());
            wine.setMainFlavours(mMainFlavours);
            wine.setRating(mRating.getRating());
            wine.setNotes(mTxtNotes.getText().toString());
            wine.setBase64Image("");

            mEditorViewModel.saveWine(wine);
            finish();
        }
    }

    private boolean validateRequiredFields() {

        String wineName = mWineName.getText() != null ? mWineName.getText().toString() : "";

        if(wineName.isEmpty()){
            mWineName.setError("Wine name cannot be empty");
            return false;
        }

        if(mCategory == null || mCategory.isEmpty()){
            mWineName.setError("Category cannot be empty");
            return false;
        }

        if(mWineryName == null || mWineryName.isEmpty()) {
            mWineName.setError("Winery name cannot be empty");
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        ImageView mToolbarImage = findViewById(R.id.toolbar_image);

        if(parent.getId() == R.id.spinner_search_category) {

            Log.i(TAG, selectedItem);
            Log.i(TAG, "mToolbarImage == null" + (mToolbarImage == null));
            switch (selectedItem) {
                case "White Wine":
                    Objects.requireNonNull(mToolbarImage).setImageResource(R.drawable.white_wine);
                    break;
                case "Red Wine":
                    Objects.requireNonNull(mToolbarImage).setImageResource(R.drawable.red_wine);
                    break;
                default:
                    Objects.requireNonNull(mToolbarImage).setImageResource(R.drawable.other_wine);
                    break;
                }

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
        outState.putSerializable(Constants.DATE_KEY, mDateOfVisit);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDateOfVisit);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        calendar.set(year, month, day, hour, minute);
        mDateOfVisit = calendar.getTime();
        mTxtDateOfVisit.setText(DateFormat.getLongDateFormat(this).format(mDateOfVisit));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(!mNewEntry)
        {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.action_delete)
        {
            ConfirmFragment confirmFragment
                    = ConfirmFragment.newInstance(Constants.DELETE_ONE_DIALOG, getString(R.string.delete_one_confirmation));
            confirmFragment.show(getSupportFragmentManager(), Constants.CONFIRM_DELETE_ONE);
            return true;
        }
        else if(item.getItemId() == R.id.action_about)
        {
            AboutFragment aboutFragment = AboutFragment.newInstance();
            aboutFragment.show(getSupportFragmentManager(), Constants.DETAILS_ABOUT_FRAGMENT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfirmed(int dialogID) {
        if(dialogID == Constants.DELETE_ONE_DIALOG)
        {
            mEditorViewModel.deleteWine();
            finish();
        }
    }


}
