package sheridan.demirkaf.winelog.viewmodel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import sheridan.demirkaf.winelog.R;

public class AboutFragment extends DialogFragment {
    public AboutFragment() {
    }

    public static AboutFragment newInstance(){
        return new AboutFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // create a new AlertDialog Builder
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setTitle(getString(R.string.winelog_project));
        builder.setMessage(getString(R.string.author));

        builder.setPositiveButton(android.R.string.ok, null);

        return builder.create();
    }
}
