package pl.wtopolski.android.sunsetwidget;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import pl.wtopolski.android.sunsetwidget.R;
import pl.wtopolski.android.sunsetwidget.provider.LocationData;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.model.Location;
import pl.wtopolski.android.sunsetwidget.util.SharedPreferencesStorage;

public class LocationsListActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO
        // 1) Create AsyncTask with ProgressBar
        // 2) Load 50 tmp positions with 1sec delay per location
        // 3) Write XML parser for raw data

        LocationManager locationManager = new LocationManagerImpl();
        locationManager.setContext(getApplicationContext());

        if (!SharedPreferencesStorage.getBoolean(getApplicationContext(), SharedPreferencesStorage.IS_CONTENT_LOADED)) {
            for (int i = 0; i < 10; i++) {
                locationManager.setContext(getApplicationContext());
                locationManager.addLocation(new Location("Łódź " + i, 1f, 2f, "Łódzkie"));

            }
            SharedPreferencesStorage.setBoolean(getApplicationContext(), SharedPreferencesStorage.IS_CONTENT_LOADED, true);
        }

        Cursor cursor = locationManager.getAllLocationsByCursor();
        LocationListAdapter adapter = new LocationListAdapter(this, R.layout.locations_item, cursor);
        setListAdapter(adapter);
    }
}
