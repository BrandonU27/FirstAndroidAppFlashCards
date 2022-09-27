package com.example.testingflashy.dialogclasses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.testingflashy.R;

public class SettingDialog extends AppCompatDialogFragment {

    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;

    private SettingDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.settingsdialog, null);

        builder.setView(view)
                .setTitle("Settings Page")
                .setNegativeButton("cancel", (dialogInterface, i) -> {

                })
                .setPositiveButton("apply", (dialogInterface, i) -> {
                Boolean check1 = option1.isChecked();
                Boolean check2 = option2.isChecked();
                Boolean check3 = option3.isChecked();

                listener.studyChange(check1, check2, check3);
                });
        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (SettingDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +
                    "must implement listener");
        }
    }

    public interface SettingDialogListener{
        void studyChange(Boolean _op1, Boolean _op2, Boolean _op3);
    }
}
