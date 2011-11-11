package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.model.Location;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import roboguice.activity.RoboListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

public class LocationsListActivity extends RoboListActivity {
    protected static final String LOG_TAG = LocationsListActivity.class.getSimpleName();

    private static String SHOULD_SHOW_ALL_KEY = "SHOULD_SHOW_ALL";
    private static boolean SHOULD_SHOW_ALL_DEFAULT = true;
    
    private LocationManager locationManager;
    private boolean shouldShowAll = true;
    private LocationListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locations);
        registerForContextMenu(getListView());
        
        if (savedInstanceState != null) {
    		shouldShowAll = savedInstanceState.getBoolean(SHOULD_SHOW_ALL_KEY, SHOULD_SHOW_ALL_DEFAULT);
        }
        
        locationManager = new LocationManagerImpl();
        locationManager.setContext(getApplicationContext());

    	if (shouldShowAll) {
            showAll();
        } else {
            showFavouritesOnly();
        }
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	outState.putBoolean(SHOULD_SHOW_ALL_KEY, shouldShowAll);
    }

    private void showAll() {
        Cursor cursor = locationManager.getAllLocationsIdByCursor();
        shouldShowAll = true;
        showLocations(cursor);
    }

    private void showFavouritesOnly() {
        Cursor cursor = locationManager.getAllFavouritesLocationsIdByCursor();
        shouldShowAll = false;
        showLocations(cursor);
    }

    private void showLocations(Cursor cursor) {
        startManagingCursor(cursor);
        adapter = new LocationListAdapter(this, R.layout.locations_item, cursor);
        setListAdapter(adapter);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	Intent intent = new Intent(this, MainActivity.class);
    	intent.putExtra(MainActivity.LOCATION_ID, (int)id);
    	startActivity(intent);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.locations_context_menu, menu);
    	super.onCreateContextMenu(menu, view, menuInfo);
    }
    
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int locationId = (int) info.id;
		Location location = locationManager.getLocation(locationId);

		switch (item.getItemId()) {
		case R.id.set_as_main:
			locationManager.selectAsMain(location);
			return true;
		case R.id.add_to_favourites:
			locationManager.makeFavourite(location);
			return true;
		case R.id.remove_from_favourites:
			locationManager.makeNoFavourite(location);
			return true;
		default:
			return super.onContextItemSelected(item);
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
    
    public void showMain(View view) {
    	startActivity(new Intent(this, MainActivity.class));
    	finish();
    }
}
