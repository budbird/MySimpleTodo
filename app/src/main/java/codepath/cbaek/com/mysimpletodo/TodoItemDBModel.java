package codepath.cbaek.com.mysimpletodo;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.List;

@Table(database = MyDatabase.class)
public class TodoItemDBModel extends BaseModel {
    @Column
    @PrimaryKey
    String itemName;

    @Column
    String priority;

    @Column
    int year;

    @Column
    int month;

    @Column
    int day;



    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) { this.month = month;}

    public void setDay(int day) {
        this.day = day;
    }

}
