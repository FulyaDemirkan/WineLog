package sheridan.demirkaf.winelog.viewmodel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import sheridan.demirkaf.winelog.R;

public class ConfirmFragment extends DialogFragment {

    private static final String MESSAGE = "message";
    private static final String ID = "id";

    private int mDialogId;

    public interface ConfirmListener {
        void onConfirmed(int dialogID);
    }
    private ConfirmListener mConfirmListener;

    public ConfirmFragment() { }

    public static ConfirmFragment newInstance(int dialogId, String message) {
        ConfirmFragment fragment = new ConfirmFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ID, dialogId);
        arguments.putString(MESSAGE, message);
        fragment.setArguments(arguments);
        return fragment;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle bundle) {

        // get the arguments
        mDialogId = 0;
        String message = MESSAGE;
        Bundle arguments = getArguments();
        if(arguments != null){
            mDialogId = arguments.getInt(ID);
            message = arguments.getString(MESSAGE);
        }

        // create a new AlertDialog Builder
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.app_name);
        builder.setMessage(message);

        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int button) {
                    if (mConfirmListener != null) {
                        mConfirmListener.onConfirmed(mDialogId);
                    }
                }
            }
        );

        builder.setNegativeButton(android.R.string.no, null);
        return builder.create(); // return the AlertDialog
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ConfirmListener) {
            mConfirmListener = (ConfirmListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mConfirmListener = null;
    }
}
