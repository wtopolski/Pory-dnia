package pl.wtopolski.android.sunsetwidget.util;

import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_ID;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_LATITUDE;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_LONGITUDE;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_NAME;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_PROVINCE;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_SELECTION;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_SIMPLE_NAME;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.CONTENT_URI;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.DEFAULT_SORT_ORDER;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.STANDARD_LOCATION_PROJECTION;

import java.util.ArrayList;
import java.util.List;

import pl.wtopolski.android.sunsetwidget.MyApplication;
import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import pl.wtopolski.android.sunsetwidget.model.SelectionType;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class LocationManagerImpl implements LocationManager {
	protected Context context;

	public LocationManagerImpl() {
		context = MyApplication.getMyApplication().getApplicationContext();
	}

	public void deleteAll() {
		context.getContentResolver().delete(CONTENT_URI, null, null);
	}

	public Uri addLocation(GPSLocation location) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, location.getName());
		values.put(COLUMN_SIMPLE_NAME, location.getName().toLowerCase());
		values.put(COLUMN_LATITUDE, location.getLatitude());
		values.put(COLUMN_LONGITUDE, location.getLongitude());
		values.put(COLUMN_PROVINCE, location.getProvince());
		values.put(COLUMN_SELECTION, location.getType().getValue());
		Uri uri = context.getContentResolver().insert(CONTENT_URI, values);
		Log.d("wtopolski", "addLocation");
		return uri;
	}

	public int getCount() {
		Cursor cursor = getAllLocationsByCursor("");
		int result = 0;
		if (cursor != null) {
			result = cursor.getCount();
			cursor.close();
		}
		return result;
	}

	public Cursor getAllLocationsByCursor(String query) {
		String selection = null;
		String[] selectionArgs = null;

		if (!TextUtils.isEmpty(query)) {
			selection = COLUMN_SIMPLE_NAME + " LIKE ?";
			selectionArgs = new String[] { query + "%" };
		}

		return context.getContentResolver().query(CONTENT_URI, STANDARD_LOCATION_PROJECTION, selection, selectionArgs, DEFAULT_SORT_ORDER);
	}

	public Cursor getAllFavouritesByCursor(String query) {
		StringBuilder selection = new StringBuilder();
		selection.append(COLUMN_SELECTION);
		selection.append(">=?");
		
		List<String> selectionArgs = new ArrayList<String>();
		selectionArgs.add(String.valueOf(SelectionType.FAVOURITE.getValue()));
		
		if (!TextUtils.isEmpty(query)) {
			selection.append(" AND ");
			selection.append(COLUMN_SIMPLE_NAME);
			selection.append(" LIKE ?");
			
			selectionArgs.add(query + "%");
		}
		
		String[] selectionArray = new String[selectionArgs.size()];
		selectionArray = selectionArgs.toArray(selectionArray);
		
		return context.getContentResolver().query(CONTENT_URI, STANDARD_LOCATION_PROJECTION, selection.toString(), selectionArray, DEFAULT_SORT_ORDER);
	}

	public GPSLocation getLocation(int id) {
		Uri locationUri = ContentUris.withAppendedId(CONTENT_URI, id);
		Cursor cursor = context.getContentResolver().query(locationUri, STANDARD_LOCATION_PROJECTION, null, null, null);
		GPSLocation location = null;

		if (cursor.moveToFirst()) {
			int nameColumn = cursor.getColumnIndex(COLUMN_NAME);
			int latitudeColumn = cursor.getColumnIndex(COLUMN_LATITUDE);
			int longitudeColumn = cursor.getColumnIndex(COLUMN_LONGITUDE);
			int provinceColumn = cursor.getColumnIndex(COLUMN_PROVINCE);
			int selectionColumn = cursor.getColumnIndex(COLUMN_SELECTION);

			String name = cursor.getString(nameColumn);
			double latitude = cursor.getDouble(latitudeColumn);
			double longitude = cursor.getDouble(longitudeColumn);
			String province = cursor.getString(provinceColumn);
			int selection = cursor.getInt(selectionColumn);

			location = new GPSLocation(id, name, latitude, longitude, province);
			location.setType(SelectionType.getSelectionType(selection));
		}

		cursor.close();
		return location;
	}

	public boolean makeNoFavourite(GPSLocation location) {
		Uri locationUri = ContentUris.withAppendedId(CONTENT_URI, location.getId());

		if (location.getType() != SelectionType.FAVOURITE) {
			return false;
		}

		ContentValues values = new ContentValues(1);
		values.put(COLUMN_SELECTION, SelectionType.NONE.getValue());

		int count = context.getContentResolver().update(locationUri, values, null, null);
		return (count <= 0) ? false : true;
	}

	public boolean makeFavourite(GPSLocation location) {
		Uri locationUri = ContentUris.withAppendedId(CONTENT_URI, location.getId());

		if (location.getType() != SelectionType.NONE) {
			return true;
		}

		ContentValues values = new ContentValues(1);
		values.put(COLUMN_SELECTION, SelectionType.FAVOURITE.getValue());

		int count = context.getContentResolver().update(locationUri, values, null, null);
		return (count <= 0) ? false : true;
	}

	public boolean selectAsMain(GPSLocation location) {
		ContentValues values = new ContentValues(1);
		values.put(COLUMN_SELECTION, SelectionType.FAVOURITE.getValue());
		context.getContentResolver().update(CONTENT_URI, values, COLUMN_SELECTION + "=?", new String[] { String.valueOf(SelectionType.MAIN.getValue()) });

		Uri locationUri = ContentUris.withAppendedId(CONTENT_URI, location.getId());
		values = new ContentValues(1);
		values.put(COLUMN_SELECTION, SelectionType.MAIN.getValue());
		int count = context.getContentResolver().update(locationUri, values, null, null);
		return (count <= 0) ? false : true;
	}

	public GPSLocation getMainLocation() {
		Cursor cursor = context.getContentResolver().query(CONTENT_URI, STANDARD_LOCATION_PROJECTION, COLUMN_SELECTION + "=?",
				new String[] { String.valueOf(SelectionType.MAIN.getValue()) }, DEFAULT_SORT_ORDER);
		GPSLocation location = null;

		if (cursor.moveToFirst()) {
			int idColumn = cursor.getColumnIndex(COLUMN_ID);
			int nameColumn = cursor.getColumnIndex(COLUMN_NAME);
			int latitudeColumn = cursor.getColumnIndex(COLUMN_LATITUDE);
			int longitudeColumn = cursor.getColumnIndex(COLUMN_LONGITUDE);
			int provinceColumn = cursor.getColumnIndex(COLUMN_PROVINCE);
			int selectionColumn = cursor.getColumnIndex(COLUMN_SELECTION);

			int id = cursor.getInt(idColumn);
			String name = cursor.getString(nameColumn);
			double latitude = cursor.getDouble(latitudeColumn);
			double longitude = cursor.getDouble(longitudeColumn);
			String province = cursor.getString(provinceColumn);
			int selection = cursor.getInt(selectionColumn);

			location = new GPSLocation(id, name, latitude, longitude, province);
			location.setType(SelectionType.getSelectionType(selection));
		}

		cursor.close();
		return location;
	}
}
