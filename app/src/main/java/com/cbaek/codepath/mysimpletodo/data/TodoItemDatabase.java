package com.cbaek.codepath.mysimpletodo.data;

import com.raizlabs.android.dbflow.annotation.Database;


@Database(name = TodoItemDatabase.NAME, version =  TodoItemDatabase.VERSION)
public class TodoItemDatabase {
    public static final String NAME = "MyDataBase";
    public static final int VERSION = 1;
}
