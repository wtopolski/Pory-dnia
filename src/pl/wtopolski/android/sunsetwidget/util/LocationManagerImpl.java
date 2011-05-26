package pl.wtopolski.android.sunsetwidget.util;

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
            int nameColumn = cursor.getColumnIndex(COLUMN_NAME_NAME);
            int latitudeColumn = cursor.getColumnIndex(COLUMN_NAME_LATITUDE);
            int longitudeColumn = cursor.getColumnIndex(COLUMN_NAME_LONGITUDE);
            int provinceColumn = cursor.getColumnIndex(COLUMN_NAME_PROVINCE);

            do {
                String name = cursor.getString(nameColumn);
                double latitude = cursor.getDouble(latitudeColumn);
                double longitude = cursor.getDouble(longitudeColumn);
                String province = cursor.getString(provinceColumn);
                Location loc = new Location(name, latitude, longitude, province);
                list.add(loc);
            } while (cursor.moveToNext());
        }

        return list;
    }

    public Cursor getAllLocationsByCursor() {
        return context.getContentResolver().query(CONTENT_URI, STANDARD_LOCATION_PROJECTION, null, null, DEFAULT_SORT_ORDER);
    }
}
