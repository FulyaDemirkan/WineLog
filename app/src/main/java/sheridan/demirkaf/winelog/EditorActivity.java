package sheridan.demirkaf.winelog;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class EditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primaryColor)));
    }
}
