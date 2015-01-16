package com.iitg.alcher.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.iitg.alcher.model.EventObj;

public class DatabaseHandler extends SQLiteOpenHelper {

	String data;
	String[] valuespa;
	String[][] datastore = new String[400][11];
	int totalEntries = 0;
	String MyInputFile;
	private final Context myContext;
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "DatabaseEvents";
	private static final String TABLE_EVENTS = "TableEvents";

	// Events Table Columns names
	private static final String KEY_ID = "C_id";
	private static final String KEY_NAME = "C_name";
	private static final String KEY_TIME0 = "C_time0";
	private static final String KEY_TIME1 = "C_time1";
	private static final String KEY_TIME2 = "C_time2";
	private static final String KEY_TIME3 = "C_time3";
	private static final String KEY_VENUE = "C_venue";
	private static final String KEY_DESCRIPTION = "C_description";
	private static final String KEY_TYPE = "C_type";
	private static final String KEY_REMIND = "C_remind";


	public DatabaseHandler(Context context, String FileAddress)
	{
		super(context, DATABASE_NAME, null, 24);//replace database version with one from file	
		this.myContext = context;
		this.MyInputFile = FileAddress;
	}

	public DatabaseHandler(Context context)
	{
		super(context, DATABASE_NAME, null, 24);//replace database version with one from file	
		this.myContext = context;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
		+ KEY_ID + " TEXT," 
		+ KEY_NAME + " TEXT,"
		+ KEY_TIME0 + " TEXT,"
		+ KEY_TIME1 + " TEXT,"
		+ KEY_TIME2 + " TEXT,"
		+ KEY_TIME3 + " TEXT,"
		+ KEY_VENUE + " TEXT,"
		+ KEY_TYPE + " TEXT,"
		+ KEY_REMIND + " TEXT,"
		+ KEY_DESCRIPTION + " TEXT" + ")";
		db.execSQL(CREATE_EVENTS_TABLE);

		try {
			readcsv(myContext);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		insertDefault(db);
	}



	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
		// Create tables again
		onCreate(db);
	}
	
	public void ManuallyUpgrade() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
		onCreate(db);
	}
	
	


	private void readcsv(Context context) throws FileNotFoundException {
		File dir = Environment.getExternalStorageDirectory();
		File yourFile = new File(dir, "alcheringa/schedule.csv");
		FileInputStream is = null;	
		is = new FileInputStream(yourFile);

		int i=0 ;
		int k=0 ;
		try {
			Scanner inputstream = new Scanner(is);
			while(inputstream.hasNextLine())
			{
				k=0;
				data = inputstream.nextLine();  // gets a whole line
				valuespa = data.split(",");
				while(k<10)
				{
					datastore[i][k] = valuespa[k];
					k++;
					
				}
				totalEntries++;
				i++;
			}
			inputstream.close();

		}
		finally{}

	}

	public ArrayList<EventObj> getresultforquery(String query) {
		ArrayList<EventObj> eventList = new ArrayList<EventObj>();
		// Select All Query
		String selectQuery = query;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				EventObj event = new EventObj();
				event.setId(cursor.getString(0));
				event.setName(cursor.getString(1));
				event.setTimeDay0(cursor.getString(2));
				event.setTimeDay1(cursor.getString(3));
				event.setTimeDay2(cursor.getString(4));
				event.setTimeDay3(cursor.getString(5));
				event.setVenue(cursor.getString(6));
				event.setType(cursor.getString(7));
				event.setVenueId(cursor.getString(8));
				event.setDescription(cursor.getString(9));
				// Adding event to list
				eventList.add(event);
			} while (cursor.moveToNext());
		}

		// return event list
		db.close();
		return eventList;
	}

	private void insertDefault(SQLiteDatabase db) {
		for(int i=0;i<totalEntries;i++)
		{
			db.execSQL("INSERT INTO `TableEvents` (`C_id`, `C_name`, `C_time0`, `C_time1`, `C_time2`, `C_time3`, `C_venue`, `C_type`, `C_remind`, `C_description`) VALUES(" + datastore[i][0] + ","  + datastore[i][1]  + "," +  datastore[i][2]  + "," +  datastore[i][3] + "," + datastore[i][4] + "," + datastore[i][5]+ "," + datastore[i][6]+ "," + datastore[i][7]+ "," + datastore[i][8]+ "," + datastore[i][9] + ");" );
		}

	}

	public static int getDatabaseVersion() {
		return DATABASE_VERSION;
	}


}
