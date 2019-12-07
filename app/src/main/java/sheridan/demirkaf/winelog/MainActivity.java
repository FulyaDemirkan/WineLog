/**
 * Project: Christmas Wish List
 * Authors:
 *  - Fulya Demirkan
 *  - Augusto Peres
 * Date: Dec. 07, 2019
 *
 * Project Description:
 * This app allows the user to add wines tasted, mostly for wine tasting trips. The user can add
 * several information such as name, location tasted, date tasted, notes and many characteristics
 * of the wine in a very intuitive and simple way. Moreover, a picture of the wine can be added
 * either from the camera or from the user's device. The location is tied with Google's Place API,
 * allowing for a very quick entry. The wines added will be displayed in a RecycleView in the main
 * page and will be stored in a DB in the user's device.
 */

package sheridan.demirkaf.winelog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sheridan.demirkaf.winelog.beans.Wine;
import sheridan.demirkaf.winelog.ui.RecyclerViewAdapter;
import sheridan.demirkaf.winelog.utility.Constants;
import sheridan.demirkaf.winelog.viewmodel.AboutFragment;
import sheridan.demirkaf.winelog.viewmodel.ConfirmFragment;
import sheridan.demirkaf.winelog.viewmodel.MainViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ConfirmFragment.ConfirmListener {
    private static final String TAG = "Debug--MainActivity";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<Wine> mWineList = new ArrayList<>();
    private RecyclerViewAdapter mAdapter;

    private MainViewModel mMainViewModel;

    @OnClick(R.id.fabAdd)
    void fabClickHandler()
    {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        final Observer<List<Wine>> wineObserver = wines -> {
            mWineList.clear();
            mWineList.addAll(wines);

            if(mAdapter == null)
            {
                Log.i(TAG, "onChanged: creating recycle view adapter");
                mAdapter = new RecyclerViewAdapter(MainActivity.this, mWineList);
                mRecyclerView.setAdapter(mAdapter);
            }
            else
            {
                mAdapter.notifyDataSetChanged();
            }
        };
        Log.i(TAG, "onChanged: creating view model");
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mMainViewModel.mWines.observe(this, wineObserver);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_sample_data) {
            addSampleData();
            return true;
        } else if (id == R.id.action_delete_all) {
            ConfirmFragment confirmFragment
                    = ConfirmFragment.newInstance(Constants.DELETE_All_DIALOG, getString(R.string.delete_all_confirmation));
            confirmFragment.show(getSupportFragmentManager(), Constants.CONFIRM_DELETE_All);
            return true;
        } else if (id == R.id.about) {
            AboutFragment aboutFragment = AboutFragment.newInstance();
            aboutFragment.show(getSupportFragmentManager(), Constants.MAIN_ABOUT_FRAGMENT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllWines() {
        mMainViewModel.deleteAllWines();
    }

    private void addSampleData() {
        mMainViewModel.addSampleData();
    }

    @Override
    public void onConfirmed(int dialogID) {
        if(dialogID == Constants.DELETE_All_DIALOG)
        {
            deleteAllWines();
        }
    }
}
