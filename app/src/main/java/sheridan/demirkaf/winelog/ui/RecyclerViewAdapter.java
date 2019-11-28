package sheridan.demirkaf.winelog.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sheridan.demirkaf.winelog.EditorActivity;
import sheridan.demirkaf.winelog.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sheridan.demirkaf.winelog.beans.Wine;
import sheridan.demirkaf.winelog.utility.Constants;
import sheridan.demirkaf.winelog.utility.ImageConverter;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "EditorActivityDebug";

    private final Context mContext;
    private final List<Wine> mWineList;

    public RecyclerViewAdapter(Context mContext, List<Wine> mWineList) {
        this.mContext = mContext;
        this.mWineList = mWineList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_wine_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Wine wine = mWineList.get(i);
        viewHolder.txtName.setText(wine.getName());

        if(wine.getBase64Image() != null && !wine.getBase64Image().equals("")) {
            Bitmap bitmap = ImageConverter.base64ToBitmap(wine.getBase64Image());
            viewHolder.imageView.setImageBitmap(bitmap);
        }
        else {
            viewHolder.imageView.setImageResource(R.drawable.image_placeholder);
        }

        viewHolder.fab.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, EditorActivity.class);
            intent.putExtra(Constants.WINE_ID_KEY, wine.getId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mWineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.listName)
        TextView txtName;

        @BindView(R.id.listImageView)
        ImageView imageView;

        @BindView(R.id.fab)
        FloatingActionButton fab;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
