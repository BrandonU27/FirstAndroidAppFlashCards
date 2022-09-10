package com.example.testingflashy.dialogclasses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.testingflashy.R;

public class AddDialog extends AppCompatDialogFragment {
    private EditText editTextName;
    private EditText editTextDate;
    private EditText editTextTime;

    private AddDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.addtestdialog, null);

        builder.setView(view)
                .setTitle("Add Test")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = editTextName.getText().toString();
                        String date = editTextDate.getText().toString();
                        String time = editTextTime.getText().toString();
                        listener.makeTest(title, date, time);
                    }
                });
        editTextName = view.findViewById(R.id.name_input);
        editTextDate = view.findViewById(R.id.date_input);
        editTextTime = view.findViewById(R.id.time_input);

        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            listener = (AddDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement AddDialogListener");
        }
    }

    public interface AddDialogListener{
        void makeTest(String _title, String _date, String _time);
    }

}
