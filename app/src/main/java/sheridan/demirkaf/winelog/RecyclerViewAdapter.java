package sheridan.demirkaf.winelog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import sheridan.demirkaf.winelog.utility.ImageConverter;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private List<Wine> mWineList;
    private static final int REQUEST_CODE = 1;

    RecyclerViewAdapter(Context mContext, List<Wine> mWineList) {
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

        Wine wine = mWineList.get(i);
        viewHolder.txtName.setText(wine.getName());

        if(!wine.getBase64Image().equals("")) {
            Bitmap bitmap = ImageConverter.base64ToBitmap(wine.getBase64Image());
            viewHolder.imageView.setImageBitmap(bitmap);
        }
        else {
            viewHolder.imageView.setImageResource(R.drawable.image_placeholder);
        }
/*
        viewHolder.fab.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DetailsActivity.class);
            //intent.putExtra("wine", wine);
            intent.putExtra("index", i);
            ((Activity) mContext).startActivityForResult(intent, REQUEST_CODE);
        });*/
    }

    @Override
    public int getItemCount() {
        return mWineList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imageView;
        FloatingActionButton fab;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.listName);
            imageView = itemView.findViewById(R.id.listImageView);
            fab = itemView.findViewById(R.id.fab);
        }
    }
}
