package com.example.testingflashy.dialogclasses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.testingflashy.R;

public class AddDeckDialog extends AppCompatDialogFragment {
    private EditText editTextDeckTitle;

    private AddDeckDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.adddeckdialog, null);

        builder.setView(view)
                .setTitle("Add Deck")
                .setNegativeButton("cancel", (dialogInterface, i) -> {

                })
                .setPositiveButton("add", (dialogInterface, i) -> {
                    String title = editTextDeckTitle.getText().toString();
                    listener.makeDeck(title);

                });
        editTextDeckTitle = view.findViewById(R.id.deck_name_input);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AddDeckDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +
                    "must implement DialogListener");
        }
    }

    public interface AddDeckDialogListener{
        void makeDeck(String _title);
    }
}
