package pl.wtopolski.android.sunsetwidget.util;

import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.*;
import pl.wtopolski.android.sunsetwidget.model.Location;
import pl.wtopolski.android.sunsetwidget.model.SelectionType;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

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

    public Uri addLocation(Location loc) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, loc.getName());
        values.put(COLUMN_NAME_LATITUDE, loc.getLatitude());
        values.put(COLUMN_NAME_LONGITUDE, loc.getLongitude());
        values.put(COLUMN_NAME_PROVINCE, loc.getProvince());
        values.put(COLUMN_NAME_SELECTION, loc.getType().getValue());
        Uri uri = context.getContentResolver().insert(CONTENT_URI, values);
        if (listener != null && uri != null) {
        	listener.addedLocation();
        }
        return uri;
    }

    public Cursor getAllLocationsIdByCursor() {
        return context.getContentResolver().query(CONTENT_URI, STANDARD_LOCATION_PROJECTION, null, null, DEFAULT_SORT_ORDER);
    }

    public Cursor getAllFavouritesLocationsIdByCursor() {
    	return context.getContentResolver().query(CONTENT_URI, STANDARD_LOCATION_PROJECTION, COLUMN_NAME_SELECTION + ">=?", new String[]{String.valueOf(SelectionType.FAVOURITE.getValue())}, DEFAULT_SORT_ORDER);
    }

    public Location getLocation(int id) {
        Uri locationUri = ContentUris.withAppendedId(CONTENT_URI, id);
        Cursor cursor = context.getContentResolver().query(locationUri, STANDARD_LOCATION_PROJECTION, null, null, null);
        Location location = null;

        if (cursor.moveToFirst()) {
            int nameColumn = cursor.getColumnIndex(COLUMN_NAME_NAME);
            int latitudeColumn = cursor.getColumnIndex(COLUMN_NAME_LATITUDE);
            int longitudeColumn = cursor.getColumnIndex(COLUMN_NAME_LONGITUDE);
            int provinceColumn = cursor.getColumnIndex(COLUMN_NAME_PROVINCE);
            int selectionColumn = cursor.getColumnIndex(COLUMN_NAME_SELECTION);

            String name = cursor.getString(nameColumn);
            double latitude = cursor.getDouble(latitudeColumn);
            double longitude = cursor.getDouble(longitudeColumn);
            String province = cursor.getString(provinceColumn);
            int selection = cursor.getInt(selectionColumn);

            location = new Location(id, name, latitude, longitude, province);
            location.setType(SelectionType.getSelectionType(selection));
        }

        cursor.close();
        return location;
    }

    public boolean makeNoFavourite(Location location) {
        Uri locationUri = ContentUris.withAppendedId(CONTENT_URI, location.getId());

        if (location.getType() != SelectionType.FAVOURITE) {
        	return false;
        }
        
        ContentValues values = new ContentValues(1);
        values.put(COLUMN_NAME_SELECTION, SelectionType.NONE.getValue());

        int count = context.getContentResolver().update(locationUri, values, null, null);
        return (count <= 0) ? false : true;
    }

    public boolean makeFavourite(Location location) {
        Uri locationUri = ContentUris.withAppendedId(CONTENT_URI, location.getId());

        if (location.getType() != SelectionType.NONE) {
        	return true;
        }
        
        ContentValues values = new ContentValues(1);
        values.put(COLUMN_NAME_SELECTION, SelectionType.FAVOURITE.getValue());

        int count = context.getContentResolver().update(locationUri, values, null, null);
        return (count <= 0) ? false : true;
    }

	public boolean selectAsMain(Location location) {
        ContentValues values = new ContentValues(1);
        values.put(COLUMN_NAME_SELECTION, SelectionType.FAVOURITE.getValue());
        context.getContentResolver().update(CONTENT_URI, values, COLUMN_NAME_SELECTION + "=?", new String[]{String.valueOf(SelectionType.SELECTED.getValue())});
		
		Uri locationUri = ContentUris.withAppendedId(CONTENT_URI, location.getId());
        values = new ContentValues(1);
        values.put(COLUMN_NAME_SELECTION, SelectionType.SELECTED.getValue());
        int count = context.getContentResolver().update(locationUri, values, null, null);
        return (count <= 0) ? false : true;
	}

	public Location getMainLocation() {
		Cursor cursor = context.getContentResolver().query(CONTENT_URI, STANDARD_LOCATION_PROJECTION, COLUMN_NAME_SELECTION + "=?", new String[]{String.valueOf(SelectionType.SELECTED.getValue())}, DEFAULT_SORT_ORDER);
        Location location = null;

        if (cursor.moveToFirst()) {
            int idColumn = cursor.getColumnIndex(COLUMN_NAME_ID);
            int nameColumn = cursor.getColumnIndex(COLUMN_NAME_NAME);
            int latitudeColumn = cursor.getColumnIndex(COLUMN_NAME_LATITUDE);
            int longitudeColumn = cursor.getColumnIndex(COLUMN_NAME_LONGITUDE);
            int provinceColumn = cursor.getColumnIndex(COLUMN_NAME_PROVINCE);
            int selectionColumn = cursor.getColumnIndex(COLUMN_NAME_SELECTION);

            int id = cursor.getInt(idColumn);
            String name = cursor.getString(nameColumn);
            double latitude = cursor.getDouble(latitudeColumn);
            double longitude = cursor.getDouble(longitudeColumn);
            String province = cursor.getString(provinceColumn);
            int selection = cursor.getInt(selectionColumn);

            location = new Location(id, name, latitude, longitude, province);
            location.setType(SelectionType.getSelectionType(selection));
        }

        cursor.close();
        return location;
	}
}
