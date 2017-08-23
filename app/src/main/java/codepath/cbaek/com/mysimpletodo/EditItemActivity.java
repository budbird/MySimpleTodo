package codepath.cbaek.com.mysimpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;



// ##### This activity was used for edititem activity. No longer used.

public class EditItemActivity extends AppCompatActivity {

    String item_title;
    int item_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        item_title = getIntent().getStringExtra("item_title");
        item_index = getIntent().getIntExtra("item_index", 1);

        EditText editItemActivityEditText = (EditText) findViewById(R.id.editItemActivityEditText);
        editItemActivityEditText.setText(item_title);
        editItemActivityEditText.setSelection(item_title.length());
    }


    public void onSaveItem(View v) {
        EditText newText = (EditText) findViewById(R.id.editItemActivityEditText);
        Intent data = new Intent();
        data.putExtra("itemIndex", item_index);
        data.putExtra("newText", newText.getText().toString());
        setResult(RESULT_OK, data);
        this.finish();
    }



}
