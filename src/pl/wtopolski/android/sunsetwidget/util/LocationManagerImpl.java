package pl.wtopolski.android.sunsetwidget.util;

import android.content.Context;
import android.database.Cursor;
import pl.wtopolski.android.sunsetwidget.model.Location;

import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.*;

import java.util.List;

public class LocationManagerImpl implements LocationManager {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean loadLocations() {
        return false;
    }

    public List<Location> getAllLocations() {
        Cursor cursor = context.getContentResolver().query(CONTENT_URI, STANDARD_LOCATION_PROJECTION, null, null, DEFAULT_SORT_ORDER);

        /*
        if (cursor.moveToFirst()) {

            int nameColumn = cur.getColumnIndex(People.NAME);
            int phoneColumn = cur.getColumnIndex(People.NUMBER);

            do {
                name = cur.getString(nameColumn);
                phoneNumber = cur.getString(phoneColumn);
            } while (cur.moveToNext());
        }
        */

        return null;
    }
}
