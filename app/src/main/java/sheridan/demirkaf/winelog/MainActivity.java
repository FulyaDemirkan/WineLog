package sheridan.demirkaf.winelog;

import androidx.annotation.Nullable;
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
import sheridan.demirkaf.winelog.utility.Constants;
import sheridan.demirkaf.winelog.viewmodel.AboutFragment;
import sheridan.demirkaf.winelog.viewmodel.ConfirmFragment;
import sheridan.demirkaf.winelog.viewmodel.MainViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ConfirmFragment.ConfirmListener {
    public static final int REQUEST_CODE = 1;
    private static final String TAG = "MainActivityDebug";

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
