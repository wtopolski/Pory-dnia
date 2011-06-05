package pl.wtopolski.android.sunsetwidget.util;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import pl.wtopolski.android.sunsetwidget.model.Location;

import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.*;

import java.util.LinkedList;
import java.util.List;

public class LocationManagerImpl implements LocationManager {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
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
        values.put(COLUMN_NAME_FAVOURITES, loc.getFavourites());
        values.put(COLUMN_NAME_SELECTED, loc.getSelected());

        return context.getContentResolver().insert(CONTENT_URI, values);
    }

    public List<Location> getAllLocationsByList() {
        List<Location> list = new LinkedList<Location>();
        Cursor cursor = context.getContentResolver().query(CONTENT_URI, STANDARD_LOCATION_PROJECTION, null, null, DEFAULT_SORT_ORDER);

        if (cursor.moveToFirst()) {
            int idColumn = cursor.getColumnIndex(COLUMN_NAME_ID);
            int nameColumn = cursor.getColumnIndex(COLUMN_NAME_NAME);
            int latitudeColumn = cursor.getColumnIndex(COLUMN_NAME_LATITUDE);
            int longitudeColumn = cursor.getColumnIndex(COLUMN_NAME_LONGITUDE);
            int provinceColumn = cursor.getColumnIndex(COLUMN_NAME_PROVINCE);
            int selectedColumn = cursor.getColumnIndex(COLUMN_NAME_SELECTED);
            int favouritesColumn = cursor.getColumnIndex(COLUMN_NAME_FAVOURITES);

            do {
                int id = cursor.getInt(idColumn);
                String name = cursor.getString(nameColumn);
                double latitude = cursor.getDouble(latitudeColumn);
                double longitude = cursor.getDouble(longitudeColumn);
                String province = cursor.getString(provinceColumn);
                int selected = cursor.getInt(selectedColumn);
                int favourites = cursor.getInt(favouritesColumn);

                Location loc = new Location(id, name, latitude, longitude, province);
                loc.setSelected(selected);
                loc.setFavourites(favourites);
                list.add(loc);
            } while (cursor.moveToNext());
        }

        return list;
    }

    public Cursor getAllLocationsIdByCursor() {
        return context.getContentResolver().query(CONTENT_URI, ID_ONLY_LOCATION_PROJECTION, null, null, DEFAULT_SORT_ORDER);
    }

    public Location getLocation(int id) {
        Uri location = ContentUris.withAppendedId(CONTENT_URI, id);
        Cursor cursor = context.getContentResolver().query(location, STANDARD_LOCATION_PROJECTION, null, null, null);
        Location loc = null;

        if (cursor.moveToFirst()) {
            int nameColumn = cursor.getColumnIndex(COLUMN_NAME_NAME);
            int latitudeColumn = cursor.getColumnIndex(COLUMN_NAME_LATITUDE);
            int longitudeColumn = cursor.getColumnIndex(COLUMN_NAME_LONGITUDE);
            int provinceColumn = cursor.getColumnIndex(COLUMN_NAME_PROVINCE);
            int selectedColumn = cursor.getColumnIndex(COLUMN_NAME_SELECTED);
            int favouritesColumn = cursor.getColumnIndex(COLUMN_NAME_FAVOURITES);

            String name = cursor.getString(nameColumn);
            double latitude = cursor.getDouble(latitudeColumn);
            double longitude = cursor.getDouble(longitudeColumn);
            String province = cursor.getString(provinceColumn);
            int selected = cursor.getInt(selectedColumn);
            int favourites = cursor.getInt(favouritesColumn);

            loc = new Location(id, name, latitude, longitude, province);
            loc.setSelected(selected);
            loc.setFavourites(favourites);
        }

        cursor.close();
        return loc;
    }

    public void revertSelection(Location location) {
        int backupState = location.getFavourites();

        Uri locationUri = ContentUris.withAppendedId(CONTENT_URI, location.getId());

        ContentValues values = new ContentValues(1);
        location.revertFavouriteState();
        values.put(COLUMN_NAME_FAVOURITES, location.getFavourites());

        int count = context.getContentResolver().update(locationUri, values, null, null);
        if (count <= 0) {
            location.setFavourites(backupState);
        }
    }
}
