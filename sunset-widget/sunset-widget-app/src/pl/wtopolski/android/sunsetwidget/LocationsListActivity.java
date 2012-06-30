package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.fragment.LocationsListFragment;
import pl.wtopolski.android.sunsetwidget.fragment.LocationsListFragment.Mode;
import pl.wtopolski.android.sunsetwidget.fragment.MainFragment;
import pl.wtopolski.android.sunsetwidget.fragment.OnLocationItemSelected;
import pl.wtopolski.android.sunsetwidget.util.FlowManager;
import pl.wtopolski.android.sunsetwidget.util.actionbar.ActionBarFragmentActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class LocationsListActivity extends ActionBarFragmentActivity implements OnLocationItemSelected {
    protected static final String LOG_TAG = LocationsListActivity.class.getSimpleName();
    
    public final static String LIST_FRAGMENT_TAG = "LIST_FRAGMENT_TAG";
    public final static String MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG";
    
    private LocationsListFragment listFragment;
    private MainFragment mainFragment;
    private FragmentManager fragmentManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locations);
        setTitle(R.string.locations_title);
        
        fragmentManager = getSupportFragmentManager();
		listFragment = (LocationsListFragment) fragmentManager.findFragmentByTag(LIST_FRAGMENT_TAG);
		mainFragment = (MainFragment) fragmentManager.findFragmentByTag(MAIN_FRAGMENT_TAG);

        if (listFragment == null) {
        	Bundle arguments = new Bundle();
        	arguments.putSerializable(LocationsListFragment.MODE_KEY, Mode.LOCATIONS);
        	
        	FragmentTransaction transaction = fragmentManager.beginTransaction();
        	listFragment = new LocationsListFragment();
        	listFragment.setArguments(arguments);
        	transaction.add(R.id.listFragment, listFragment, LIST_FRAGMENT_TAG);
            transaction.commit();
        }
        
    	if (isDualPanel()) {
    		if (mainFragment == null) {
	            Bundle arguments = new Bundle();
	            arguments.putInt(MainFragment.LOCATION_ID, MainFragment.LOCATION_UNKNOWN);
	            
	        	FragmentTransaction transaction = fragmentManager.beginTransaction();
	        	mainFragment = new MainFragment();
	        	mainFragment.setArguments(arguments);
	        	transaction.add(R.id.mainFragment, mainFragment, MAIN_FRAGMENT_TAG);
	            transaction.commit();
    		}
    	} else {
    		resetAllCommitedTransaction();
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
			listFragment.setQuery(query);
		} else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			onLocationItemSelected(Integer.valueOf(intent.getDataString()));
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
            case R.id.home:
            	FlowManager.goToParent(this, HomeActivity.class);
                break;
            case R.id.menu_search_item:
            	onSearchRequested();
            	break;
        }
        return super.onOptionsItemSelected(item);
    }

	public void onLocationItemSelected(int id) {
        Bundle arguments = new Bundle();
        arguments.putInt(MainFragment.LOCATION_ID, id);
        
		if (isDualPanel()) {
        	FragmentTransaction transaction = fragmentManager.beginTransaction();
        	mainFragment = new MainFragment();
        	mainFragment.setArguments(arguments);
        	transaction.replace(R.id.mainFragment, mainFragment, MAIN_FRAGMENT_TAG);
        	transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        	transaction.addToBackStack(null);
            transaction.commit();
		} else {
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtras(arguments);
			startActivity(intent);
		}
	}

	private void resetAllCommitedTransaction() {
		for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
			fragmentManager.popBackStack();
		}
	}
	
	private boolean isDualPanel() {
		return null != findViewById(R.id.mainFragment);
	}
}
