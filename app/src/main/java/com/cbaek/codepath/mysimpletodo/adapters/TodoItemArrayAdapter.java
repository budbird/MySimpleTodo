package com.cbaek.codepath.mysimpletodo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cbaek.codepath.mysimpletodo.R;
import com.cbaek.codepath.mysimpletodo.models.TodoItemArrayListModel;

import java.util.ArrayList;


public class TodoItemArrayAdapter extends ArrayAdapter<TodoItemArrayListModel> {
    public TodoItemArrayAdapter(Context context, ArrayList<TodoItemArrayListModel> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TodoItemArrayListModel todoItemArrayListModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(com.cbaek.codepath.mysimpletodo.R.layout.main_list_todo_item, parent, false);
        }
        // Lookup view for data population
        TextView itemName = (TextView) convertView.findViewById(com.cbaek.codepath.mysimpletodo.R.id.item_name);
        TextView itemPriority = (TextView) convertView.findViewById(com.cbaek.codepath.mysimpletodo.R.id.item_priority);

        TextView itemDueDate = (TextView) convertView.findViewById(com.cbaek.codepath.mysimpletodo.R.id.item_due_date);

        // Populate the data into the template view using the data object
        itemName.setText(todoItemArrayListModel.itemName);
        itemPriority.setText(todoItemArrayListModel.priority);
        itemDueDate.setText(todoItemArrayListModel.month + "/" + todoItemArrayListModel.day + "/" + todoItemArrayListModel.year);
        itemDueDate.setTextColor(getContext().getResources().getColor(R.color.colorDueDate));

        int color = Color.BLACK;
        switch (todoItemArrayListModel.priority) {
            case "HIGH":
                color = Color.RED;
                break;
            case "MEDIUM":
                color = Color.BLUE;
                break;
            case "LOW":
                color = Color.GREEN;
                break;
        }
        itemPriority.setTextColor(color);
        // Return the completed view to render on screen
        return convertView;
    }
}