package sheridan.demirkaf.winelog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import sheridan.demirkaf.winelog.beans.Wine;
import sheridan.demirkaf.winelog.viewmodel.MainViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    private static final String TAG = "MainActivityDebug";

    private ArrayList<Wine> mWineList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.recyclerView);

        initRecyclerView();
        initViewModel();

        FloatingActionButton mFab = findViewById(R.id.fabAdd);
        mFab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditorActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    private void initViewModel() {
        final Observer<List<Wine>> wineObserver = new Observer<List<Wine>>() {
            @Override
            public void onChanged(List<Wine> wines) {
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
            }
        };
        Log.i(TAG, "onChanged: creating view model");
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.mWines.observe(this, wineObserver);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            int index = data.getIntExtra("index", -1);
            Wine wine = data.getParcelableExtra("wine");

            if(index == -1) {
                mWineList.add(wine);
                mAdapter.notifyItemInserted(mWineList.size()-1);
            }
            else {
                mWineList.set(index, wine);
                mAdapter.notifyItemChanged(index);
            }
        }
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
            deleteAllWines();
            return true;
        } else if (id == R.id.about) {
//            AboutFragment aboutFragment = AboutFragment.newInstance();
//            aboutFragment.show(getSupportFragmentManager(), Constants.ABOUT_FRAGMENT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllWines() {
        mViewModel.deleteAllWines();
    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }
}
