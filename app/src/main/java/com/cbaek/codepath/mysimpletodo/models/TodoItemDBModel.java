package com.cbaek.codepath.mysimpletodo.models;

import com.cbaek.codepath.mysimpletodo.data.TodoItemDatabase;
import com.raizlabs.android.dbflow.annotation.Table;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = TodoItemDatabase.class)
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


    public String getItemName() { return this.itemName;};
    public String getPriority() { return this.priority;};

    public int getYear() { return this.year;};
    public int getMonth() { return this.month;};
    public int getDay() { return this.day;};



}
