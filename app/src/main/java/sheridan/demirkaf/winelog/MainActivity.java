package sheridan.demirkaf.winelog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;

    private ArrayList<Wine> mWineList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);

        initRecyclerView();
        initRecyclerViewAdapter();

        FloatingActionButton mFab = findViewById(R.id.fabAdd);
        mFab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditorActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
        
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }

    private void initRecyclerViewAdapter() {
        if(mAdapter == null)
        {
            mAdapter = new RecyclerViewAdapter(MainActivity.this, mWineList);
            mRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.notifyDataSetChanged();
        }
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
}
