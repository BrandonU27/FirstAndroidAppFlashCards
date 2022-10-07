package com.example.testingflashy.dialogclasses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.testingflashy.R;

public class InforDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.informationdialog, null);

        builder.setView(view)
                .setTitle("HOW IT WORKS")
                .setPositiveButton("Ok", ((dialogInterface, i) -> {

                }));

                return builder.create();
    }

}
