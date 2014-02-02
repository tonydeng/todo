package cn.nhorizon.android.demo.todo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "todolistDBv1";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table todolist (_id INTEGER primary key autoincrement, task text not null, state INTEGER NOT null);";
	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Not implemented
		
	}
	
}
