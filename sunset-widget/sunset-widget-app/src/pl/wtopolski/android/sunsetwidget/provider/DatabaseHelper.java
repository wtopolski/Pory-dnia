package pl.wtopolski.android.sunsetwidget.provider;

import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_ID;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_LATITUDE;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_LONGITUDE;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_NAME;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_PROVINCE;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_SELECTION;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_SIMPLE_NAME;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.TABLE_NAME;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "locations.db";
	private static final int DATABASE_VERSION = 1;
	
	DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT," + COLUMN_SIMPLE_NAME + " TEXT,"
				+ COLUMN_LATITUDE + " REAL," + COLUMN_LONGITUDE + " REAL," + COLUMN_PROVINCE + " TEXT," + COLUMN_SELECTION + " INTEGER" + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}
