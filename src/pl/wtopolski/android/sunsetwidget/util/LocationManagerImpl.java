package pl.wtopolski.android.sunsetwidget.util;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.google.inject.Inject;
import com.google.inject.Provider;
import pl.wtopolski.android.sunsetwidget.model.Location;
import roboguice.inject.ContextScoped;

import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.*;

public class LocationManagerImpl implements LocationManager {
    protected Context context;

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

    public Cursor getAllLocationsIdByCursor() {
        return context.getContentResolver().query(CONTENT_URI, ID_ONLY_LOCATION_PROJECTION, null, null, DEFAULT_SORT_ORDER);
    }

    public Cursor getAllFavouritesLocationsIdByCursor() {
        return context.getContentResolver().query(CONTENT_URI, ID_ONLY_LOCATION_PROJECTION, COLUMN_NAME_FAVOURITES + "=?", new String[]{"1"}, DEFAULT_SORT_ORDER);
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
            int selectedColumn = cursor.getColumnIndex(COLUMN_NAME_SELECTED);
            int favouritesColumn = cursor.getColumnIndex(COLUMN_NAME_FAVOURITES);

            String name = cursor.getString(nameColumn);
            double latitude = cursor.getDouble(latitudeColumn);
            double longitude = cursor.getDouble(longitudeColumn);
            String province = cursor.getString(provinceColumn);
            int selected = cursor.getInt(selectedColumn);
            int favourites = cursor.getInt(favouritesColumn);

            location = new Location(id, name, latitude, longitude, province);
            location.setSelected(selected);
            location.setFavourites(favourites);
        }

        cursor.close();
        return location;
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
