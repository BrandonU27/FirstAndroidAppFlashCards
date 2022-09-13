package com.example.testingflashy.dialogclasses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.testingflashy.R;

public class AddCardDialog extends AppCompatDialogFragment {
    private EditText editTextQuestion;
    private  EditText editTextAnswer;

    private AddCardDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.addcarddialog, null   );

        builder.setView(view)
                .setTitle("Add Card")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String question = editTextQuestion.getText().toString();
                        String answer = editTextAnswer.getText().toString();

                        listener.makeCard(question, answer);
                    }
                });
        editTextQuestion = view.findViewById(R.id.question_input);
        editTextAnswer = view.findViewById(R.id.answer_input);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AddCardDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement listener");
        }
    }

    public interface AddCardDialogListener{
        void makeCard(String _question, String _answer);
    }
}
