package com.example.soundhoard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SoundboardDialog extends AppCompatDialogFragment {
    public static final String CREATE_DIALOG = "create";
    public static final String UPDATE_DIALOG = "update";
    public static final String DELETE_DIALOG = "delete";

    private EditText soundboardNameEditText;
    private SoundboardDialogListener listener;
    private int title;
    private int hint;

    public SoundboardDialog(String mode) {
        if(mode.equals("create")) {
            title = R.string.soundboards_activity_dialog_title;
            hint = R.string.soundboards_activity_dialog_hint;
        } else if(mode.equals("update")) {
            title = R.string.soundboard_activity_dialog_title;
            hint = R.string.soundboard_activity_dialog_hint;
        } else if(mode.equals("delete")) {
            title = R.string.soundboard_activity_dialog_delete_title;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(title == R.string.soundboard_activity_dialog_delete_title) {
            builder.setTitle(title)
                   .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                       }
                   })
                   .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           listener.applyDelete();
                       }
                   });
        } else {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.activity_soundboards_dialog, null);

            builder.setView(view)
                    .setTitle(title)
                    .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String soundboardName = soundboardNameEditText.getText().toString();
                            listener.applyText(soundboardName);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

            soundboardNameEditText = view.findViewById(R.id.dialogInput);
            soundboardNameEditText.setHint(hint);
        }

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (SoundboardDialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SoundboardDialogListener.");
        }
    }

    public interface SoundboardDialogListener {
        void applyText(String name);
        void applyDelete();
    }
}