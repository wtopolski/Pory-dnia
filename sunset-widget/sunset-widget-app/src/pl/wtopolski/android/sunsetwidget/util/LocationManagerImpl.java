package pl.wtopolski.android.sunsetwidget.util;

import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.*;
import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import pl.wtopolski.android.sunsetwidget.model.SelectionType;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

public class LocationManagerImpl implements LocationManager {
	protected Context context;
	private LocationManagerListener listener;

	public void setContext(Context context) {
		setContext(context, null);
	}

	public void setContext(Context context, LocationManagerListener listener) {
		this.context = context;
		this.listener = listener;
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

		if (listener != null && uri != null) {
			listener.addedLocation();
		}
		return uri;
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
		return context.getContentResolver().query(CONTENT_URI, STANDARD_LOCATION_PROJECTION, COLUMN_SELECTION + ">=?",
				new String[] { String.valueOf(SelectionType.FAVOURITE.getValue()) }, DEFAULT_SORT_ORDER);
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
