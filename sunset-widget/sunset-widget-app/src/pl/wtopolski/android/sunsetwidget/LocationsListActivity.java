package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.util.FlowManager;
import pl.wtopolski.android.sunsetwidget.util.actionbar.ActionBarFragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class LocationsListActivity extends ActionBarFragmentActivity implements LocationListActivityInterface {
    protected static final String LOG_TAG = LocationsListActivity.class.getSimpleName();

    public static String SHOW_ACTION = "SHOW_ACTION";
    public static boolean SHOW_ALL = true;
    public static boolean SHOW_FAVOURITES = false;
    private boolean showAction = SHOW_ALL;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locations);
        
        if (savedInstanceState != null) {
    		showAction = savedInstanceState.getBoolean(SHOW_ACTION, SHOW_ALL);
        } else if (getIntent().hasExtra(SHOW_ACTION)) {
    		showAction = getIntent().getBooleanExtra(SHOW_ACTION, SHOW_ALL);
        }

    	if (showAction) {
            setTitle(R.string.dashboard_locations);
        } else {
            setTitle(R.string.dashboard_favorites);
        }
        
        if (savedInstanceState != null) {
        	ListFragment locationsListFragment = (ListFragment) getSupportFragmentManager().findFragmentByTag("lista");
        } else {
        	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ListFragment locationsListFragment = new LocationsListFragment();
            transaction.replace(R.id.locationsListFragement, locationsListFragment, "lista");
            transaction.commit();
        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	outState.putBoolean(SHOW_ACTION, showAction);
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
        }
        return super.onOptionsItemSelected(item);
    }

	public boolean getShowAction() {
		return showAction;
	}
}
