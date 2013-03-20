package com.start.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDBHelper extends SQLiteOpenHelper {

	private static final int DATABASEVERSION = 1;
	
	public SQLiteDBHelper(Context context) {
		super(context, Constant.SQLiteDataBaseName, null, DATABASEVERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(oldVersion==2){
			onCreate(db);
		}
	}
}