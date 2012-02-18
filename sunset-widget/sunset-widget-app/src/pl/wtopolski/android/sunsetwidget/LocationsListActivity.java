package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.adapter.LocationListAdapter;
import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import pl.wtopolski.android.sunsetwidget.util.actionbar.ActionBarListActivity;
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

public class LocationsListActivity extends ActionBarListActivity {
    protected static final String LOG_TAG = LocationsListActivity.class.getSimpleName();

    public static String SHOW_ACTION = "SHOW_ACTION";
    public static boolean SHOW_ALL = true;
    public static boolean SHOW_FAVOURITES = false;
    
    private LocationManager locationManager;
    private boolean showAction = SHOW_ALL;
    private LocationListAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locations);
        registerForContextMenu(getListView());
        
        if (savedInstanceState != null) {
    		showAction = savedInstanceState.getBoolean(SHOW_ACTION, SHOW_ALL);
        } else if (getIntent().hasExtra(SHOW_ACTION)) {
    		showAction = getIntent().getBooleanExtra(SHOW_ACTION, SHOW_ALL);
        }
        
        locationManager = new LocationManagerImpl();
        locationManager.setContext(getApplicationContext());

    	if (showAction) {
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
    	outState.putBoolean(SHOW_ACTION, showAction);
    }

    private void showAll() {
        Cursor cursor = locationManager.getAllLocationsIdByCursor();
        showAction = SHOW_ALL;
        showLocations(cursor);
    }

    private void showFavouritesOnly() {
        Cursor cursor = locationManager.getAllFavouritesLocationsIdByCursor();
        showAction = SHOW_FAVOURITES;
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
		GPSLocation location = locationManager.getLocation(locationId);

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
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.locations, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            	finishAndGoTo(HomeActivity.class);
                break;

            case R.id.menu_favourite:
            	finishAndGoTo(MainActivity.class);
                break;
                
            case R.id.show_all:
                showAll();
            	break;
            
            case R.id.show_favourites_only:
                showFavouritesOnly();
            	break;
        }
        return super.onOptionsItemSelected(item);
    }
}
