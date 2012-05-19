package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.LocationsListFragment.Mode;
import pl.wtopolski.android.sunsetwidget.util.FlowManager;
import pl.wtopolski.android.sunsetwidget.util.actionbar.ActionBarFragmentActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class LocationsListActivity extends ActionBarFragmentActivity {
    protected static final String LOG_TAG = LocationsListActivity.class.getSimpleName();
    
    private final static String FRAGMENT_TAG = "LOCATIONS_FRAGMENT_TAG";
    private LocationsListFragment locationsListFragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locations);
        setTitle(R.string.dashboard_locations);
        
        if (savedInstanceState != null) {
			locationsListFragment = (LocationsListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        } else {
        	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        	locationsListFragment = new LocationsListFragment();
        	locationsListFragment.setMode(Mode.LOCATIONS);
            transaction.replace(R.id.locationsListFragement, locationsListFragment, FRAGMENT_TAG);
            transaction.commit();
        }
        
		handleIntent(getIntent());
    }

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			locationsListFragment.setQuery(query);
		} else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			Intent outputIntent = new Intent(this, MainActivity.class);
			outputIntent.putExtra(MainActivity.LOCATION_ID, Integer.valueOf(intent.getDataString()));
			startActivity(outputIntent);
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
            	FlowManager.goToHome(this);
                break;
            case R.id.menu_search_item:
            	onSearchRequested();
            	break;
        }
        return super.onOptionsItemSelected(item);
    }
}
