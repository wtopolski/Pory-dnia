package pl.wtopolski.android.sunsetwidget;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.inject.Inject;
import pl.wtopolski.android.sunsetwidget.provider.SharedPreferencesStorage;
import pl.wtopolski.android.sunsetwidget.util.DataLoader;
import pl.wtopolski.android.sunsetwidget.util.DataLoaderImpl;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import roboguice.activity.RoboListActivity;

public class LocationsListActivity extends RoboListActivity {
    private static final String LOG_TAG = LocationsListActivity.class.getSimpleName();

    private LocationManager locationManager;
    private boolean shouldShowAll = true;
    private LocationListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locations);

        createLocationManager();
        loadData();

        if (shouldShowAll) {
            showAll();
        } else {
            showFavouritesOnly();
        }
    }

    private void createLocationManager() {
        locationManager = new LocationManagerImpl();
        locationManager.setContext(getApplicationContext());
    }

    private void loadData() {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.locations_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_location:
                newLocation();
                return true;
            case R.id.show_all:
                showAll();
                return true;
            case R.id.show_favourites_only:
                showFavouritesOnly();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFavouritesOnly() {
        Cursor cursor = locationManager.getAllFavouritesLocationsIdByCursor();
        showLocations(cursor);
    }

    private void showAll() {
        Cursor cursor = locationManager.getAllLocationsIdByCursor();
        showLocations(cursor);
    }

    private void newLocation() {
    }

    private void showLocations(Cursor cursor) {
        startManagingCursor(cursor);

        if (adapter == null) {
            adapter = createAdapter(cursor);
            setListAdapter(adapter);
        } else {
            adapter.changeCursor(cursor);
            adapter.notifyDataSetChanged();
        }
    }

    private LocationListAdapter createAdapter(Cursor cursor) {
        adapter = new LocationListAdapter(this, R.layout.locations_item, cursor);
        adapter.setLocationManager(locationManager);
        return adapter;
    }
}
