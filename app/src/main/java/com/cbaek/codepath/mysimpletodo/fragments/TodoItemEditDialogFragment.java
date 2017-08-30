package com.cbaek.codepath.mysimpletodo.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

public class TodoItemEditDialogFragment extends DialogFragment implements /*TextView.OnEditorActionListener,*/ View.OnClickListener/*, DatePickerDialog.O.OnDateSetListener*/ {

    private EditText mEditText;
    private DatePicker mDatePicker;

    private String mRadioButtonString;

    String item_title;
    int item_index;
    String item_priority;
    int item_priority_index;

    int year;
    int month;
    int day;

    public TodoItemEditDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    // 1. Defines the listener interface with a method passing back data result.
    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText, int index, String priority, int year, int month, int day);
    }

    public static TodoItemEditDialogFragment newInstance(String title) {
        TodoItemEditDialogFragment frag = new TodoItemEditDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.95), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(com.cbaek.codepath.mysimpletodo.R.layout.edit_dialog_fragment, container);
        view.findViewById(com.cbaek.codepath.mysimpletodo.R.id.radio_high).setOnClickListener(this);
        view.findViewById(com.cbaek.codepath.mysimpletodo.R.id.radio_medium).setOnClickListener(this);
        view.findViewById(com.cbaek.codepath.mysimpletodo.R.id.radio_low).setOnClickListener(this);
        view.findViewById(com.cbaek.codepath.mysimpletodo.R.id.editItemActivityButton).setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(com.cbaek.codepath.mysimpletodo.R.id.editItemActivityEditText);
        mDatePicker = (DatePicker) view.findViewById(com.cbaek.codepath.mysimpletodo.R.id.datePicker);

        RadioButton mRadiobutton1 = (RadioButton) view.findViewById(com.cbaek.codepath.mysimpletodo.R.id.radio_high);
        RadioButton mRadiobutton2 = (RadioButton) view.findViewById(com.cbaek.codepath.mysimpletodo.R.id.radio_medium);
        RadioButton mRadiobutton3 = (RadioButton) view.findViewById(com.cbaek.codepath.mysimpletodo.R.id.radio_low);

        item_title = getArguments().getString("item_title");
        item_index = getArguments().getInt("item_index", 1);
        item_priority = getArguments().getString("item_priority");
        item_priority_index = getArguments().getInt("item_priority_index", 0);

        year = getArguments().getInt("item_year", 0);
        month = getArguments().getInt("item_month", 0);
        day = getArguments().getInt("item_day", 0);


        mDatePicker.updateDate(year, month-1, day);

        mRadioButtonString = item_priority;
        switch (item_priority_index) {
            case 0:
                mRadiobutton1.setChecked(true);
                break;
            case 1:
                mRadiobutton2.setChecked(true);
                break;
            case 2:
                mRadiobutton3.setChecked(true);
                break;

        }
        mEditText.setText(item_title);
        mEditText.setSelection(item_title.length());
    }


    public boolean onSaveItem() {
        EditNameDialogListener listener = (EditNameDialogListener) getActivity();
        listener.onFinishEditDialog(mEditText.getText().toString(), item_index, mRadioButtonString, mDatePicker.getYear(),mDatePicker.getMonth() + 1, mDatePicker.getDayOfMonth());
        dismiss();
        return true;
    }

    @Override
    public void onClick(View v){
        if (v instanceof RadioButton) {
        boolean checked = ((RadioButton) v).isChecked();
        switch(v.getId()) {
            case com.cbaek.codepath.mysimpletodo.R.id.radio_high:
                if(checked) {
                    mRadioButtonString = "HIGH";
                }
                break;
            case com.cbaek.codepath.mysimpletodo.R.id.radio_medium:
                if(checked) {
                    mRadioButtonString = "MEDIUM";
                }
                break;
            case com.cbaek.codepath.mysimpletodo.R.id.radio_low:
                if(checked) {
                    mRadioButtonString = "LOW";
                }
                break;
        }
        } else if (v instanceof Button) {
            onSaveItem();
         }
    }

}