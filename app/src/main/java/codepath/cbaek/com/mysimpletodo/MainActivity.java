package codepath.cbaek.com.mysimpletodo;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EditNameDialogFragment.EditNameDialogListener {

    ArrayList<TodoItemArrayListModel> items;
    ListView lvItems;
    TodoItemDBModel todoItemDBModel;
    TodoItemArrayAdapter todoItemArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<TodoItemArrayListModel>();

        readData();

        // Create the adapter to convert the array to views
        todoItemArrayAdapter = new TodoItemArrayAdapter(this, items);
        // Attach the adapter to a ListView
        lvItems.setAdapter(todoItemArrayAdapter);

        setupListViewListner();

        todoItemDBModel = new TodoItemDBModel();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        if (!itemText.isEmpty()) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);

            TodoItemArrayListModel todoItemArrayListModel = new TodoItemArrayListModel(itemText, "HIGH", year, month, day);
            todoItemArrayAdapter.add(todoItemArrayListModel);
            etNewItem.setText("");
            insertData(itemText, "HIGH", year, month, day);
        }
    }

    private void setupListViewListner() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        deleteData(items.get(i).itemName);
                        items.remove(i);
                        todoItemArrayAdapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l ) {
                        FragmentManager fm = getSupportFragmentManager();
                        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("edit_dialog");
                        Bundle bundle = new Bundle();
                        bundle.putString("item_title", items.get(i).itemName);

                        bundle.putInt("item_year", items.get(i).year);
                        bundle.putInt("item_month", items.get(i).month);
                        bundle.putInt("item_day", items.get(i).day);

                        bundle.putString("item_priority", items.get(i).priority);
                        int priorityIndex = 0;
                        switch (items.get(i).priority) {
                            case "HIGH":
                                priorityIndex = 0;
                                break;
                            case "MEDIUM":
                                priorityIndex = 1;
                                break;
                            case "LOW":
                                priorityIndex = 2;
                                break;
                        }

                        bundle.putInt("item_index", i);
                        bundle.putInt("item_priority_index", priorityIndex);

                        editNameDialogFragment.setArguments(bundle);
                        editNameDialogFragment.show(fm, "fragment_edit_name");
                    }
                }
        );
    }



    private void insertData(String item, String priority, int year, int month, int day) {
        todoItemDBModel.setItemName(item);
        todoItemDBModel.setPriority(priority);
        todoItemDBModel.setYear(year);
        todoItemDBModel.setMonth(month);
        todoItemDBModel.setDay(day);
        todoItemDBModel.save();
    }

    private void updateData(String srcItem, String tarItemName, String tarPriority, int year, int month, int day) {
        SQLite.update(TodoItemDBModel.class).
                set(TodoItemDBModel_Table.itemName.eq(tarItemName), TodoItemDBModel_Table.priority.eq(tarPriority),
                        TodoItemDBModel_Table.year.eq(year), TodoItemDBModel_Table.month.eq(month), TodoItemDBModel_Table.day.eq(day)).
                where(TodoItemDBModel_Table.itemName.is(srcItem)).
                async().
                execute();
    }

    private void deleteData(String item) {
        todoItemDBModel = SQLite.select().
                from(TodoItemDBModel.class).
                where(TodoItemDBModel_Table.itemName.eq(item)).
                querySingle();
        todoItemDBModel.delete();
    }

    private void readData() {
        List<TodoItemDBModel> todoItemDBModel = SQLite.select().
                from(TodoItemDBModel.class).
                queryList();

        Iterator<TodoItemDBModel> todoItemModelIterator =  todoItemDBModel.iterator();

        while (todoItemModelIterator.hasNext()) {
            TodoItemDBModel todoItem = todoItemModelIterator.next();
            items.add(new TodoItemArrayListModel(todoItem.itemName, todoItem.priority, todoItem.year, todoItem.month, todoItem.day));
        }
    }

    // This method is invoked in the activity when the listener is triggered
    // Access the data result passed to the activity here
    @Override
    public void onFinishEditDialog(String inputText, int index, String priority, int year, int month, int day) {
        Toast.makeText(this, "item updated:  " + inputText + " / index -> " + index + " / priority -> " + priority
                + " / year: " + year + " / month: " + month  + " / day: " + day,
                Toast.LENGTH_SHORT).show();

        updateData(items.get(index).itemName, inputText, priority, year, month, day);
        //items.set(index, inputText);
        items.set(index, new TodoItemArrayListModel(inputText, priority, year, month, day));
        //itemsAdapter.notifyDataSetChanged();
        todoItemArrayAdapter.notifyDataSetChanged();
    }



    // ** For using text file **
//    private void readItems() {
//
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, "todo.txt");
//        try {
//            items = new ArrayList<String>(FileUtils.readLines(todoFile));
//        } catch (IOException e) {
//            items =new ArrayList<String>();
//        }
//    }

    // ** For using text file **
//    private void writeItems() {
//        File filesDir =  getFilesDir();
//        File todoFile = new File(filesDir, "todo.txt");
//        try {
//            FileUtils.writeLines(todoFile, items);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    // ** For using activity **
//   @Override
//    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//            String newItemTitle = data.getExtras().getString("newText");
//            int itemIndex = data.getExtras().getInt("itemIndex");
//
//            updateData(items.get(itemIndex), newItemTitle);
//            items.set(itemIndex, newItemTitle);
//            itemsAdapter.notifyDataSetChanged();
//
//        }
//
//    }





}
