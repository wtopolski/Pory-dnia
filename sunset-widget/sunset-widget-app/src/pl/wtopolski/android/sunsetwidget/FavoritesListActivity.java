package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.fragment.LocationsListFragment;
import pl.wtopolski.android.sunsetwidget.fragment.LocationsListFragment.Mode;
import pl.wtopolski.android.sunsetwidget.fragment.MainFragment;
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

public class FavoritesListActivity extends ActionBarFragmentActivity implements LocationsListFragment.OnLocationSelectedListener {
    protected static final String LOG_TAG = FavoritesListActivity.class.getSimpleName();
    
    private final static String FRAGMENT_TAG = "FAVORITES_FRAGMENT_TAG";
    private LocationsListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);
        setTitle(R.string.favorites_title);
        
        FragmentManager fragmentManager = getSupportFragmentManager();
		listFragment = (LocationsListFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);

        if (listFragment == null) {
        	Bundle arguments = new Bundle();
        	arguments.putSerializable(LocationsListFragment.MODE_KEY, Mode.FAVOURITES);
        	
        	FragmentTransaction transaction = fragmentManager.beginTransaction();
        	listFragment = new LocationsListFragment();
        	listFragment.setArguments(arguments);
        	transaction.add(R.id.locationsListFragement, listFragment, FRAGMENT_TAG);
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
			listFragment.setQuery(query);
		} else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			Intent outputIntent = new Intent(this, MainActivity.class);
			outputIntent.putExtra(MainFragment.LOCATION_ID, Integer.valueOf(intent.getDataString()));
			startActivity(outputIntent);
		}
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.favorites, menu);
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

	public void onLocationSelected(int id) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(MainFragment.LOCATION_ID, id);
		startActivity(intent);
	}
}
