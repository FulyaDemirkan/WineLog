package sheridan.demirkaf.winelog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import sheridan.demirkaf.winelog.beans.Wine;
import sheridan.demirkaf.winelog.viewmodel.EditorViewModel;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class EditorActivity extends AppCompatActivity {

    private EditorViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primaryColor)));
        
        initViewModel();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);
        mViewModel.mLiveWine.observe(this, new Observer<Wine>() {
            @Override
            public void onChanged(Wine wine) {
                // TODO: LINK TO FIELDS
            }
        });
    }
}
