package pl.wtopolski.android.sunsetwidget;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import org.xmlpull.v1.XmlPullParserException;
import pl.wtopolski.android.sunsetwidget.provider.SharedPreferencesStorage;
import pl.wtopolski.android.sunsetwidget.util.DataLoader;
import pl.wtopolski.android.sunsetwidget.util.DataLoaderImpl;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.model.Location;

import java.io.IOException;

public class LocationsListActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locations);

        // TODO
        // 1) Create AsyncTask with ProgressBar
        // 2) Load 50 tmp positions with 1sec delay per location
        // 3) Write XML parser for raw data

        LocationManager locationManager = new LocationManagerImpl();
        locationManager.setContext(getApplicationContext());

        if (!SharedPreferencesStorage.getBoolean(getApplicationContext(), SharedPreferencesStorage.IS_CONTENT_LOADED)) {
            locationManager.deleteAll();

            try {
                DataLoader dataLoader = new DataLoaderImpl();
                dataLoader.fill(getApplicationContext(), locationManager, R.xml.places);
                SharedPreferencesStorage.setBoolean(getApplicationContext(), SharedPreferencesStorage.IS_CONTENT_LOADED, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Cursor cursor = locationManager.getAllLocationsIdByCursor();
        startManagingCursor(cursor);
        LocationListAdapter adapter = new LocationListAdapter(this, R.layout.locations_item, cursor);
        adapter.setLocationManager(locationManager);
        setListAdapter(adapter);
    }
}
